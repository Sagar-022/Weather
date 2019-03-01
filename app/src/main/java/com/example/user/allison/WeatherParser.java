package com.example.user.allison;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class WeatherParser extends AsyncTask<String, Void, String[]> {

    private TextView tempText, humText, cityText, dateText, timeText;
    private static final Integer DESCRIPTION = 0;
    private static final Integer TEMPERATURE = 1;
    private static final Integer PRESSURE = 2;
    private static final Integer HUMIDITY = 3;
    private static final Integer NAME = 4;

    public static final String[] COLUMNS = {
            "date",
            "time",
            "location",
            "tempurature",
            "pressure",
            "humidity",
            "description"
    };

    private SQLiteDatabase db;
    private Boolean isCel;

    public WeatherParser(Boolean isCel, TextView tempText, TextView humText, TextView cityText, TextView dateText, TextView timeText, SQLiteDatabase db) {
        this.tempText = tempText;
        this.humText = humText;
        this.cityText = cityText;
        this.dateText = dateText;
        this.timeText = timeText;
        this.db = db;
        this.isCel = isCel;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected String[] doInBackground(String... strings) {
        String[] weather = new String[6];
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
            }

            JSONObject topLevel = new JSONObject(builder.toString());
            String wDesc = topLevel.getJSONArray("weather").getJSONObject(0).getString("description");
            weather[0] = wDesc;



            JSONObject wMain = topLevel.getJSONObject("main");
            weather[1] = String.valueOf(wMain.getInt("temp") - 273);
            weather[2] = wMain.getString("pressure");
            weather[3] = wMain.getString("humidity");
            weather[4] = topLevel.getString("name");

            urlConnection.disconnect();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return weather;
    }


    @Override
    protected void onPostExecute(String[] s) {
        if(!isCel) {
            s[1] = String.valueOf(9*Double.parseDouble(s[1])/5d + 32d);
        }

        tempText.setText(s[1]);
        humText.setText(s[3]);
        cityText.setText(s[4]);

        Calendar cal = Calendar.getInstance();
        String date = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH);
        dateText.setText(date);
        String time = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
        timeText.setText(time);

        db.execSQL("CREATE TABLE IF NOT EXISTS weather(" +
                "date TEXT," +
                "time TEXT, " +
                "location TEXT, " +
                "tempurature REAL, " +
                "pressure REAL, " +
                "humidity REAL, " +
                "description TEXT" +
                ");");
        db.execSQL("INSERT INTO weather VALUES('"+
                date+ "','"+
                time+ "','"+
                s[NAME]+ "','" +
                s[TEMPERATURE]+ "','" +
                s[PRESSURE]+ "','" +
                s[HUMIDITY]+ "','" +
                s[DESCRIPTION]+
                "');");
    }
}
