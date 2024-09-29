package com.aminfaruq.dicodingevent.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.aminfaruq.dicodingevent.R
import com.aminfaruq.dicodingevent.data.response.EventDetail
import com.aminfaruq.dicodingevent.databinding.ActivityDetailBinding
import com.aminfaruq.dicodingevent.ui.ViewModelFactory
import com.aminfaruq.dicodingevent.utils.ErrorDialog
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var currentEventDetail: EventDetail
    private lateinit var viewModel: DetailViewModel
    private var isFavorite = false


    @SuppressLint("SetTextI18n", "QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Detail Event"
            setDisplayHomeAsUpEnabled(true)
        }

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val id = intent.getIntExtra(EXTRA_ID, 0)

        viewModel.requestDetail(id)
        viewModel.eventDetail.observe(this) { item ->
            currentEventDetail = item
            Glide.with(this)
                .load(item.mediaCover)
                .into(binding.imageCover)

            binding.eventName.text = item.name
            binding.ownerName.text = item.ownerName
            binding.beginTime.text = item.beginTime

            val quota = item.quota ?: 0
            val registrants = item.registrants ?: 0
            binding.quota.text = "Quota: ${quota - registrants}"
            binding.description.text = HtmlCompat.fromHtml(
                item.description.toString(),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )

            binding.openLinkButton.setOnClickListener {
                val url = item.link
                val openLinkIntent = Intent(Intent.ACTION_VIEW)
                openLinkIntent.data = Uri.parse(url)

                if (openLinkIntent.resolveActivity(packageManager) != null) {
                    startActivity(openLinkIntent)
                } else {
                    Toast.makeText(this, "There no Application cant open this link", Toast.LENGTH_SHORT).show()
                }
            }
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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)

        val favoriteItem = menu?.findItem(R.id.action_save)
        val eventId = intent.getIntExtra(EXTRA_ID, 0)

        viewModel.getEventById(eventId).observe(this) { favoriteEvent ->
            isFavorite = !favoriteEvent.isNullOrEmpty()
            if (isFavorite) {
                favoriteItem?.setIcon(R.drawable.baseline_bookmark_24)
            } else {
                favoriteItem?.setIcon(R.drawable.baseline_bookmark_border_24)
            }
        }

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_save -> {
                if (isFavorite) {
                    currentEventDetail.id?.let { viewModel.removeFromFavorite(it) }
                    item.setIcon(R.drawable.baseline_bookmark_border_24)
                    Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addToFavorite(currentEventDetail)
                    item.setIcon(R.drawable.baseline_bookmark_24)
                    Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
                }
                isFavorite = !isFavorite
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.openLinkButton.visibility = View.GONE
        } else {
            binding.openLinkButton.visibility = View.VISIBLE
        }
        binding.detailLoadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_ID = "ID"
    }
}
