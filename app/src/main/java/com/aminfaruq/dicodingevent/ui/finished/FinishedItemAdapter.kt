package com.aminfaruq.dicodingevent.ui.finished

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aminfaruq.dicodingevent.data.response.EventDetail
import com.aminfaruq.dicodingevent.databinding.EmptyViewBinding
import com.aminfaruq.dicodingevent.databinding.FinishedItemBinding
import com.aminfaruq.dicodingevent.ui.home.OnItemClickListener
import com.bumptech.glide.Glide

class FinishedItemAdapter(
    private val listEvents: List<EventDetail>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class ViewHolder(val binding: FinishedItemBinding) : RecyclerView.ViewHolder(binding.root)
    class EmptyViewHolder(binding: EmptyViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            EMPTY_VIEW_TYPE -> {
                val binding =
                    EmptyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                EmptyViewHolder(binding)
            }

            ITEM_VIEW_TYPE -> {
                val binding =
                    FinishedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ViewHolder(binding)
            }

            else -> throw RuntimeException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val item = listEvents[position]
                holder.binding.tvTitleFinished.text = item.name
                holder.binding.tvDescFinished.text = item.name

                Glide.with(holder.itemView.context)
                    .load(item.imageLogo)
                    .into(holder.binding.imgFinishedItem)

                holder.itemView.setOnClickListener {
                    onItemClickListener.onItemClick(item.id ?: 0)
                }
            }

            is EmptyViewHolder -> {}
        }
    }

    override fun getItemCount(): Int {
        return if (listEvents.isEmpty()) 1 else listEvents.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (listEvents.isEmpty()) EMPTY_VIEW_TYPE else ITEM_VIEW_TYPE
    }

    companion object {
        private const val EMPTY_VIEW_TYPE = 0
        private const val ITEM_VIEW_TYPE = 1
    }
}
