package com.example.firstaidassistance;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.firstaidassistance.NearbyPlaceModels.CustomInfoWindow;
import com.example.firstaidassistance.NearbyPlaceModels.MarkerInfo;
import com.example.firstaidassistance.NearbyPlaceModels.Model2.MyPojo1;
import com.example.firstaidassistance.NearbyPlaceModels.ModelClasses.Myplaces;
import com.example.firstaidassistance.NearbyPlaceModels.ModelClasses.Results;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapsActivity  extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private static final int LOCATION_PERMISSION_CODE =100 ;
    private static final int PHONE_PERMISSION_CODE =500 ;
    private static final int REQUEST_CHECK_SETTINGS =200 ;
    private GoogleMap mMap;



    LocationRequest locationRequest;
    LocationCallback locationCallback;
    FusedLocationProviderClient fusedLocationClient;
    double lat,lon;

    boolean getLocationOnce=true;


    //atert dialog code
    AlertDialog.Builder builder;

    View mapView;




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:


                    String googleUrl=getUrl(lat,lon,"Hospital");//method gets the required google api url
                    setPointsonMap(googleUrl) ;//method retrieves nearest points and sets them on google map


                    return true;
                case R.id.navigation_dashboard:

                    String googleUrl1=getUrl(lat,lon,"Pharmacy");//method gets the required google api url
                    setPointsonMap(googleUrl1) ;//method retrieves nearest points and sets them on google map


                    return true;

            }
            return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);



        //initializing the android fast library used to fetch data from the google APis
        AndroidNetworking.initialize(getApplicationContext());


        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mapView = mapFragment.getView();


        //alert dialog
        builder=new AlertDialog.Builder(MapsActivity.this);



        //initializing fused location listener
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);





        if(checkLocationPermission()){
            //if permission is granted then request user to switch on mobile gps

            //gps methods

            switchGPSON();




        }else{

            //request fot Location permission
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
            Toast.makeText(this, "Please allow permission location for the app to function correctly", Toast.LENGTH_LONG).show();

        }







    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        CustomInfoWindow adapter = new CustomInfoWindow(MapsActivity.this);//setting custom info window
        mMap.setInfoWindowAdapter(adapter);
        mMap.setOnInfoWindowClickListener(this);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(0.1544413,35.6588999);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Kenya"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setMyLocationEnabled(true);


        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 150);
        }


    }





    private void setPointsonMap(String url) {

        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(Myplaces.class, new ParsedRequestListener<Myplaces>() {

                    @Override
                    public void onResponse(Myplaces response) {

                        final MarkerOptions markerOptions=new MarkerOptions();
                        Log.i("placesize", "results size: "+response.getResults().size());



                        for(int i=0;i<response.getResults().size();i++){

                            Results googlePlace=response.getResults().get(i);

                            final String pointName=googlePlace.getName();

                            final double point_lat=Double.parseDouble(googlePlace.getGeometry().getLocation().getLat());
                            final double point_lon=Double.parseDouble(googlePlace.getGeometry().getLocation().getLng());





                            String placeDetailsUrl=getDetails(googlePlace.getPlace_id());

                            AndroidNetworking.get(placeDetailsUrl)
                                    .setPriority(Priority.HIGH)
                                    .build()
                                    .getAsObject(MyPojo1.class, new ParsedRequestListener<MyPojo1>() {

                                        @Override
                                        public void onResponse(MyPojo1 response) {


                                            String phoneNo=response.getResult().getInternationalPhoneNumber();
                                            String photoUrl;



                                            if(response.getResult().getPhotos()!=null){

                                                photoUrl=response.getResult().getPhotos().get(0).getPhotoReference();//get the first photo in the list


                                            }
                                            else {
                                                photoUrl="https://via.placeholder.com/250?text=NO IMAGE";
                                            }




                                            LatLng latLng=new LatLng(point_lat,point_lon);

                                            markerOptions.position(latLng);
                                            markerOptions.title(pointName);


                                            MarkerInfo markerInfo = new MarkerInfo();
                                            //   markerInfo.setOpening_Hrs(opening_hrs);
                                            markerInfo.setPhone_number(phoneNo);
                                            //  markerInfo.setPlaceType(placeType);
                                            markerInfo.setPoto_reference(photoUrl);
                                            Gson gson = new Gson();
                                            String markerInfoString = gson.toJson(markerInfo);
                                            //adding complex data to map snippet
                                            markerOptions.snippet(markerInfoString);






                                            mMap.addMarker(markerOptions);
                                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));









                                        }

                                        @Override
                                        public void onError(ANError anError) {

                                        }
                                    });












                        }



                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }








    public String getDetails(String place_id) {

        StringBuilder googlePlacesUri=new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
        googlePlacesUri.append("placeid="+place_id);
        googlePlacesUri.append("&fields=name,international_phone_number,place_id,photo,rating");
        googlePlacesUri.append("&key="+getResources().getString(R.string.google_maps_key));

        Log.d("get_url",googlePlacesUri.toString());

        return googlePlacesUri.toString();
    }




    //the method is used to retrieve the nearby places using the google api key
    private String getUrl(double latitude, double longitude, String placeType) {

        StringBuilder googlePlacesUri=new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUri.append("location="+latitude+","+longitude);
        googlePlacesUri.append("&radius="+10000);//radius for searching nearby areas
        googlePlacesUri.append("&type="+placeType);
        googlePlacesUri.append("&keyword="+placeType);
        googlePlacesUri.append("&sensor=true");
        googlePlacesUri.append("&key="+getResources().getString(R.string.google_maps_key));

        Log.d("get url",googlePlacesUri.toString());

        return googlePlacesUri.toString();
    }


    //methods used to check device permission and also ensure the phone GPS is enabled



    private boolean checkLocationPermission() {
        boolean permissionGranted = ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if(permissionGranted){
            //permission granted
            return true;
        }else{
            //permission NOT granted

            return false;
        }
    }


    protected void switchGPSON() {
        LocationRequest locationRequest1 = LocationRequest.create();
        locationRequest1.setInterval(10000);
        locationRequest1.setFastestInterval(5000);
        locationRequest1.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //get current location settings
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest1);
        //check whether current location settings are satisfied
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());


        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                //if GPS is on run the method

                Log.i("placesize", "Gps success: ");

                buildLocationRequest();
                buildLocationCallBack();

                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {

                        //    Log.i("placesize", "Gps failed: ");
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().

                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(MapsActivity.this,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }




    //the two method used to request gos locations periodically
    //start---------------------------------------------------------------------------------------------------------
    void buildLocationRequest(){
        locationRequest=new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10);

    }
    void buildLocationCallBack(){
        locationCallback=new LocationCallback()
        {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for(Location location:locationResult.getLocations()){


                    if (location != null) {

                        lat=location.getLatitude();
                        lon=location.getLongitude();


                        if(getLocationOnce){
                            //ensures the location is obtained only once and not after every 5 seconds

                            Log.i("placesize", "results latitude: "+lat);
                            Log.i("placesize", "results longitude: "+lon);



                            String googleUrl=getUrl(lat,lon,"Hospital");//method gets the required google api url
                            setPointsonMap(googleUrl) ;//method retrieves nearest points and sets them on google map

                            getLocationOnce=false;


                        }


                    }
//
                }
            }
        };
    }

    //end----------------------------------------------------------------------------------------------------------



    //dealing with permission request for GPS and mobile location
    //start........................................................................................................
    @SuppressLint("MissingPermission")
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_CODE://LOCATION PERMISSION
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (locationAccepted){

                        //if Location oermission granted sweitch on mobile GPS
                        switchGPSON();
                    }
                    else {
                        //permission denied
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel(
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION},
                                                            LOCATION_PERMISSION_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;


        }
    }


    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MapsActivity.this)
                .setMessage("You need to allow access to this permission for the app to work")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }





    private void showMessageGPSOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MapsActivity.this)
                .setMessage("You need to enable GPS for the app to work")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }





    @SuppressLint("MissingPermission")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //the method is called when the dialogue requesting user to enable GPS is enabled

        if(requestCode==REQUEST_CHECK_SETTINGS && resultCode== Activity.RESULT_OK){
            //result from asking for device location change

            //if gps enable request is allowed do the following

            Log.i("placesize", "Gps enabled: ");

            buildLocationRequest();
            buildLocationCallBack();
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());



        }else if(requestCode==REQUEST_CHECK_SETTINGS && resultCode==Activity.RESULT_CANCELED){
            //if gps enable request is denied do the following

            Log.i("placesize", "Gps canceled: ");


            showMessageGPSOKCancel(
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(which==-1){
                                //if ok button is clicked
                                switchGPSON();
                            }else{

                                Toast.makeText(MapsActivity.this,"GPS must be enabled for the maps to function",Toast.LENGTH_LONG).show();
                            }


                        }
                    });


        }

    }






    //end...................................................................................................................



    //CODE DEALONG WITH MARKER INFO CLICK EVENTS
    //START------------------------------------------------------------------------------------------------------------------
    @Override
    public void onInfoWindowClick(final Marker marker) {

        View  view= LayoutInflater.from(MapsActivity.this).inflate(R.layout.alertbox_distance,null);
        //   distance=view.findViewById(R.id.id_distance);
        ImageView call=view.findViewById(R.id.id_call);
        TextView text_call=view.findViewById(R.id.text_call);
        builder.setView(view);
        final AlertDialog testDialog = builder.create();
        testDialog.show();


        Gson gson = new Gson();
        final MarkerInfo aMarkerInfo = gson.fromJson(marker.getSnippet(), MarkerInfo.class);

        try{
            if(aMarkerInfo.getPhone_number()==null){
                call.setVisibility(View.GONE);
                text_call.setText("Has no Mobile Number");
            }

        }catch (Exception e){}





//        distance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                testDialog.dismiss();
//                calculateDirections(marker);
//
//
//            }
//        });


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testDialog.dismiss();
                callMe(aMarkerInfo);

            }
        });
    }


    private void callMe(MarkerInfo aMarkerInfo) {
        //METHOD USED TO INITIATE DIRECT PHONE CALL

        if(ContextCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            // if the permission has been denied allow user to request for the permission
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.CALL_PHONE},PHONE_PERMISSION_CODE);

        }else{

            String dial="tel:"+aMarkerInfo.getPhone_number();
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));

        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.map_hybrid) {

            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


        }

        if (id == R.id.map_satellite) {

            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);




        }

        if (id == R.id.map_std) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);




        }



        return true;
    }


}
