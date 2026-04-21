package com.snms.app.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.snms.app.databinding.ActivitySettingsBinding
import com.snms.app.utils.PermissionUtils
import com.snms.app.utils.PrefsUtils
import com.snms.app.viewmodel.NotificationViewModel
import com.snms.app.viewmodel.NotificationViewModelFactory

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: NotificationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Settings"

        viewModel = ViewModelProvider(
            this,
            NotificationViewModelFactory(application)
        )[NotificationViewModel::class.java]

        // Restore saved focus mode state
        binding.switchFocusMode.isChecked = PrefsUtils.getFocusMode(this)
        viewModel.setFocusMode(binding.switchFocusMode.isChecked)

        binding.btnNotificationAccess.setOnClickListener {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }

        binding.switchFocusMode.setOnCheckedChangeListener { _, isChecked ->
            PrefsUtils.setFocusMode(this, isChecked)
            viewModel.setFocusMode(isChecked)
        }
    }

    override fun onResume() {
        super.onResume()
        val enabled = PermissionUtils.isNotificationListenerEnabled(this)
        binding.tvPermissionStatus.text =
            if (enabled) "✅ Permission Granted" else "❌ Permission Not Granted"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
