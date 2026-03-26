package util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class weatherService {
    public static class WeatherData {
        public double currentTemp;
        public int humidity;
        public String[] dailyDates;
        public double[] dailyMaxTemps;
        public double[] dailyMinTemps;
        public int[] rainChance;
    }

    private static String makeRequest(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        Scanner scanner = new Scanner(connection.getInputStream());
        StringBuilder response = new StringBuilder();
        while (scanner.hasNextLine()) {
            response.append(scanner.nextLine());
        }
        scanner.close();
        return response.toString();
    }

    private static String getGeoHash(String city) throws Exception {
        String urlString = "https://api.weather.bom.gov.au/v1/locations?search="
                + city.replace(" ", "%20");
        String response = makeRequest(urlString);

        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        JsonArray data = json.getAsJsonArray("data");

        if (data.size() == 0) {
            return null;
        }

        return data.get(0).getAsJsonObject().get("geohash").getAsString();
    }

    public static WeatherData getWeather(String city) {
        try {
            String geohash = getGeoHash(city);
            if (geohash == null) {
                return null;
            }

            String obResponse = makeRequest("https://api.weather.bom.gov.au/v1/locations/" + geohash + "/observations");

            String forecastResponse = makeRequest(
                    "https://api.weather.bom.gov.au/v1/locations/" + geohash + "/forecasts/daily");

            WeatherData data = new WeatherData();

            JsonObject obsJson = JsonParser.parseString(obResponse).getAsJsonObject();
            JsonObject obs = obsJson.getAsJsonObject("data");

            data.currentTemp = obs.get("temp").getAsDouble();
            data.humidity = obs.get("humidity").getAsInt();

            JsonObject forecastJson = JsonParser.parseString(forecastResponse).getAsJsonObject();
            JsonArray days = forecastJson.getAsJsonArray("data");
            int numDays = 7;
            data.dailyDates = new String[numDays];
            data.dailyMaxTemps = new double[numDays];
            data.dailyMinTemps = new double[numDays];
            data.rainChance = new int[numDays];

            for (int i = 0; i < numDays; i++) {
                JsonObject day = days.get(i).getAsJsonObject();
                data.dailyDates[i] = day.get("date").getAsString();
                data.dailyMaxTemps[i] = day.get("max_temp").getAsDouble();
                data.dailyMinTemps[i] = day.get("min_temp").getAsDouble();
                data.rainChance[i] = day.get("rain_chance").getAsInt();
            }
            return data;

        }

        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
