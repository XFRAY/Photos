package com.itrexgroup.photos.ui.fragments.photos

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itrexgroup.photos.R
import com.itrexgroup.photos.data.network.NetworkState
import com.itrexgroup.photos.ui.adapters.photos.PhotosAdapter
import com.itrexgroup.photos.ui.base.BaseFragment
import com.itrexgroup.photos.utils.getColor
import kotlinx.android.synthetic.main.fragment_photos.*
import org.koin.androidx.viewmodel.ext.android.viewModel


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
        setupSwipeRefresh()
        if (savedInstanceState == null) {
            viewModel.loadFirstPagePhotos()
        }
    }

    private fun setupSwipeRefresh() {
        swipeRefreshLayout.setColorSchemeColors(getColor(R.color.colorAccent))
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshItems()
        }
    }

    private fun setupRecyclerView() {
        adapter = PhotosAdapter(this::replaceWithTransition)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= adapter.items.size
                ) {
                    viewModel.loadNextPagePhotosIfExists()
                }
            }
        })
    }

    private fun observeViewModel() {
        viewModel.initialNetworkStateLiveData.observe(this, Observer {
            handleInitialLoadNetworkState(it)
        })

        viewModel.paginationNetworkStateLiveData.observe(this, Observer {
            adapter.setNetworkState(it)
        })

        viewModel.listPhotosLiveData.observe(this, Observer {
            adapter.items = it
        })

        viewModel.refreshItemsLiveData.observe(this, Observer {
            handleRefreshItemsNetworkState(it)
        })
    }


    private fun replaceWithTransition(view: View) {
    }

    private fun handleRefreshItemsNetworkState(state: NetworkState) {
        when (state) {
            NetworkState.LOADED -> {
                swipeRefreshLayout.isRefreshing = false
            }
            NetworkState.LOADING -> {
                swipeRefreshLayout.isRefreshing = true
            }
            NetworkState.FAILED -> {
                swipeRefreshLayout.isRefreshing = false
            }
        }
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