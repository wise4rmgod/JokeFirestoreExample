package com.dev.datastoreexample

import android.content.Context
import android.content.Intent
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.work.*
import com.dev.datastoreexample.adapter.Adapter
import com.dev.datastoreexample.model.JokesModel
import com.dev.datastoreexample.model.TestToast
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    val db = Firebase.firestore

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GetJokes()
        // getDate()
        getworkmanager()
        savebtn.setOnClickListener {
            //  SaveToDb(jokename.text.toString())

            startActivity(Intent(this, MainActivity2::class.java))

        }

    }

    fun SaveToDb(jokename: String) {
        val user = hashMapOf(
            "joke" to jokename,
        )

        db.collection("Jokes").add(user)
            .addOnSuccessListener {

                Toast.makeText(application, "Saved Successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(application, "Not Successful", Toast.LENGTH_SHORT).show()
            }
    }

    fun GetJokes() {

        db.collection("Jokes").addSnapshotListener { snapshot, e ->

            val getjokes = ArrayList<JokesModel?>()

            for (document in snapshot!!) {

                getjokes.add(
                    JokesModel(
                        document.getString("joke")
                    )
                )
                val ty = Adapter(getjokes, applicationContext)
                recy.adapter = ty
                ty.notifyDataSetChanged()

            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getDate() {
        val dt = Date()
        val calendar = Calendar.getInstance()
        val today = calendar.time
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val tomorrow = calendar.time

        val dateFormat: DateFormat = SimpleDateFormat("dd-MMM-yyyy")
        val todayAsString: String = dateFormat.format(today)
        val tomorrowAsString: String = dateFormat.format(tomorrow)
        // (1)
        val pref = getSharedPreferences("shared", Context.MODE_PRIVATE)// (2)
        val editor = pref.edit()
        editor.putString("date", tomorrowAsString)
        // editor.putLong("ty", today.toString().toLong())
        editor.putString("tdate", "27-Sep-2020")
        editor.apply()
        val getdate = pref.getString("tdate", "")

        when (getdate) {
            todayAsString -> {
                editor.putString("tdate", "28-Sep-2020")
                Toast.makeText(this, getdate, Toast.LENGTH_SHORT).show()
            }
            else -> {
            }
        }


    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun getworkmanager() {
        val workManager = WorkManager.getInstance(applicationContext)
        // Create a Constraints object that defines when the task should run
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
// ...then create a OneTimeWorkRequest that uses those constraints
        val ttt = PeriodicWorkRequest.Builder(Mywork::class.java, 2, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()
        workManager.enqueue(ttt)
        // TestToast().toast(applicationContext)
    }

}