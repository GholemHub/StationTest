package com.example.stationstest.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.stationstest.R
import com.example.stationstest.views.MainActivity
import com.example.stationstest.views.MainActivity.Companion.responseBody

class info_station : AppCompatActivity() {

    lateinit var tv_id: TextView
    lateinit var tv_stationName: TextView
    lateinit var tv_cityName: TextView
    lateinit var btn_back: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_station)

        tv_id =  findViewById(R.id.AIS_tv_id)
        tv_stationName = findViewById(R.id.AIS_tv_addressStreet)
        tv_cityName = findViewById(R.id.AIS_tv_cityName)

        BtnLitener()

        val bundle = intent.extras

        var position = 0;
        if (bundle != null) {
            var StrPosition = bundle.getString("id")
            if (StrPosition != null) {
                position = StrPosition.toInt()
            }else{
                Toast.makeText(baseContext, "the putExtra == $StrPosition", Toast.LENGTH_SHORT).show()
            }
        }
        SetText(position)
    }

    private fun SetText(position: Int) {

        tv_id.setText(responseBody[position].id.toString())
        tv_stationName.setText(responseBody[position].addressStreet.toString())
        tv_cityName.setText(responseBody[position].city.name.toString())

    }

    fun BtnLitener(){
        btn_back = findViewById(R.id.btn_back)
            btn_back.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

    }

}
