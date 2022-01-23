package com.jdm.menunenaw.ui.location

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.jdm.menunenaw.R
import com.jdm.menunenaw.base.ViewBindingFragment
import com.jdm.menunenaw.data.BundleKey
import com.jdm.menunenaw.data.DEFAULT_LATITUDE
import com.jdm.menunenaw.data.DEFAULT_LONGITUDE
import com.jdm.menunenaw.data.remote.response.LocationSearchResponse
import com.jdm.menunenaw.databinding.FragmentLocationSearchBinding
import com.jdm.menunenaw.ui.adapter.LocationSearchListAdapter
import com.jdm.menunenaw.utils.GPSListener
import com.jdm.menunenaw.utils.checkPermissionsAndRequest
import com.jdm.menunenaw.utils.controlSoftKeyboard
import com.jdm.menunenaw.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class LocationSearchFragment : ViewBindingFragment<FragmentLocationSearchBinding>() {
    private val TAG = LocationSearchFragment::class.java.simpleName
    override val layoutId: Int = R.layout.fragment_location_search
    private val viewModel: MainViewModel by activityViewModels()

    private var currentLatitude = DEFAULT_LATITUDE
    private var currentLongitude = DEFAULT_LONGITUDE
    private val gpsListener = GPSListener()
    private val locationManager: LocationManager by lazy {
        requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private val locationSearchAdapter by lazy {
        LocationSearchListAdapter(itemClick = { item ->
            onClickOfLocationItem(
                item
            )
        })
    }
    private val permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private val requestMultiplePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            var permissionFlag = true
            for (entry in it.entries) {
                if (!entry.value) {
                    permissionFlag = false
                    break
                }
            }
            if (!permissionFlag) {
                Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.location_permission_no),
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().popBackStack()
            } else {
                setLocationManager()
            }

        }

    /** 반드시 Permission 허용 상태일때만 수행 */
    @SuppressLint("MissingPermission")
    private fun setLocationManager() {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            viewModel.locationRequestTimeInterval,
            viewModel.locationRequestDistanceInterval,
            gpsListener
        )
        locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            viewModel.locationRequestTimeInterval,
            viewModel.locationRequestDistanceInterval,
            gpsListener
        )

    }

    private fun isEnableLocationSensor(): Boolean {
        return isGPSEnabled() || isNetworkEnabled()
    }


    override fun initView() {
        binding.clLocationSearchCurrentPos.isEnabled = false
        gpsListener.onReceiveLocation = this::recieveLocation
        if (!isEnableLocationSensor()) {
            enableLocationSensorSettings()
        }

        if(context?.checkPermissionsAndRequest(permissions,requestMultiplePermissionLauncher) == true ){
            @SuppressLint("MissingPermission")
            viewModel.location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (viewModel.location != null) {
                //가장 마지막에 저장한 위치값
            }
            setLocationManager()
        }

        binding.apply {
            fragment = this@LocationSearchFragment
            tvLocationSearchResultList.adapter = locationSearchAdapter
            svLocationSearchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    viewModel.queryFlow.value = newText
                    return false
                }
            })
        }
    }

    override fun subscribe() {
        viewModel.searchResult.observe(this) { pair ->
            binding.run {
                if (pair.first == MainViewModel.DataType.REMOTE) {
                    tvLocationSearchRecent.visibility = View.GONE
                    locationSearchAdapter.submitList(pair.second)
                } else {
                    tvLocationSearchRecent.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun onClickOfLocationItem(item: LocationSearchResponse.Document) {
        Log.i(TAG, "onClickOfLocationItem : $item ")
        binding.let { context?.controlSoftKeyboard(it.root, false) }
        moveFragment(
            R.id.action_locationSearchFragment_to_mapBoundFragment,
            bundle = Bundle().apply {
                putString(BundleKey.LOCATION_Y.name, item.y)
                putString(BundleKey.LOCATION_X.name, item.x)
                putString(BundleKey.LOCATION_NAME.name, item.address_name)
            })
    }

    fun onClickOfCurrentPosition(){
        binding.let { context?.controlSoftKeyboard(it.root, false) }
        moveFragment(
            R.id.action_locationSearchFragment_to_mapBoundFragment,
            bundle = Bundle().apply {
                putString(BundleKey.LOCATION_Y.name, currentLatitude.toString())
                putString(BundleKey.LOCATION_X.name, currentLongitude.toString())
            })
    }

    private fun enableLocationSensorSettings() {
        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).run { startActivity(this) }
    }

    private fun isGPSEnabled() = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    private fun isNetworkEnabled() =
        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    private fun recieveLocation(location: Location) {
        Log.e(TAG, "${location.latitude} / ${location.longitude}")
        currentLatitude = location.latitude
        currentLongitude = location.longitude
        binding.clLocationSearchCurrentPos.isEnabled = true
    }
}