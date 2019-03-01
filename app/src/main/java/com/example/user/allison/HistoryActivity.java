package com.example.user.allison;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.example.user.allison.Util.WeatherAdapter;

public class HistoryActivity extends Activity {

    RecyclerView recyclerView;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        recyclerView = findViewById(R.id.recycler_view_history);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));

        SQLiteDatabase db = openOrCreateDatabase("weather", Context.MODE_PRIVATE, null);
        cursor = db.query("weather", WeatherParser.COLUMNS, null, null, null, null, null);
        WeatherAdapter adapter = new WeatherAdapter(cursor);
        recyclerView.setAdapter(adapter);


    }


    @Override
    protected void onStop() {
        super.onStop();
        cursor.close();
    }
}
