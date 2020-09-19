package com.dev.datastoreexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.dev.datastoreexample.adapter.Adapter
import com.dev.datastoreexample.model.JokesModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GetJokes()

        savebtn.setOnClickListener {
            SaveToDb(jokename.text.toString())

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

}