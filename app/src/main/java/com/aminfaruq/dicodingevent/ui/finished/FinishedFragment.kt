package com.aminfaruq.dicodingevent.ui.finished

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aminfaruq.dicodingevent.MainActivity
import com.aminfaruq.dicodingevent.R
import com.aminfaruq.dicodingevent.data.response.EventDetail
import com.aminfaruq.dicodingevent.databinding.FragmentFinishedBinding
import com.aminfaruq.dicodingevent.ui.ViewModelFactory
import com.aminfaruq.dicodingevent.ui.detail.DetailActivity
import com.aminfaruq.dicodingevent.ui.home.OnItemClickListener
import com.aminfaruq.dicodingevent.utils.ErrorDialog
import com.google.android.material.bottomnavigation.BottomNavigationView

class FinishedFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentFinishedBinding
    private lateinit var adapter: FinishedItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFinished.layoutManager = layoutManager

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: FinishedViewModel by viewModels {
            factory
        }

        viewModel.listFinished.observe(viewLifecycleOwner) {
            setupList(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.isError.observe(viewLifecycleOwner) {
            if (it) {
                val errorDialog = ErrorDialog()
                errorDialog.setRetryCallback(object : ErrorDialog.RetryCallback {
                    override fun onRetry() {
                        errorDialog.dismiss()
                        viewModel.requestFinished(canSearch = true)
                    }
                })
                errorDialog.show(parentFragmentManager, "ErrorDialog")
            }
        }

        binding.rvFinished.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0 && recyclerView.canScrollVertically(1)) {
                    (activity as MainActivity).findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility =
                        View.GONE
                } else {
                    (activity as MainActivity).findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility =
                        View.VISIBLE
                }
            }
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    viewModel.requestFinished(canSearch = true)
                } else {
                    viewModel.requestFinished(-1, query , true)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewModel.requestFinished(canSearch = true)
                }
                return false
            }
        })
    }

    private fun setupList(list: List<EventDetail>) {
        adapter = FinishedItemAdapter(list, this)
        binding.rvFinished.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.finishedLoadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onItemClick(id: Int) {
        val moveIntoDetail = Intent(requireContext(), DetailActivity::class.java)
        moveIntoDetail.putExtra(DetailActivity.EXTRA_ID, id)
        startActivity(moveIntoDetail)
    }
}
