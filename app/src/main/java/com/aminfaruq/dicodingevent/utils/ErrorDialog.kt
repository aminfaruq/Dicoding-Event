package com.aminfaruq.dicodingevent.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.aminfaruq.dicodingevent.databinding.CustomDialogBinding

class ErrorDialog : DialogFragment() {

    private var _binding: CustomDialogBinding? = null
    private val binding get() = _binding

    // Define a callback interface
    interface RetryCallback {
        fun onRetry()
    }

    // Add a callback variable
    private var retryCallback: RetryCallback? = null

    // Set the callback
    fun setRetryCallback(callback: RetryCallback) {
        retryCallback = callback
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = CustomDialogBinding.inflate(inflater, container, false)

        val view = binding?.root

        binding?.retryButton?.setOnClickListener {
            // Call the callback when the retry button is clicked
            retryCallback?.onRetry()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
