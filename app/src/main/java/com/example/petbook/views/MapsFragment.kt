package com.example.petbook.views

import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.example.petbook.BuildConfig.MAPS_API_KEY
import com.example.petbook.R
import com.example.petbook.databinding.FragmentMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import androidx.lifecycle.lifecycleScope
import com.example.petbook.model.Candidate
import com.example.petbook.model.PlacesApiResponse
import com.example.petbook.util.LoadingDialog
import com.example.petbook.util.State
import com.example.petbook.viewModel.LocationViewModel
import com.google.android.material.snackbar.Snackbar


class MapsFragment : Fragment() ,GoogleMap.OnMarkerClickListener {
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var map: GoogleMap
    private lateinit var binding: FragmentMapsBinding
    private val locationViewModel: LocationViewModel by viewModels<LocationViewModel>()
    private var radius = 1500
    private lateinit var loadingDialog: LoadingDialog
    private var CandidateList: ArrayList<Candidate>  = ArrayList()

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        map = googleMap

        // to put marker in center
        map.uiSettings.isZoomControlsEnabled = true

        // the activity is the callback triggered when user click on marker aka when clicked this function is called
        googleMap.setOnMarkerClickListener(this)

        // check its function above
        setUpMap()


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMapsBinding.inflate(inflater, container, false)
        val view = binding.root
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        var CandidateList = arrayListOf<Candidate>()


    binding.CheckVetsButton.setOnClickListener{

        getNearByPlace("Pet Store")


    }
        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog(requireActivity())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)



    }

    // when click a marker
    override fun onMarkerClick(p0: Marker): Boolean {
        return false
    }

    // checks if the app has been granted the ACCESS_FINE_LOCATION permission otherwise request it again from user
    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }


  // enables my-location layer which draws a light blue dor on the user's location and add a button to center the map on his location
        map.isMyLocationEnabled = true

// gives you the most recent location currently available.
        fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { location ->
            // Got last known location. In some rare situations this can be null.
            // when could get last location center camera on it
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                // show the marker
                placeMarkerOnMap(currentLatLng)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }

    }




    private fun placeMarkerOnMap(location: LatLng) {
        // it creates a marker object and sets the user curretn location as the position of the marker
        val markerOptions = MarkerOptions().position(location)
        markerOptions.icon(
            BitmapDescriptorFactory.fromBitmap(
            BitmapFactory.decodeResource(resources, R.mipmap.ic_user_location)))

       /* val titleStr = getAddress(location)  // add these two lines
        markerOptions.title(titleStr)*/

        // Add the marker to the map
        map.addMarker(markerOptions)
    }



  /*  private fun getAddress(latLng: LatLng): String {
        // Create a geocoder object to turn latitude, longitude into adress and vice versa
        val geocoder = Geocoder(requireActivity())
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            // Asks the geocoder to get the adress from the location passed to the method
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            // if the response contains any adress then append it to a string and return
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses[0]
                for (i in 0 until address.maxAddressLineIndex) {
                    addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
                }
            }
        } catch (e: IOException) {
            Log.e("MapsFragment", e.localizedMessage)
        }

        return addressText
    }*/


  private fun getNearByPlace(placeType: String) {
      val url2 = ("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
              + lastLocation.latitude + "," + lastLocation.longitude
              + "&radius=" + radius + "&type=" + placeType + "&key=" + MAPS_API_KEY)

      val url= ("https://geocode-api.arcgis.com/arcgis/rest/services/World/GeocodeServer" +
              "/findAddressCandidates?category=" + placeType + "&location="+ lastLocation.longitude +"," + lastLocation.latitude + "&f=json&token" +
              "=AAPKe6988844ea3741459d854ef7f35517621m9bsCDmrdHn6pAnVT_Nlxca6xkVHfp_errD43Acrc9WsnwKjOXO-FpfNScU140Y")



      lifecycleScope.launchWhenStarted {
          locationViewModel.getNearByPlace(url).collect {
              when (it) {
                  is State.Loading -> {
                      if (it.flag == true) {
                          loadingDialog.startLoading()
                      }
                  }

                  is State.Success -> {
                      loadingDialog.stopLoading()
                      val apiPalcesResonse: PlacesApiResponse =
                          it.data as PlacesApiResponse

                      if (apiPalcesResonse.candidates !== null &&
                          apiPalcesResonse.candidates.isNotEmpty()
                      ) {
                          CandidateList.clear()
                          map.clear()

                          for (i in apiPalcesResonse.candidates.indices) {


                              CandidateList.add(apiPalcesResonse.candidates[i])
                              addMarker(apiPalcesResonse.candidates[i], i)
                          }
                         // googlePlaceAdapter.setGooglePlaces(googlePlaceList)
                      } else {
                          map.clear()
                          CandidateList.clear()

                      }

                  }
                  is State.Failed -> {
                      loadingDialog.stopLoading()
                      Snackbar.make(
                          binding.root, it.error,
                          Snackbar.LENGTH_SHORT
                      ).show()
                  }
              }
          }
      }
  }

    private fun addMarker(candidate: Candidate, position: Int) {
        Log.d("vetmarker", candidate.address + "  " + candidate.location )
        val markerOptions = MarkerOptions()
            .position(
                LatLng(
                    candidate.location.y!!,
                    candidate.location.x!!
                )
            )
            .title(candidate.address)
            .snippet(candidate.address)

        markerOptions.icon((
                BitmapDescriptorFactory.fromBitmap(
                    BitmapFactory.decodeResource(resources, R.drawable.marker))))
        map.addMarker(markerOptions)?.tag = position

    }


}


