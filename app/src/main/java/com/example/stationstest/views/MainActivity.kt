package com.example.stationstest.views


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.widget.SearchView


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stationstest.interfaces.ApiInterface
import com.example.stationstest.R
import com.example.stationstest.adapters.Adapter
import com.example.stationstest.model.StationDataItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity(){


    lateinit private var adapter: Adapter
    lateinit private var linearLayoutManager: LinearLayoutManager

    lateinit private var recyclerView: RecyclerView
    lateinit private var searchView: SearchView

    companion object{
        lateinit var responseBody: List<StationDataItem>

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getStetionData()
        LaunchRecyclerView()
        search()


    }

    private fun LaunchRecyclerView() {
        recyclerView = findViewById(R.id.ReccyclerView_Stations)
        recyclerView.setHasFixedSize(true)

        searchView = findViewById(R.id.searchView)

        linearLayoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = linearLayoutManager

    }

    private var displayList = mutableListOf<StationDataItem>()

    private fun search() {


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //displayList.addAll(responseBody)
                if (newText.isNotEmpty()) {
                    displayList.clear()
                    var search = newText.toLowerCase(Locale.getDefault() )
                    for(f in responseBody){

                        if(f.city.name.contains(search)){
                            displayList.add(f)
                        }

                        recyclerView.adapter!!.notifyDataSetChanged()

                    }
                }else{
                    displayList.clear()
                    displayList.addAll(responseBody)
                }
                recyclerView.adapter!!.notifyDataSetChanged()


                return true
            }
        })

    }


    private fun getStetionData() {

        //var IdText: TextView = findViewById(R.id.Id)

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.gios.gov.pl/")
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<StationDataItem>?> {
            override fun onResponse(
                call: Call<List<StationDataItem>?>,
                response: Response<List<StationDataItem>?>
            ) {
               responseBody = response.body()!!

                adapter = Adapter(baseContext, responseBody)
                adapter.notifyDataSetChanged()
                recyclerView.adapter = adapter

                adapter.setOnItemClickListener(object : Adapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        
                        val intent = Intent(baseContext, info_station::class.java)
                            intent.putExtra("id", position.toString())

                        baseContext.startActivity(intent)
                    }


                })
            }

            override fun onFailure(call: Call<List<StationDataItem>?>, t: Throwable) {
                d("LOG", "onFailure: " + t.message)
            }
        })




    }
}