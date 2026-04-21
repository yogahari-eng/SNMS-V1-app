package com.snms.app.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.snms.app.adapters.NotificationAdapter
import com.snms.app.database.Priority
import com.snms.app.databinding.ActivityNotificationListBinding
import com.snms.app.viewmodel.NotificationViewModel
import com.snms.app.viewmodel.NotificationViewModelFactory

class NotificationListActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_FILTER = "filter"
    }

    private lateinit var binding: ActivityNotificationListBinding
    private lateinit var viewModel: NotificationViewModel
    private lateinit var adapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val filter = intent.getStringExtra(EXTRA_FILTER)
        supportActionBar?.title = when (filter) {
            "HIGH" -> "🔴 High Priority"
            "MEDIUM" -> "🟡 Medium Priority"
            "LOW" -> "🟢 Low Priority"
            else -> "All Notifications"
        }

        viewModel = ViewModelProvider(
            this,
            NotificationViewModelFactory(application)
        )[NotificationViewModel::class.java]

        adapter = NotificationAdapter(
            onMarkRead = { id -> viewModel.markAsRead(id) },
            onDelete = { id -> viewModel.delete(id) }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        val liveData = when (filter) {
            "HIGH" -> viewModel.getByPriority(Priority.HIGH)
            "MEDIUM" -> viewModel.getByPriority(Priority.MEDIUM)
            "LOW" -> viewModel.getByPriority(Priority.LOW)
            else -> viewModel.allNotifications
        }

        liveData.observe(this) { list ->
            adapter.submitList(list)
            binding.emptyView.visibility = if (list.isNullOrEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
