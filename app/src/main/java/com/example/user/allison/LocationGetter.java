package com.example.user.allison;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class LocationGetter extends AsyncTask<Object, Void, Object[]> {
    private final String API_KEY = "AIzaSyCOtroLBbVGOoU-_KOvpoRKhKEftHlxOxA";
    private boolean isCel;

    public LocationGetter(boolean isCel) {


        this.isCel = isCel;
    }

    @Override
    protected Object[] doInBackground(Object... params) {
        Pair<String, String> location = new Pair<>(Locations.latiture[0], Locations.longitude[0]);
        try {
            Log.i("Sagar", "https://maps.googleapis.com/maps/api/geocode/json?address="+params[0].toString()+"&key="+API_KEY);
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+params[0].toString()+"&key="+API_KEY);
            Log.i("Sagar", "ABC");
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            Log.i("Sagar", "DEF");

            Log.i("Sagar", params[0].toString());
            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
            }

            urlConnection.disconnect();
            Log.i("Sagar", "GHI");
            JSONObject json = new JSONObject(builder.toString());
            JSONArray jArray = json.optJSONArray("results");
            json = jArray.optJSONObject(0);
            json = json.optJSONObject("geometry");
            json = json.optJSONObject("location");
            String lat = String.valueOf(json.optDouble("lat"));
            String lng = String.valueOf(json.optDouble("lng"));
            Log.i("Sagar", "JKL");
            location = new Pair<String, String>(lat, lng);
        } catch (IOException | JSONException e) {
            Log.i("Sagar","Hello");
            e.printStackTrace();
        }
        Object[] returnObj = new Object[] {location, params[1]};
        return returnObj;
    }

    @Override
    protected void onPostExecute(Object[] ress) {
        Log.i("Sagar", "Entered");
        Pair<String, String> loc = (Pair<String, String>) ress[0];
        Log.i("Sagar", loc.toString());
        Context con = (Context) ress[1];
        String lat = loc.first;
        String lon= loc.second;
        Log.i("Sagar", "Came here");
        Intent intent = new Intent(con, SelectedCityActivity.class);
        intent.putExtra(SelectedCityActivity.IS_CEL, isCel);
        intent.putExtra(SelectedCityActivity.LAT, lat);
        intent.putExtra(SelectedCityActivity.LONG, lon);
        con.startActivity(intent);
    }
}
