package com.aminfaruq.dicodingevent.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aminfaruq.dicodingevent.data.response.EventDetail
import com.aminfaruq.dicodingevent.databinding.FinishedSectionBinding
import com.aminfaruq.dicodingevent.databinding.UpcomingSectionBinding
import com.aminfaruq.dicodingevent.ui.finished.FinishedItemAdapter
import com.aminfaruq.dicodingevent.ui.upcoming.UpcomingItemAdapter

interface OnItemClickListener {
    fun onItemClick(id: Int)
}

class HomeAdapter(
    private val upcomingEvents: List<EventDetail>,
    private val finishedEvents: List<EventDetail>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_UPCOMING -> {
                val binding = UpcomingSectionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HomeViewHolderUpcoming(binding)
            }

            VIEW_TYPE_FINISHED -> {
                val binding = FinishedSectionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HomeViewHolderFinished(binding)
            }

            else -> throw RuntimeException("Unknown view type")
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeViewHolderUpcoming -> {
                holder.binding.rvSectionUpcoming.layoutManager = LinearLayoutManager(
                    holder.itemView.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                holder.binding.rvSectionUpcoming.adapter =
                    UpcomingItemAdapter(upcomingEvents, onItemClickListener)
            }

            is HomeViewHolderFinished -> {
                holder.binding.rvFinishedSection.layoutManager = LinearLayoutManager(
                    holder.itemView.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                holder.binding.rvFinishedSection.adapter =
                    FinishedItemAdapter(finishedEvents, onItemClickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_UPCOMING
            1 -> VIEW_TYPE_FINISHED
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

    companion object {
        const val VIEW_TYPE_UPCOMING = 0
        const val VIEW_TYPE_FINISHED = 1
    }
}

class HomeViewHolderUpcoming(val binding: UpcomingSectionBinding) :
    RecyclerView.ViewHolder(binding.root)

class HomeViewHolderFinished(val binding: FinishedSectionBinding) :
    RecyclerView.ViewHolder(binding.root)
