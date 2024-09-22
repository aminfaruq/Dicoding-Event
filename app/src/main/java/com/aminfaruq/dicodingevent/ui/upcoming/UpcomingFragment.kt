package com.aminfaruq.dicodingevent.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aminfaruq.dicodingevent.MainActivity
import com.aminfaruq.dicodingevent.R
import com.aminfaruq.dicodingevent.data.response.EventDetail
import com.aminfaruq.dicodingevent.databinding.FragmentUpcomingBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class UpcomingFragment : Fragment() {

    private val viewModel by viewModels<UpcomingViewModel>()
    private lateinit var binding: FragmentUpcomingBinding
    private lateinit var adapter: UpcomingItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvUpcoming.layoutManager = layoutManager

        viewModel.listUpcoming.observe(viewLifecycleOwner) { upcomingEvents ->
            setupList(upcomingEvents)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        binding.rvUpcoming.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // Check if the user has scrolled to the bottom of the RecyclerView
                if (dy > 0 && recyclerView.canScrollVertically(1)) {
                    // Hide the bottom navigation bar
                    (activity as MainActivity).findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE
                } else if (dy < 0 && recyclerView.canScrollVertically(-1)) {
                    // Show the bottom navigation bar
                    (activity as MainActivity).findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setupList(list: List<EventDetail>) {
        adapter = UpcomingItemAdapter(list)
        binding.rvUpcoming.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.upcomingLoadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
