package com.aminfaruq.dicodingevent.ui.upcoming

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aminfaruq.dicodingevent.data.response.EventDetail
import com.aminfaruq.dicodingevent.databinding.UpcomingItemBinding
import com.aminfaruq.dicodingevent.ui.home.OnItemClickListener
import com.bumptech.glide.Glide

class UpcomingItemAdapter(
    private val listEvents: List<EventDetail>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<UpcomingItemAdapter.ViewHolder>() {
    class ViewHolder(val binding: UpcomingItemBinding) : RecyclerView.ViewHolder(binding.root)

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

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(item.id ?: 0)
        }
    }
}
