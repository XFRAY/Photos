package com.itrexgroup.photos.view.fragments

import android.os.Bundle
import android.view.View
import androidx.core.app.SharedElementCallback
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.itrexgroup.photos.R
import com.itrexgroup.photos.view.activities.MainActivity
import com.itrexgroup.photos.view.adapters.PhotosAdapter
import com.itrexgroup.photos.view.fragments.base.BaseFragment
import com.itrexgroup.photos.vm.PhotosViewModel
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
        viewModel.loadPhotos()
    }

    private fun setupRecyclerView() {
        adapter = PhotosAdapter(this::onItemClick)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun onItemClick(view: View, url: String, photoTransitionName: String) {
        router?.navigateToWithViewTransition(
            PhotoDetailsFragment.newInstance(url, photoTransitionName),
            PhotoDetailsFragment.TAG,
            view,
            "123"
        )
    }


    private fun observeViewModel() {
        viewModel.progressLiveData.observe(this, Observer {

        })

        viewModel.listPhotosLiveData.observe(this, Observer {
            adapter.listPhotos = it
        })
    }

}