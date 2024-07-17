package com.example.bythenai.utils.checknetwork

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.catch

@Composable
fun rememberConnectivityState(): State<NetworkStatus> {
    val context = LocalContext.current
    val networkStatusTracker = NetworkStatusTracker(context)
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return produceState(initialValue = getCurrentConnectivityState(connectivityManager)) {
        networkStatusTracker.networkStatus.catch {
            value = NetworkStatus.Unavailable
        }.collect {
            value = it
        }
    }
}

private fun getCurrentConnectivityState(connectivityManager: ConnectivityManager): NetworkStatus {
    val connected =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false

    return if (connected) NetworkStatus.Available else NetworkStatus.Unavailable
}