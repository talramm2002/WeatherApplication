package util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class weatherService {

    // Mapping of city names to their latitude and longitude coordinates for API
    // requests
    private static final Map<String, double[]> cityGeoHash = new HashMap<>();

    static {
        cityGeoHash.put("Adelaide", new double[] { -34.9285, 138.6007 });
        cityGeoHash.put("Brisbane", new double[] { -27.4698, 153.0251 });
        cityGeoHash.put("Cairns", new double[] { -16.9186, 145.7781 });
        cityGeoHash.put("Canberra", new double[] { -35.2809, 149.1300 });
        cityGeoHash.put("Darwin", new double[] { -12.4634, 130.8456 });
        cityGeoHash.put("Esperance", new double[] { -33.8612, 121.8914 });
        cityGeoHash.put("Gold Coast", new double[] { -28.0167, 153.4000 });
        cityGeoHash.put("Hobart", new double[] { -42.8821, 147.3272 });
        cityGeoHash.put("Melbourne", new double[] { -37.8136, 144.9631 });
        cityGeoHash.put("Newcastle", new double[] { -32.9283, 151.7817 });
        cityGeoHash.put("Perth", new double[] { -31.9505, 115.8605 });
        cityGeoHash.put("Sunshine Coast", new double[] { -26.6500, 153.0667 });
        cityGeoHash.put("Sydney", new double[] { -33.8688, 151.2093 });
        cityGeoHash.put("Townsville", new double[] { -19.2590, 146.8169 });
        cityGeoHash.put("Wollongong", new double[] { -34.4278, 150.8931 });
    }

    // Data class to hold weather information retrieved from the API
    public static class WeatherData {
        public double currentTemp;
        public int humidity;
        public String[] dailyDates;
        public double[] dailyMaxTemps;
        public double[] dailyMinTemps;
        public double[] rainChance;
    }

    // Helper method to make a HTTP GET request
    private static String makeRequest(String urlString) throws Exception {
        URL url = new URL(urlString);
        // Opening the connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // Outlining request method, accepting only JSON for the scanner
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        // Timeouts to prevent hanging if API is unresponsive
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(10000);

        // Opening "pipe" to read the stream of bytes
        Scanner scanner = new Scanner(connection.getInputStream());
        // Reading and appending lines into StringBuilder as long as there is a next
        // line to read
        StringBuilder response = new StringBuilder();
        while (scanner.hasNextLine()) {
            response.append(scanner.nextLine());
        }
        scanner.close();
        return response.toString();
    }

    // Main method to retrieve weather data for a given city using the Open-Meteo
    // API
    public static WeatherData getWeather(String city) {
        try {
            // Look up coordinates for the city
            double[] coords = cityGeoHash.get(city);
            if (coords == null)
                return null;

            double lat = coords[0];
            double lon = coords[1];

            // Construct API request URL with query parameters for current weather and 7 day
            // forecast
            String response = makeRequest(
                    "https://api.open-meteo.com/v1/forecast"
                            + "?latitude=" + lat
                            + "&longitude=" + lon
                            + "&current=temperature_2m,relative_humidity_2m"
                            + "&daily=temperature_2m_max,temperature_2m_min,precipitation_probability_max"
                            + "&timezone=auto"
                            + "&forecast_days=7");

            WeatherData data = new WeatherData();

            // Validating JSON format and parsing response into WeatherData object
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();

            // Parse "current" object
            JsonObject current = json.getAsJsonObject("current");
            data.currentTemp = current.get("temperature_2m").getAsDouble();
            data.humidity = current.get("relative_humidity_2m").getAsInt();

            // Parse "daily" object
            JsonObject daily = json.getAsJsonObject("daily");
            JsonArray dates = daily.getAsJsonArray("time");
            JsonArray maxTemps = daily.getAsJsonArray("temperature_2m_max");
            JsonArray minTemps = daily.getAsJsonArray("temperature_2m_min");
            JsonArray rain = daily.getAsJsonArray("precipitation_probability_max");

            // Initialise array to hold weather data
            int numDays = dates.size();
            data.dailyDates = new String[numDays];
            data.dailyMaxTemps = new double[numDays];
            data.dailyMinTemps = new double[numDays];
            data.rainChance = new double[numDays];

            // Populate WeatherData arrays for each day
            for (int i = 0; i < numDays; i++) {
                data.dailyDates[i] = dates.get(i).getAsString();
                data.dailyMaxTemps[i] = maxTemps.get(i).getAsDouble();
                data.dailyMinTemps[i] = minTemps.get(i).getAsDouble();
                data.rainChance[i] = rain.get(i).getAsDouble();
            }

            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
