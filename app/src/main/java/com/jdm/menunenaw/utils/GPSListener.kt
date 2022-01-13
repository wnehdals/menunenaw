package com.jdm.menunenaw.utils

import android.location.Location
import android.location.LocationListener

class GPSListener : LocationListener {
    var onReceiveLocation: (Location) -> Unit = {}
    override fun onLocationChanged(it: Location) {
        onReceiveLocation(it)
    }
}