package com.dev.datastoreexample

import android.content.Context
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi

class MainActivity2 : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val calendar = Calendar.getInstance()
        val today = calendar.time
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val tomorrow = calendar.time
        val dateFormat: DateFormat = SimpleDateFormat("dd-MMM-yyyy")
        val todayAsString: String = dateFormat.format(today)
        val tomorrowAsString: String = dateFormat.format(tomorrow)
        // (1)
        val pref = getSharedPreferences("shared", Context.MODE_PRIVATE)  // (2)
        val editor = pref.edit()


        val getdate = pref.getString("tdate", "")
        editor.putString("tdate", "hthththth")
        editor.apply()
        Toast.makeText(this, getdate, Toast.LENGTH_SHORT).show()
        if (getdate == todayAsString) {

            editor.putString("tdate", "hthththth")

        }
    }
}