package com.foobles.kotlinnum.storage.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.foobles.kotlinnum.BaseFragment
import com.foobles.kotlinnum.R
import com.foobles.kotlinnum.databinding.StorageFragmentBinding
import com.foobles.kotlinnum.login.vm.LoginViewModel
import com.foobles.kotlinnum.nav.NavigationActivity
import com.foobles.kotlinnum.storage.adapter.StorageAdapter
import com.foobles.kotlinnum.storage.vm.StorageViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.StorageReference

class StorageFragment : BaseFragment() {
    private val storageViewModel by viewModels<StorageViewModel>()
    private lateinit var binding: StorageFragmentBinding
    private val loginViewModel by activityViewModels<LoginViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StorageFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun setList(): StorageAdapter {
        val storageAdapter = StorageAdapter { storageReference -> onItemClick(storageReference) }
        with(binding.recycleviewStorage) {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            adapter = storageAdapter
        }
        return storageAdapter
    }

    private fun onItemClick(storageReference: StorageReference) {
        Snackbar.make(
            binding.root,
            "Paid feature! Contact us to get this feature.",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val currentUser = loginViewModel.getCurrentUser()
        showProgressBar()
        val adapter = setList()
        storageViewModel.fetchFiles(currentUser)
        storageViewModel.filesList?.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val items: MutableList<StorageReference> = it.items
                adapter.setData(items)
            }
            hideProgressBar()

        })
    }
}