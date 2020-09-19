package com.dev.datastoreexample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.datastoreexample.R
import com.dev.datastoreexample.model.JokesModel


class Adapter(
    private var dataList: ArrayList<JokesModel?>,
    private val context: Context
) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.list_of_jokes,
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel = dataList.get(position)
        holder.listj.text = dataModel?.joke

    }

    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        var listj: TextView = itemLayoutView.findViewById(R.id.listjoke)

    }


}
