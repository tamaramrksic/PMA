package com.example.ftn.showbook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class HomeFragment extends Fragment implements OnMapReadyCallback{

    private GoogleMap map;
    private FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fragmentManager = getActivity().getSupportFragmentManager();


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng cinplex = new LatLng(45.254313, 19.853547);
        map.addMarker(new MarkerOptions().position(cinplex).title("Marker in Cineplex"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(cinplex, 17));
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, new RepertoireFragment())
                        .addToBackStack(null)
                        .commit();

                return false;
            }
        });*/

        map = googleMap;
      //  map.setInfoWindowAdapter(new MapInfoAdapter());
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {

                if(markers.containsKey(marker))
                {
                    marker.showInfoWindow();
                }
                return true;
            }
        });

      /*  map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {
                Facility facility = markers.get(arg0);

                FragmentTransition.to(ReviewObjectTabsFragment.newInstance(rev.getModelId()), getActivity());

            }
        });
*/
        if (myLocation != null) {
            addMarker(myLocation);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
       // Toast.makeText(getActivity(), "onLocationChange()", Toast.LENGTH_SHORT).show();
        addMarker(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void addMarker(Location location) {
        // Toast.makeText(getActivity(), "addMarker",
        // Toast.LENGTH_SHORT).show();
        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

        if (home != null) {
            home.remove();
        }
        fillTheMapWithFacilities(map, myLocation);
        home = map.addMarker(new MarkerOptions()
                .position(loc)
                .title("Zdravo!")
                .snippet("Ovde se trenutno nalaziš!")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_my_location))

                     );

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(loc).zoom(17).build();
        home.showInfoWindow();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    private void fillTheMapWithFacilities(GoogleMap map, Location location)
    {
       /* SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity().getApplicationContext());
        String lookupRadius = sharedPreferences.getString(getString(R.string.pref_radius), "5");//1km
        double radius = Double.parseDouble(lookupRadius);

        List<FacilityDB> list = FacilityDB.filterByDistance(ReviewerTools.stringListToTagList(tagFilter),
                location.getLatitude(), location.getLongitude(), radius);

        //clear from list
        map.clear();

        //clear marker
        markers.clear();

        for(FacilityDB facility : list)
        {

            Geocoder geocoder = new Geocoder(getActivity().getApplicationContext());
            List<Address> addresses = new ArrayList<>();
            try {
               // addresses = geocoder.getFromLocationName(facility.getAddress() + ',' + facility.getLocation().getName(), 1);
            }catch (IOException io ){
                System.out.println(io);
            }
            if(facility.getType().equals(Facility.Type.CINEMA)) {
                LatLng loc = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                Marker marker = map.addMarker(new MarkerOptions()
                        .title(facility.getName())
                        .snippet(facility.getAddress())
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                        .position(loc));

                markers.put(marker, facility);
            }else if(facility.getType().equals(Facility.Type.THEATER)) {
                LatLng loc = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                Marker marker = map.addMarker(new MarkerOptions()
                        .title(facility.getName())
                        .snippet(facility.getAddress())
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .position(loc));
                markers.put(marker, facility);

            }
        }*/
    }




}
