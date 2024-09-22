package com.aminfaruq.dicodingevent.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aminfaruq.dicodingevent.data.response.EventDetail
import com.aminfaruq.dicodingevent.databinding.FinishedItemBinding
import com.aminfaruq.dicodingevent.databinding.UpcomingItemBinding
import com.bumptech.glide.Glide

class UpcomingItemAdapter (private val listEvents: List<EventDetail>) : RecyclerView.Adapter<UpcomingItemAdapter.ViewHolder>() {
    class ViewHolder(val binding: UpcomingItemBinding):  RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            UpcomingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listEvents.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listEvents[position]
        holder.binding.tvUpcomingTitle.text = item.name

        Glide.with(holder.itemView.context)
            .load(item.imageLogo)
            .into(holder.binding.imgUpcomingItem)
    }
}

class FinishedItemAdapter (private val listEvents: List<EventDetail>) : RecyclerView.Adapter<FinishedItemAdapter.ViewHolder>() {
    class ViewHolder(val binding: FinishedItemBinding):  RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FinishedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listEvents.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listEvents[position]
        holder.binding.tvTitleFinished.text = item.name
        holder.binding.tvDescFinished.text = item.name

        Glide.with(holder.itemView.context)
            .load(item.imageLogo)
            .into(holder.binding.imgFinishedItem)
    }
}
/*
*
*
class HomeViewHolderUpcoming(val binding: UpcomingSectionBinding) :
    RecyclerView.ViewHolder(binding.root)

class HomeViewHolderFinished(val binding: FinishedSectionBinding) :
    RecyclerView.ViewHolder(binding.root)

* */