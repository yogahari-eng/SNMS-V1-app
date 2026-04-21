package com.snms.app.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.snms.app.R
import com.snms.app.database.NotificationEntity
import com.snms.app.database.Priority
import com.snms.app.databinding.ItemNotificationBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationAdapter(
    private val onMarkRead: (Long) -> Unit,
    private val onDelete: (Long) -> Unit
) : ListAdapter<NotificationEntity, NotificationAdapter.ViewHolder>(DIFF_CALLBACK) {

    private val timeFormatter = SimpleDateFormat("hh:mm a, dd MMM", Locale.getDefault())

    inner class ViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NotificationEntity) {
            binding.tvAppName.text = item.appName
            binding.tvTitle.text = item.title
            binding.tvContent.text = item.content.ifBlank { "No message" }
            binding.tvTimestamp.text = timeFormatter.format(Date(item.timestamp))

            // Priority badge
            val (label, colorRes) = when (item.priority) {
                Priority.HIGH -> "HIGH" to R.color.priority_high
                Priority.MEDIUM -> "MEDIUM" to R.color.priority_medium
                Priority.LOW -> "LOW" to R.color.priority_low
            }
            binding.tvPriority.text = label
            binding.tvPriority.backgroundTintList =
                ContextCompat.getColorStateList(binding.root.context, colorRes)

            // Dim if read
            binding.root.alpha = if (item.isRead) 0.55f else 1.0f

            binding.btnMarkRead.setOnClickListener { onMarkRead(item.id) }
            binding.btnDelete.setOnClickListener { onDelete(item.id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNotificationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NotificationEntity>() {
            override fun areItemsTheSame(old: NotificationEntity, new: NotificationEntity) =
                old.id == new.id

            override fun areContentsTheSame(old: NotificationEntity, new: NotificationEntity) =
                old == new
        }
    }
}
