package com.example.user.allison.Util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.allison.R;
import com.example.user.allison.WeatherParser;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    Cursor cursor;
    public WeatherAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WeatherViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.weather_view, parent, false));
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {

        if( cursor.moveToPosition(position)) {
            holder.dateText.setText(cursor.getString(0));
            holder.timeText.setText(cursor.getString(1));
            holder.locText.setText(cursor.getString(2));
            holder.tempText.setText(cursor.getString(3));
            holder.pressureText.setText(cursor.getString(4));
            holder.humText.setText(cursor.getString(5));
            holder.descText.setText(cursor.getString(6));
        }



    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {

        TextView dateText, locText, tempText, humText, timeText, descText, pressureText;
        public WeatherViewHolder(View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.weather_view_date);
            timeText = itemView.findViewById(R.id.weather_view_time);
            locText = itemView.findViewById(R.id.weather_view_loc);
            tempText = itemView.findViewById(R.id.weather_view_temp);
            pressureText = itemView.findViewById(R.id.weather_view_pressure);
            humText = itemView.findViewById(R.id.weather_view_hum);
            descText = itemView.findViewById(R.id.weather_view_desc);
        }
    }
}
