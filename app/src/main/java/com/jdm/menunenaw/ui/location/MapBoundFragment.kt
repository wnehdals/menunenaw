package com.jdm.menunenaw.ui.location

import android.util.Log
import android.view.ViewGroup
import com.jdm.menunenaw.R
import com.jdm.menunenaw.base.ViewBindingFragment
import com.jdm.menunenaw.data.BaseValue
import com.jdm.menunenaw.databinding.FragmentMapBoundBinding
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class MapBoundFragment : ViewBindingFragment<FragmentMapBoundBinding>() {
    private val TAG = MapBoundFragment::class.java.simpleName
    private val DEFAULT_LATITUDE = 37.0
    private val DEFAULT_LONGITUDE = 127.0

    override val layoutId: Int = R.layout.fragment_map_bound

    /* Map 관련 */
    private val mapView by lazy { MapView(requireActivity()).apply {
        mapViewEventListener = mapViewEventListener
        setPOIItemEventListener(poiItemEventListener)
    }}
    private val marker by lazy { MapPOIItem() }


    override fun initView() {
        super.initView()
        binding?.apply {
            llMapBoundMapContainer.addView(mapView,ViewGroup.LayoutParams.MATCH_PARENT)
            val y = arguments?.getString(BaseValue.BundleKey.LOCATION_Y.name)?.toDouble()
                ?: DEFAULT_LATITUDE
            val x = arguments?.getString(BaseValue.BundleKey.LOCATION_X.name)?.toDouble()
                ?: DEFAULT_LONGITUDE

            Log.i(TAG, "latitude : $y, longitude : $x ")
            val mapPoint = MapPoint.mapPointWithGeoCoord(y, x)
            mapView.setMapCenterPoint(mapPoint,false)
            setMarkerPos(mapPoint)
        }
    }

    override fun subscribe() {

    }

    private fun setMarkerPos(mapPoint : MapPoint){
        mapView.removeAllPOIItems()
        marker.itemName = "이 장소로 설정"
        marker.tag = 0
        marker.mapPoint = mapPoint
        marker.markerType = MapPOIItem.MarkerType.BluePin
        marker.showAnimationType = MapPOIItem.ShowAnimationType.SpringFromGround
        mapView.addPOIItem(marker)
    }

    private val mapViewEventListener = object :MapView.MapViewEventListener{
        override fun onMapViewInitialized(p0: MapView?) {}

        override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {}

        override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {}

        override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {}

        override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {}

        override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {}

        override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {}

        override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {}

        override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {}
    }

    // 마커 관련 이벤트
    private val poiItemEventListener = object :MapView.POIItemEventListener{
        override fun onPOIItemSelected(mapView: MapView?, poiItem: MapPOIItem?) {}

        override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?) {}

        override fun onCalloutBalloonOfPOIItemTouched(
            mapView: MapView?,
            poiItem: MapPOIItem?,
            p2: MapPOIItem.CalloutBalloonButtonType?
        ) {}

        override fun onDraggablePOIItemMoved(mapView: MapView?, poiItem: MapPOIItem?, mapPoint: MapPoint?) {}
    }
}