package com.itrexgroup.photos.view.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itrexgroup.photos.view.adapters.PhotosAdapter
import com.itrexgroup.photos.view.base.BaseFragment
import com.itrexgroup.photos.vm.PhotosViewModel
import kotlinx.android.synthetic.main.fragment_photos.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import com.itrexgroup.photos.R


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
        viewModel.loadFirstPagePhotos()
    }

    private fun setupRecyclerView() {
        adapter = PhotosAdapter(this::onItemClick)
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
                    && totalItemCount >= PAGE_SIZE
                ) {
                    viewModel.loadNextPagePhotosIfExists()
                }
            }
        })
    }

    private fun onItemClick(view: View, url: String, photoTransitionName: String) {

    }


    private fun observeViewModel() {
        viewModel.progressLiveData.observe(this, Observer {

        })

        viewModel.listPhotosLiveData.observe(this, Observer {
            adapter.listPhotos = it
        })
    }

}