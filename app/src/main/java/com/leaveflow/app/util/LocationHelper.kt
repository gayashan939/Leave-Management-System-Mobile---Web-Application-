package com.leaveflow.app.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

data class GpsLocation(val latitude: Double, val longitude: Double)

object LocationHelper {

    /** Returns true if the app holds ACCESS_FINE_LOCATION or ACCESS_COARSE_LOCATION. */
    fun hasLocationPermission(context: Context): Boolean =
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED ||
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED

    /**
     * Fetches the current GPS location using FusedLocationProviderClient.
     * Falls back to last known location if current location is unavailable (e.g., on emulator).
     * Returns null if permission is not granted or if location is unavailable.
     */
    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(context: Context): GpsLocation? {
        if (!hasLocationPermission(context)) return null

        val client = LocationServices.getFusedLocationProviderClient(context)
        val cts    = CancellationTokenSource()

        val current = suspendCancellableCoroutine { cont ->
            client.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.token)
                .addOnSuccessListener { location: Location? ->
                    cont.resume(location)
                }
                .addOnFailureListener {
                    cont.resume(null)
                }

            cont.invokeOnCancellation { cts.cancel() }
        }

        if (current != null) {
            return GpsLocation(current.latitude, current.longitude)
        }

        // Fallback: use last known location (works better on emulators)
        return suspendCancellableCoroutine { cont ->
            client.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        cont.resume(GpsLocation(location.latitude, location.longitude))
                    } else {
                        cont.resume(null)
                    }
                }
                .addOnFailureListener {
                    cont.resume(null)
                }
        }
    }

    /** Formats a GpsLocation into a readable string for display. */
    fun format(location: GpsLocation): String =
        "Lat: %.6f, Lng: %.6f".format(location.latitude, location.longitude)

    /** Builds a Google Maps intent URI from a GpsLocation. */
    fun mapsUri(location: GpsLocation): String =
        "geo:${location.latitude},${location.longitude}?q=${location.latitude},${location.longitude}"
}
