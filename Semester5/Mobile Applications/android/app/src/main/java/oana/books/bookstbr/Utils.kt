package oana.books.bookstbr

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat

fun checkForInternet(context: Context): Boolean {
    val connectivityManager = ContextCompat.getSystemService(
        context, ConnectivityManager::class.java
    ) as ConnectivityManager

    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_ETHERNET
            ) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        ) return true
    }
    return false
}

@Composable
fun ToastNoInternet(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Column() {
                Text("Device is not connected to the internet")
                Text("Action cannot be done")
            }
        }
    )
}
