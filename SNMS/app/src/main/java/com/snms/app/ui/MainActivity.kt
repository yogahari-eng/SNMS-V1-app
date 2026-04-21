package com.snms.app.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.snms.app.R
import com.snms.app.databinding.ActivityMainBinding
import com.snms.app.utils.PermissionUtils
import com.snms.app.viewmodel.NotificationViewModel
import com.snms.app.viewmodel.NotificationViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NotificationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(
            this,
            NotificationViewModelFactory(application)
        )[NotificationViewModel::class.java]

        setupObservers()
        setupClickListeners()
        checkPermission()
    }

    override fun onResume() {
        super.onResume()
        checkPermission()
    }

    private fun setupObservers() {
        viewModel.totalCount.observe(this) { count ->
            binding.tvTotalCount.text = (count ?: 0).toString()
        }
        viewModel.highCount.observe(this) { count ->
            binding.tvHighCount.text = (count ?: 0).toString()
        }
        viewModel.mediumCount.observe(this) { count ->
            binding.tvMediumCount.text = (count ?: 0).toString()
        }
        viewModel.lowCount.observe(this) { count ->
            binding.tvLowCount.text = (count ?: 0).toString()
        }
    }

    private fun setupClickListeners() {
        binding.btnViewAll.setOnClickListener {
            startActivity(Intent(this, NotificationListActivity::class.java))
        }
        binding.btnViewHigh.setOnClickListener {
            startActivity(
                Intent(this, NotificationListActivity::class.java)
                    .putExtra(NotificationListActivity.EXTRA_FILTER, "HIGH")
            )
        }
        binding.btnViewMedium.setOnClickListener {
            startActivity(
                Intent(this, NotificationListActivity::class.java)
                    .putExtra(NotificationListActivity.EXTRA_FILTER, "MEDIUM")
            )
        }
        binding.btnViewLow.setOnClickListener {
            startActivity(
                Intent(this, NotificationListActivity::class.java)
                    .putExtra(NotificationListActivity.EXTRA_FILTER, "LOW")
            )
        }
        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        binding.btnEnableAccess.setOnClickListener {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }
        binding.btnClearAll.setOnClickListener {
            viewModel.clearAll()
            Toast.makeText(this, "All notifications cleared", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermission() {
        val enabled = PermissionUtils.isNotificationListenerEnabled(this)
        binding.bannerPermission.visibility =
            if (enabled) android.view.View.GONE else android.view.View.VISIBLE
        binding.statusDot.setImageResource(
            if (enabled) R.drawable.dot_green else R.drawable.dot_red
        )
        binding.tvStatus.text =
            if (enabled) getString(R.string.status_active) else getString(R.string.status_inactive)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
