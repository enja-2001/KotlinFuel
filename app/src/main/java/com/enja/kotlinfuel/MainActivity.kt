package com.enja.kotlinfuel

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val url = "https://api.weatherapi.com/v1/current.json"
    private val key = "2dc292196707415eab9134613212612"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        butWeather.setOnClickListener {
            val inputLocation = inputCityName.text.toString()
            if (inputLocation != null && inputLocation.isNotEmpty())
                getWeatherData(inputLocation)
        }
    }

    private fun getWeatherData(inputLocation : String){

        url.httpGet(listOf("key" to key, "q" to inputLocation)).responseString { request, response, result ->
            when(result){
                is Result.Success->{
                    val data = result.get()
                    val jsonObj = JSONObject(data)

                    val temp = jsonObj.getJSONObject("current").getString("temp_c")
                    val textCondition = jsonObj.getJSONObject("current").getJSONObject("condition").getString("text")

                    runOnUiThread {
                        tvWeatherStatus.text = textCondition
                        tvTemp.text = temp+"°"
                    }
                }
                is Result.Failure->{
                    runOnUiThread{
                        Toast.makeText(this, "Oops! Error occurred", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}