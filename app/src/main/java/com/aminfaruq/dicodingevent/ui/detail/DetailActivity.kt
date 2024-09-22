package com.aminfaruq.dicodingevent.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.aminfaruq.dicodingevent.databinding.ActivityDetailBinding
import com.aminfaruq.dicodingevent.utils.ErrorDialog
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

    companion object {
        const val EXTRA_ID = "ID"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(EXTRA_ID, 0)

        viewModel.requestDetail(id)
        viewModel.eventDetail.observe(this) {
            Glide.with(this)
                .load(it.mediaCover)
                .into(binding.imageCover)

            binding.eventName.text = it.name
            binding.ownerName.text = it.ownerName
            binding.beginTime.text = it.beginTime
            binding.quota.text = "Quota: ${it.quota.toString()}"
            binding.description.text = HtmlCompat.fromHtml(
                it.description.toString(),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.isError.observe(this) {
            if (it) {
                val errorDialog = ErrorDialog()
                errorDialog.setRetryCallback(object : ErrorDialog.RetryCallback {
                    override fun onRetry() {
                        // Handle the retry event here
                        errorDialog.dismiss()
                        viewModel.requestDetail(id)
                    }
                })
                errorDialog.show(supportFragmentManager, "ErrorDialog")
            }
        }

        binding.openLinkButton .setOnClickListener {

        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.openLinkButton.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.detailLoadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
