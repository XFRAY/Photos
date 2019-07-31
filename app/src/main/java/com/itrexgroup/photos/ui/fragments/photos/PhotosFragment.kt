package com.itrexgroup.photos.ui.fragments.photos

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.itrexgroup.photos.ui.adapters.photos.PhotosAdapter
import com.itrexgroup.photos.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_photos.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.itrexgroup.photos.R
import com.itrexgroup.photos.data.network.NetworkState


class PhotosFragment : BaseFragment() {

    companion object {
        const val TAG = "PHOTOS_FRAGMENT_TAG"
        fun newInstance() = PhotosFragment()
    }

    private val viewModel: PhotosViewModel by viewModel()
    private lateinit var adapter: PhotosAdapter

    override fun getLayoutResourceId() = R.layout.fragment_photos

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = PhotosAdapter()
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.initialLoadStateLiveData.observe(this, Observer {
            handleInitialLoadNetworkState(it)
        })

        viewModel.paginationLoadStateLiveData.observe(this, Observer {
            adapter.setNetworkState(it)
        })

        viewModel.listPhotosLiveData.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun handleInitialLoadNetworkState(networkState: NetworkState) {
        when (networkState) {
            NetworkState.LOADED -> {
                hideProgressBar()
            }
            NetworkState.LOADING -> {
                showProgressBar()
            }
            NetworkState.FAILED -> {
                showEmptyScreen()
            }
        }
    }

    private fun showEmptyScreen() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        groupNoItems.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
        groupNoItems.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        groupNoItems.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

}