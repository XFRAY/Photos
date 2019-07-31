package com.itrexgroup.photos.data.network



@Suppress("DataClassPrivateConstructor")
data class NetworkState(
    val status: Status,
    val message: String? = null) {
    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.RUNNING)
        val FAILED = NetworkState(Status.FAILED)
    }

    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED
    }
}