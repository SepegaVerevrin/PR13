package com.example.pr13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ShowSupernovaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_country)

        val supernova = intent.getSerializableExtra("SUPERNOVA") as Supernova
        findViewById<TextView>(R.id.name).text = supernova.name
    }
}

//class ShowCountryActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_show_country)
//
//        val country = intent.getSerializableExtra("COUNTRY") as Country
//        findViewById<TextView>(R.id.name).text = country.name
//        findViewById<TextView>(R.id.capital).text = country.capital
//        findViewById<TextView>(R.id.region).text = country.region
//        findViewById<TextView>(R.id.subregion).text = country.subregion
//        findViewById<TextView>(R.id.population).text = country.population.toString()
//    }
//}