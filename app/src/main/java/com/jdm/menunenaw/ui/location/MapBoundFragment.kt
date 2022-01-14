package com.jdm.menunenaw.ui.location

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.jdm.menunenaw.R
import com.jdm.menunenaw.base.ViewBindingFragment
import com.jdm.menunenaw.data.*
import com.jdm.menunenaw.databinding.FragmentMapBoundBinding
import com.jdm.menunenaw.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MapBoundFragment : ViewBindingFragment<FragmentMapBoundBinding>() {
    private val TAG = MapBoundFragment::class.java.simpleName
    private val SEEK_BAR_MOUNT = 15
    val SEEK_BAR_MAX = 5

    override val layoutId: Int = R.layout.fragment_map_bound
    private val viewModel : MainViewModel by activityViewModels()

    /* Map 관련 */
    private lateinit var mapView : MapView
    private val marker by lazy { MapPOIItem() }
    private val circle by lazy{
        MapCircle(MapPoint.mapPointWithGeoCoord(DEFAULT_LATITUDE, DEFAULT_LONGITUDE),
            DEFAULT_CIRCLE_RADIUS,
            Color.argb(128,255,255,255),
            Color.argb(40,200,50,128)
        ).apply { tag = 1234 }
    }

    private var locationLatitude = DEFAULT_LATITUDE
    private var locationLongitude = DEFAULT_LONGITUDE
    private var zoomLavel = 0
    val locationName = MutableLiveData("")
    val searchResult = MutableLiveData("")

    override fun initView() {
        super.initView()
        initData()

        binding.apply {
            fragment = this@MapBoundFragment
            lifecycleOwner = this@MapBoundFragment
            mapView = MapView(requireActivity()).apply {
                setMapViewEventListener(mapViewEvent)
                setPOIItemEventListener(poiItemEventListener)
                setZoomLevel(zoomLavel, false)
            }
            llMapBoundMapContainer.addView(mapView, ViewGroup.LayoutParams.MATCH_PARENT)

            Log.i(TAG, "latitude : ${locationLatitude}, longitude : ${locationLongitude} ")
            val mapPoint = MapPoint.mapPointWithGeoCoord(locationLatitude, locationLongitude)
            mapView.setMapCenterPoint(mapPoint,false)
            setMarkerPos(mapPoint)
            setCircle(mapPoint)
            searchCategory()
        }
        setSeekbarUpdate()
    }

    override fun subscribe() {}

    override fun onDestroyView() {
        super.onDestroyView()
        binding.llMapBoundMapContainer.removeView(mapView)
    }

    private fun initData(){
        arguments?.getString(BundleKey.LOCATION_Y.name)?.toDouble()?.let{ locationLatitude = it }
        arguments?.getString(BundleKey.LOCATION_X.name)?.toDouble()?.let{ locationLongitude = it }
        arguments?.getString(BundleKey.LOCATION_NAME.name)?.let{ locationName.value = it } ?: run {
            moveLocation(locationLatitude, locationLongitude)
        }
    }

    private fun setSeekbarUpdate(){
        binding.sbMapBoundBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                setCircleSize(p1 * SEEK_BAR_MOUNT + DEFAULT_CIRCLE_RADIUS)
                searchCategory()
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

    private fun moveLocation(latitude: Double, longitude: Double) {
        locationLatitude = latitude
        locationLongitude = longitude
        viewModel.getLocationInfo(locationLatitude, locationLongitude){
            locationName.postValue(it)
        }
    }

    // 지정 범위 내 음식점 개수 가져오기
    private fun searchCategory() {
        viewModel.getSearchCategoryCount(locationLatitude, locationLongitude, circle.radius,1){
            searchResult.postValue(String.format("내위치로부터 %d개의 식당이 발견되었어요",
                it.coerceAtMost(MAX_STORE_COUNT)
            ))
            binding.tbMapBoundNext.isChecked = it >= 3
            binding.tbMapBoundNext.isEnabled = binding.tbMapBoundNext.isChecked
        }
    }

    fun onClickOfNext() {
        moveFragment(R.id.action_mapBoundFragment_to_storeSelectFragment,
            bundle = Bundle().apply {
                putDouble(BundleKey.LOCATION_Y.name, locationLatitude)
                putDouble(BundleKey.LOCATION_X.name, locationLongitude)
                putInt(BundleKey.RADIUS.name, circle.radius)
            })
    }

    private val mapViewEvent = object :MapView.MapViewEventListener{
        override fun onMapViewInitialized(p0: MapView?) {}

        override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
            Log.d(TAG,"mapViewEvent onMapViewCenterPointMoved : $p1")
        }

        override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
            Log.d(TAG,"mapViewEvent onMapViewZoomLevelChanged : $p1")
            zoomLavel = p1
        }

        override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
            Log.d(TAG,"mapViewEvent onMapViewSingleTapped : $p1")
        }

        override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {
            Log.d(TAG,"mapViewEvent onMapViewDoubleTapped : $p1")
        }

        override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
            Log.d(TAG,"mapViewEvent onMapViewLongPressed : $p1")
        }

        override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
            Log.d(TAG,"mapViewEvent onMapViewDragStarted : $p1")
        }

        override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
            Log.d(TAG,"mapViewEvent onMapViewDragEnded : $p1")
        }

        override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
            Log.d(TAG,"mapViewEvent onMapViewMoveFinished : $p1")
        }
    }

    // 마커 관련 이벤트
    private val poiItemEventListener = object :MapView.POIItemEventListener{
        override fun onPOIItemSelected(mapView: MapView?, poiItem: MapPOIItem?) {
            Log.d(TAG,"poiItemEventListener onPOIItemSelected : ${poiItem?.tag}")
        }

        override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?) {
            Log.d(TAG,"poiItemEventListener onCalloutBalloonOfPOIItemTouched : ${poiItem?.tag}")
        }

        override fun onCalloutBalloonOfPOIItemTouched(
            mapView: MapView?,
            poiItem: MapPOIItem?,
            p2: MapPOIItem.CalloutBalloonButtonType?
        ) {
            Log.d(TAG,"poiItemEventListener onCalloutBalloonOfPOIItemTouched : ${poiItem?.tag}")
        }

        override fun onDraggablePOIItemMoved(mapView: MapView?, poiItem: MapPOIItem?, mapPoint: MapPoint?) {
            Log.d(TAG,"poiItemEventListener onDraggablePOIItemMoved : ${poiItem?.tag}")
            mapPoint?.let{
                setCircle(it)
                moveLocation(mapPoint.mapPointGeoCoord.latitude,mapPoint.mapPointGeoCoord.longitude)
                searchCategory()
            }
        }
    }
}