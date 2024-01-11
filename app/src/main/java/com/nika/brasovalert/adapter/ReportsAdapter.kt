package com.nika.brasovalert.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nika.brasovalert.R
import com.nika.brasovalert.databinding.AlertsCardviewBinding
import com.nika.brasovalert.remote.DataXXX

class ReportsAdapter():RecyclerView.Adapter<ReportsAdapter.ReportsViewHolder>() {

    var reportsList:List<DataXXX> = mutableListOf()
    class ReportsViewHolder(val binding :AlertsCardviewBinding):ViewHolder(binding.root)

    fun setReportList(reportsList:List<DataXXX>){
        this.reportsList=reportsList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportsViewHolder {
        return ReportsViewHolder(AlertsCardviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {

        Log.d("TAAG", "${reportsList.size}")
       return reportsList.size

    }

    override fun onBindViewHolder(holder: ReportsViewHolder, position: Int) {

        val report=reportsList[position]
        holder.binding.imageView4.setImageResource(R.drawable.alert)
        holder.binding.tvProfile.text="${report.author.firstName.first()}${report.author.lastName.first()}"
        holder.binding.tvTitle.text="${report.name}"
        holder.binding.rvName.text="${report.author.firstName} ${report.author.lastName}"
        holder.binding.tvDate.text="${report.createdAt}"
        holder.binding.tvAddress.text="${report.description}"
    }
}