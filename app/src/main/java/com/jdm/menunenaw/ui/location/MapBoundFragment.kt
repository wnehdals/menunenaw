package com.jdm.menunenaw.ui.location

import android.graphics.Color
import android.util.Log
import android.view.ViewGroup
import android.widget.SeekBar
import com.jdm.menunenaw.R
import com.jdm.menunenaw.base.ViewBindingFragment
import com.jdm.menunenaw.data.BundleKey
import com.jdm.menunenaw.databinding.FragmentMapBoundBinding
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class MapBoundFragment : ViewBindingFragment<FragmentMapBoundBinding>() {
    private val TAG = MapBoundFragment::class.java.simpleName
    private val DEFAULT_LATITUDE = 37.0
    private val DEFAULT_LONGITUDE = 127.0
    private val DEFAULT_CIRCLE_RADIUS = 150
    private val SEEK_BAR_MOUNT = 50

    override val layoutId: Int = R.layout.fragment_map_bound

    /* Map 관련 */
    private val mapView by lazy { MapView(requireActivity()).apply {
        setMapViewEventListener(mapViewEvent)
        setPOIItemEventListener(poiItemEventListener)
    }}

    private val marker by lazy { MapPOIItem() }
    private val circle by lazy{
        MapCircle(MapPoint.mapPointWithGeoCoord(DEFAULT_LATITUDE, DEFAULT_LONGITUDE),
            DEFAULT_CIRCLE_RADIUS,
            Color.argb(128,255,255,255),
            Color.argb(40,200,50,128)
        ).apply { tag = 1234 }
    }

    override fun initView() {
        super.initView()
        binding.apply {
            llMapBoundMapContainer.addView(mapView,ViewGroup.LayoutParams.MATCH_PARENT)
            val y = arguments?.getString(BundleKey.LOCATION_Y.name)?.toDouble()
                ?: DEFAULT_LATITUDE
            val x = arguments?.getString(BundleKey.LOCATION_X.name)?.toDouble()
                ?: DEFAULT_LONGITUDE

            Log.i(TAG, "latitude : $y, longitude : $x ")
            val mapPoint = MapPoint.mapPointWithGeoCoord(y, x)
            mapView.setMapCenterPoint(mapPoint,false)
            setMarkerPos(mapPoint)
            setCircle(mapPoint)
        }
        setSeekbarUpdate()
    }

    override fun subscribe() {

    }

    private fun setSeekbarUpdate(){
        binding?.sbMapBoundBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                setCircleSize(p1 * SEEK_BAR_MOUNT + DEFAULT_CIRCLE_RADIUS)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }

    private fun setMarkerPos(mapPoint : MapPoint){
        mapView.removeAllPOIItems()
        marker.itemName = "이 장소로 설정"
        marker.tag = 0
        marker.mapPoint = mapPoint
        marker.markerType = MapPOIItem.MarkerType.BluePin
        marker.showAnimationType = MapPOIItem.ShowAnimationType.SpringFromGround
        marker.isDraggable = true
        mapView.addPOIItem(marker)
    }

    private fun setCircle(mapPoint: MapPoint){
        mapView.removeCircle(circle)
        circle.center = mapPoint
        mapView.addCircle(circle)
    }

    private fun setCircleSize(radius : Int){
        mapView.removeCircle(circle)
        circle.radius = radius
        mapView.addCircle(circle)
    }

    private val mapViewEvent = object :MapView.MapViewEventListener{
        override fun onMapViewInitialized(p0: MapView?) {}

        override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
            Log.i(TAG,"mapViewEvent onMapViewCenterPointMoved : $p1")

        }

        override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
            Log.i(TAG,"mapViewEvent onMapViewZoomLevelChanged : $p1")

        }

        override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
            Log.i(TAG,"mapViewEvent onMapViewSingleTapped : $p1")

        }

        override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {
            Log.i(TAG,"mapViewEvent onMapViewDoubleTapped : $p1")
        }

        override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
            Log.i(TAG,"mapViewEvent onMapViewLongPressed : $p1")
        }

        override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
            Log.i(TAG,"mapViewEvent onMapViewDragStarted : $p1")
        }

        override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
            Log.i(TAG,"mapViewEvent onMapViewDragEnded : $p1")
        }

        override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
            Log.i(TAG,"mapViewEvent onMapViewMoveFinished : $p1")
        }
    }

    // 마커 관련 이벤트
    private val poiItemEventListener = object :MapView.POIItemEventListener{
        override fun onPOIItemSelected(mapView: MapView?, poiItem: MapPOIItem?) {
            Log.i(TAG,"poiItemEventListener onPOIItemSelected : ${poiItem?.tag}")
        }

        override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?) {
            Log.i(TAG,"poiItemEventListener onCalloutBalloonOfPOIItemTouched : ${poiItem?.tag}")
        }

        override fun onCalloutBalloonOfPOIItemTouched(
            mapView: MapView?,
            poiItem: MapPOIItem?,
            p2: MapPOIItem.CalloutBalloonButtonType?
        ) {
            Log.i(TAG,"poiItemEventListener onCalloutBalloonOfPOIItemTouched : ${poiItem?.tag}")
        }

        override fun onDraggablePOIItemMoved(mapView: MapView?, poiItem: MapPOIItem?, mapPoint: MapPoint?) {
            Log.i(TAG,"poiItemEventListener onDraggablePOIItemMoved : ${poiItem?.tag}")
            mapPoint?.let{ setCircle(it) }
        }
    }
}