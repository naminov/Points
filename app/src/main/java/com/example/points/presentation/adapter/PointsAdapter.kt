package com.example.points.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.points.databinding.PointItemBinding
import com.example.points.domain.model.Point

class PointsAdapter : RecyclerView.Adapter<PointsAdapter.PointViewHolder>() {
    var items: List<Point> = listOf()
        set(value) {
            val callback = DefaultDiffCallback(
                oldList = field,
                newList = value,
            )
            field = value
            val result = DiffUtil.calculateDiff(callback)
            result.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointViewHolder {
        val binding = PointItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return PointViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PointViewHolder(
        private val binding: PointItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(point: Point) {
            binding.xTv.text = point.x.toString()
            binding.yTv.text = point.y.toString()
        }
    }
}