package com.aminfaruq.dicodingevent.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aminfaruq.dicodingevent.MainActivity
import com.aminfaruq.dicodingevent.R
import com.aminfaruq.dicodingevent.data.response.EventDetail
import com.aminfaruq.dicodingevent.databinding.FragmentHomeBinding
import com.aminfaruq.dicodingevent.ui.ViewModelFactory
import com.aminfaruq.dicodingevent.ui.detail.DetailActivity
import com.aminfaruq.dicodingevent.utils.ErrorDialog
import com.aminfaruq.dicodingevent.utils.hideBottomNavigationOnScroll

class HomeFragment : Fragment() , OnItemClickListener{

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

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: HomeViewModel by viewModels {
            factory
        }

        viewModel.listUpcoming.observe(viewLifecycleOwner) { upcomingEvents ->
            viewModel.listFinished.observe(viewLifecycleOwner) { finishedEvents ->
                setupList(upcomingEvents, finishedEvents)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.isError.observe(viewLifecycleOwner) {
            if (it) {
                val errorDialog = ErrorDialog()
                errorDialog.setRetryCallback(object : ErrorDialog.RetryCallback {
                    override fun onRetry() {
                        // Handle the retry event here
                        errorDialog.dismiss()
                        viewModel.requestUpcoming()
                        viewModel.requestFinished()
                    }
                })
                errorDialog.show(parentFragmentManager, "ErrorDialog")
            }
        }

        binding.rvHome.hideBottomNavigationOnScroll(activity as MainActivity, R.id.bottomNavigationView)

    }

    private fun setupList(upcomingEvents: List<EventDetail>, finishedEvents: List<EventDetail>) {
        adapter = HomeAdapter(upcomingEvents, finishedEvents, this)
        binding.rvHome.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.homeLoadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onItemClick(id: Int) {
        val moveIntoDetail = Intent(requireContext(), DetailActivity::class.java)
        moveIntoDetail.putExtra(DetailActivity.EXTRA_ID, id)
        startActivity(moveIntoDetail)
    }
}
