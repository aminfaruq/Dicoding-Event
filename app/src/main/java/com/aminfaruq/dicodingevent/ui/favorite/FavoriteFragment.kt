package com.aminfaruq.dicodingevent.ui.favorite

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
import com.aminfaruq.dicodingevent.databinding.FragmentFavoriteBinding
import com.aminfaruq.dicodingevent.ui.ViewModelFactory
import com.aminfaruq.dicodingevent.ui.detail.DetailActivity
import com.aminfaruq.dicodingevent.ui.finished.FinishedItemAdapter
import com.aminfaruq.dicodingevent.ui.home.OnItemClickListener
import com.aminfaruq.dicodingevent.utils.hideBottomNavigationOnScroll

class FavoriteFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var adapter: FinishedItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorite.layoutManager = layoutManager

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: FavoriteViewModel by viewModels {
            factory
        }

        viewModel.getFavoriteEvents().observe(viewLifecycleOwner) {
            setupList(it)
        }

        binding.rvFavorite.hideBottomNavigationOnScroll(
            activity as MainActivity,
            R.id.bottomNavigationView
        )
    }

    private fun setupList(list: List<EventDetail>) {
        adapter = FinishedItemAdapter(list, this)
        binding.rvFavorite.adapter = adapter
    }

    override fun onItemClick(id: Int) {
        val moveIntoDetail = Intent(requireContext(), DetailActivity::class.java)
        moveIntoDetail.putExtra(DetailActivity.EXTRA_ID, id)
        startActivity(moveIntoDetail)
    }
}