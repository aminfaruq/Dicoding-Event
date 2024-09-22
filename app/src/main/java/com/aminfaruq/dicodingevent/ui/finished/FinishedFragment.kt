package com.aminfaruq.dicodingevent.ui.finished

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aminfaruq.dicodingevent.R

class FinishedFragment : Fragment() {

    companion object {
        fun newInstance() = FinishedFragment()
    }

    private lateinit var viewModel: FinishedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_finished, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FinishedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}