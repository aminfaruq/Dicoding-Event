package com.aminfaruq.dicodingevent.ui.finished

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
import com.google.android.material.bottomnavigation.BottomNavigationView

class FinishedFragment : Fragment() {

    private val viewModel by viewModels<FinishedViewModel>()
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

        viewModel.listFinished.observe(viewLifecycleOwner) {
            setupList(it)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        binding.rvFinished.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // Check if the user has scrolled to the bottom of the RecyclerView
                if (dy > 0 && recyclerView.canScrollVertically(1)) {
                    // Hide the bottom navigation bar
                    (activity as MainActivity).findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility =
                        View.GONE
                } else {
                    // Show bottom navigation bar
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
        adapter = FinishedItemAdapter(list)
        binding.rvFinished.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.finishedLoadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
