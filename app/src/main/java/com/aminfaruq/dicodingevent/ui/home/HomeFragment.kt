package com.aminfaruq.dicodingevent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aminfaruq.dicodingevent.data.response.EventDetail
import com.aminfaruq.dicodingevent.databinding.FragmentHomeBinding
import com.aminfaruq.dicodingevent.ui.home.adapter.HomeAdapter

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvHome.layoutManager = LinearLayoutManager(requireContext())
        viewModel.listUpcoming.observe(viewLifecycleOwner) { upcomingEvents ->
            viewModel.listFinished.observe(viewLifecycleOwner) { finishedEvents ->
                setupList(upcomingEvents, finishedEvents)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun setupList(upcomingEvents: List<EventDetail>, finishedEvents: List<EventDetail>) {
        adapter = HomeAdapter(upcomingEvents, finishedEvents)
        binding.rvHome.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.homeLoadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}