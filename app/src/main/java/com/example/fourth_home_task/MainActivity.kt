package com.example.fourth_home_task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.fourth_home_task.databinding.ActivityMainBinding
import com.example.fourth_home_task.model.BankViewModel
import com.example.fourth_home_task.network.BankATM
import com.example.fourth_home_task.network.BankRenderer
import com.example.fourth_home_task.place.Place
import com.example.fourth_home_task.place.PlaceRenderer
import com.example.fourth_home_task.place.PlacesReader
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager

// Вариант 1 с Retrofit
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var circle: Circle? = null
    private val bankViewModel: BankViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bankViewModel.bankATM.observe(this) {
            val mapFragment = supportFragmentManager.findFragmentById(
                R.id.map_fragment
            ) as? SupportMapFragment
            mapFragment?.getMapAsync { googleMap ->
                googleMap.setOnMapLoadedCallback {
                    val bounds = LatLngBounds.builder()
                    bankViewModel.bankATM.value?.forEach {
                        bounds.include(LatLng(it.gpsX.toDouble(), it.gpsY.toDouble()))
                    }
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 20))
                }
                addClusteredMarkers(googleMap)
            }
        }
    }

    private fun addClusteredMarkers(googleMap: GoogleMap) {
        val clusterManager = ClusterManager<BankATM>(this, googleMap)
        clusterManager.renderer =
            BankRenderer(
                this,
                googleMap,
                clusterManager
            )
        clusterManager.addItems(bankViewModel.bankATM.value)
        clusterManager.cluster()
        clusterManager.setOnClusterItemClickListener { item ->
            addCircle(googleMap, item)
            return@setOnClusterItemClickListener false
        }
        googleMap.setOnCameraMoveStartedListener {
            clusterManager.markerCollection.markers.forEach { it.alpha = 0.3f }
            clusterManager.clusterMarkerCollection.markers.forEach { it.alpha = 0.3f }
        }

        googleMap.setOnCameraIdleListener {
            clusterManager.markerCollection.markers.forEach { it.alpha = 1.0f }
            clusterManager.clusterMarkerCollection.markers.forEach { it.alpha = 1.0f }
            clusterManager.onCameraIdle()
        }
    }

    private fun addCircle(googleMap: GoogleMap, item: BankATM) {
        circle?.remove()
        circle = googleMap.addCircle(
            CircleOptions()
                .center(LatLng(item.gpsX.toDouble(), item.gpsY.toDouble()))
                .radius(1000.0)
                .fillColor(ContextCompat.getColor(this, R.color.green_200))
                .strokeColor(ContextCompat.getColor(this, R.color.pink_500))
        )
    }
}


//Вариант 2 чтение из файла atm_gomel.json
//class MainActivity : AppCompatActivity() {
//    private var circle: Circle? = null
//    private val places: List<Place> by lazy {
//        PlacesReader(this).read()
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        val mapFragment = supportFragmentManager.findFragmentById(
//            R.id.map_fragment
//        ) as? SupportMapFragment
//        mapFragment?.getMapAsync { googleMap ->
//            googleMap.setOnMapLoadedCallback {
//                val bounds = LatLngBounds.builder()
//                places.forEach { bounds.include(it.latLng) }
//
//                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 20))
//            }
//            addClusteredMarkers(googleMap)
//        }
//    }
//
//    private fun addClusteredMarkers(googleMap: GoogleMap) {
//        val clusterManager = ClusterManager<Place>(this, googleMap)
//        clusterManager.renderer =
//            PlaceRenderer(
//                this,
//                googleMap,
//                clusterManager
//            )
//        clusterManager.markerCollection.setInfoWindowAdapter(MarkerInfoWindowAdapter(this))
//        clusterManager.addItems(places)
//        clusterManager.cluster()
//        clusterManager.setOnClusterItemClickListener { item ->
//            addCircle(googleMap, item)
//            return@setOnClusterItemClickListener false
//        }
//        googleMap.setOnCameraMoveStartedListener {
//            clusterManager.markerCollection.markers.forEach { it.alpha = 0.3f }
//            clusterManager.clusterMarkerCollection.markers.forEach { it.alpha = 0.3f }
//        }
//
//        googleMap.setOnCameraIdleListener {
//            clusterManager.markerCollection.markers.forEach { it.alpha = 1.0f }
//            clusterManager.clusterMarkerCollection.markers.forEach { it.alpha = 1.0f }
//            clusterManager.onCameraIdle()
//        }
//    }
//
//    private fun addCircle(googleMap: GoogleMap, item: Place) {
//        circle?.remove()
//        circle = googleMap.addCircle(
//            CircleOptions()
//                .center(item.latLng)
//                .radius(1000.0)
//                .fillColor(ContextCompat.getColor(this, R.color.green_200))
//                .strokeColor(ContextCompat.getColor(this, R.color.pink_500))
//        )
//    }
//}