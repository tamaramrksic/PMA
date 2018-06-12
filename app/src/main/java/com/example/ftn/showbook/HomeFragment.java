package com.example.ftn.showbook;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ftn.showbook.database.DatabaseHelper;
import com.example.ftn.showbook.database.FacilityDB;
import com.example.ftn.showbook.database.UserDB;
import com.example.ftn.showbook.model.Facility;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;


public class HomeFragment extends Fragment implements OnMapReadyCallback, LocationListener{

    private GoogleMap map;
    private FragmentManager fragmentManager;
    private String locationProvider;
    private LocationManager locationManager;
    private Location myLocation;
    private LatLng myLocationLatLng;
    private Marker home;
    private SupportMapFragment mMapFragment;
    private AlertDialog dialog;
    private Criteria criteria;
    private HashMap<Marker, FacilityDB> markers;
    private DatabaseHelper db;
    private Intent intent;
    private Geocoder geocoder;
    private String address;
    private Integer numberOfDialogLocation;
    private Integer numberOfDialogLocationPermission;
    public static HomeFragment newInstance() {

        HomeFragment hf = new HomeFragment();

        return hf;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle(getResources().getString(R.string.nav_map_label));
        createMapFragmentAndInflate();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getActivity());
        intent = getActivity().getIntent();
        geocoder = new Geocoder(getActivity());
        numberOfDialogLocation = Integer.parseInt(intent.getStringExtra("numLoc"));
        numberOfDialogLocationPermission = Integer.parseInt(intent.getStringExtra("numPerLoc"));
        UserDB user = db.getUserByUsername(intent.getStringExtra("drawerUsername"));
        address = user.getAddress()+ ", "+user.getLocation() + ", Srbija";



    }

    private void createMapFragmentAndInflate() {
        // Get LocationManager object from System Service LOCATION_SERVIC
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
        fragmentManager = getActivity().getSupportFragmentManager();
       // locationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);
        locationManager = (LocationManager) getActivity().getSystemService(
                Context.LOCATION_SERVICE);
        criteria = new Criteria();
        locationProvider = locationManager.getBestProvider(criteria, true);
        setMyLocationOff(locationProvider);

    }
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // Toast.makeText(getActivity(), "onResume()",
        // Toast.LENGTH_SHORT).show();

        //Toast.makeText(getActivity(), "onResume()", Toast.LENGTH_SHORT).show();



        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean wifi = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!gps && !wifi){
            //new LocationDialog(getActivity()).prepareDialog().show();
            numberOfDialogLocation = Integer.parseInt(intent.getStringExtra("numLoc"));
            if(numberOfDialogLocation<1) {
                showLocatonDialog();
                intent.putExtra("numLoc", "1");
            }
        } else {
            // Toast.makeText(getActivity(), "noService",
            // Toast.LENGTH_SHORT).show();
            locationProvider = locationManager.getBestProvider(criteria, true);
            System.out.println("location provider " + locationProvider);
            if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED  ) {

                   locationManager.requestLocationUpdates(locationProvider, 10, 0, this);
                   myLocation = locationManager.getLastKnownLocation(locationProvider);
                   myLocationLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());


                // System.out.println("Longitude jee " + myLocation.getLongitude());
                // System.out.println("Latitude jee " + myLocation.getLatitude());
            }else {
                System.out.println("nema odobrenje izgleda");
                numberOfDialogLocationPermission = Integer.parseInt(intent.getStringExtra("numPerLoc"));
                if(numberOfDialogLocationPermission<1) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            1);
                    intent.putExtra("numPerLoc", "1");
                }
            }
        }
        if(markers == null)
        {
            markers = new HashMap<Marker, FacilityDB>();
        }

    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        locationManager.removeUpdates(this);
    }
    private void showLocatonDialog(){
        if(dialog == null){
            dialog = new LocationDialog(getActivity()).prepareDialog();
        }else{
            if(dialog.isShowing()){
                dialog.dismiss();
            }
        }

        dialog.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //LatLng myLtnLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
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
/*
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(markers.get(marker) != null) {
                    FacilityDB facilityDB = markers.get(marker);
                    System.out.println("pritisnut je neki facilitiy a id mu je " + facilityDB.getId());
                    intent.putExtra("FacilityId", facilityDB.getId().toString());
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_container, new RepertoireFragment())
                            .addToBackStack(null)
                            .commit();
                }



                return false;
            }
        });
*/

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                if(markers.get(marker) != null) {
                    FacilityDB facilityDB = markers.get(marker);
                    System.out.println("pritisnut je neki facilitiy a id mu je " + facilityDB.getBackId());
                    intent.putExtra("FacilityId", facilityDB.getBackId());
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_container, new RepertoireFragment())
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
        if (myLocationLatLng != null) {
            addMarker(myLocationLatLng);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
       // Toast.makeText(getActivity(), "onLocationChange()", Toast.LENGTH_SHORT).show();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        addMarker(latLng);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        System.out.println("STANJE SE PROMENILO I SAD JE " + s);
        System.out.println("NEKO INT JE " + i);

    }

    @Override
    public void onProviderEnabled(String s) {
        System.out.println("SADA JE OMOGUCEN PROVIDED: "+s);

    }

    @Override
    public void onProviderDisabled(String s) {
        System.out.println("SADA JE ONEMOGUCE PROVIDER: "+s);

    }

    private void addMarker(LatLng loc) {
        // Toast.makeText(getActivity(), "addMarker",
        // Toast.LENGTH_SHORT).show();
        //LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

        if (home != null) {
            home.remove();
        }
        fillTheMapWithFacilities(map, myLocationLatLng);
        home = map.addMarker(new MarkerOptions()
                .position(loc)
                .title("Hej!")
                .snippet("Ovde se trenutno nalaziš!")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_my_location))

        );

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(loc).zoom(13).build();
        home.showInfoWindow();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    private void fillTheMapWithFacilities(GoogleMap map, LatLng latLng)
    {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity().getApplicationContext());
        Intent intent = getActivity().getIntent();
        String username = intent.getStringExtra("drawerUsername");
        UserDB userDb = db.getUserByUsername(username);
        String  personalRadius = userDb.getMaxDistance().toString();
        String lookupRadius = sharedPreferences.getString(getString(R.string.pref_radius), personalRadius);
        double radius = Double.parseDouble(lookupRadius);
        System.out.println("sad je radijus " + radius);
        List<FacilityDB> facilityDBS = db.getAllFacilities();
        List<FacilityDB> list = FacilityDB.filterByDistanceAndType(facilityDBS,
                latLng.latitude, latLng.longitude, radius, userDb.getFacilityType());

        //clear from list
        map.clear();

        //clear marker
       // markers.clear();

        for (Marker m: markers.keySet()) {
            m.remove();
        }

        for(FacilityDB facility : list)
        {
            if(facility.getType().equals("CINEMA")) {
                LatLng loc = new LatLng(Double.parseDouble(facility.getLatitude()), Double.parseDouble(facility.getLongitude()));
                Marker marker = map.addMarker(new MarkerOptions()
                        .title(facility.getName())
                        .snippet(facility.getAddress())
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_cinema))
                        .position(loc));
                marker.showInfoWindow();
                markers.put(marker, facility);
            }else if(facility.getType().equals("THEATER")) {
                LatLng loc = new LatLng(Double.parseDouble(facility.getLatitude()), Double.parseDouble(facility.getLongitude()));
                Marker marker = map.addMarker(new MarkerOptions()
                        .title(facility.getName())
                        .snippet(facility.getAddress())
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_theater))
                        .position(loc));
                marker.showInfoWindow();
                markers.put(marker, facility);


            }

        }



    }
    public void setMyLocationOff(String locationProvider) {
        try {

            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            Address myLocationOff = addresses.get(0);
            System.out.println("latitude je " + myLocationOff.getLatitude());
//            myLocation.setLatitude(myLocationOff.getLatitude());
            //      myLocation.setLongitude(myLocationOff.getLongitude());
            myLocationLatLng = new LatLng(myLocationOff.getLatitude(),myLocationOff.getLongitude() );
            myLocation = new Location(locationProvider);
            myLocation.setLongitude(myLocationOff.getLongitude() );
            myLocation.setLatitude(myLocationOff.getLatitude());
        }catch (IOException io) {
            System.out.println("ovo je porukica  " +io.toString());
        }
    }




}
