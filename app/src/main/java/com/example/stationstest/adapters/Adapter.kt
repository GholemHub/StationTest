package com.example.stationstest.adapters

import android.content.Context
import android.content.Intent
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.Filter

import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stationstest.R
import com.example.stationstest.model.StationDataItem
import com.example.stationstest.views.info_station
import java.util.Collections.addAll

class Adapter(var context: Context, var StationList: List<StationDataItem>):
    RecyclerView.Adapter<Adapter.ViewHolder>(), Filterable {

    private var StationListFull = StationList.toMutableList()



    private lateinit var clickListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        clickListener = listener


    }

    class ViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView) {
        var Station_id: TextView
        var Station_stationName: TextView

        init{

            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
            Station_id = itemView.findViewById(R.id.tv_id)
            Station_stationName = itemView.findViewById(R.id.tv_stationName)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.row_items, parent, false)


        return ViewHolder(itemView, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.Station_id.text = StationList[position].id.toString()
        holder.Station_stationName.text = StationList[position].city.name


    }

    override fun getItemCount(): Int {
        return StationList.size
    }

    override fun getFilter(): Filter {
        return filter
    }

    private var filter = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            var filterList = mutableListOf<StationDataItem>()

            if(p0 == null || p0.length == 0){
                filterList.addAll(StationListFull)
            }else{
                var filterPattern: String = p0.toString().toLowerCase().trim()

                for(f in StationListFull){
                    if(f.city.name.toLowerCase().contains(filterPattern)){
                        filterList.add(f)
                    }
                }
            }

            var results = FilterResults()
            results.values = filterList

            return results

        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {


        }
    }



}