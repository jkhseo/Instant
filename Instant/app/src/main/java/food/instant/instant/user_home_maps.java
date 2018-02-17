package food.instant.instant;



import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.internal.FusedLocationProviderResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;
import static food.instant.instant.GlobalConstants.MY_PERMISSIONS_REQUEST_LOCATION;
import static food.instant.instant.HttpRequests.HttpGET;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link user_home_maps.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link user_home_maps#newInstance} factory method to
 * create an instance of this fragment.
 */

public class user_home_maps extends Fragment implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {
    private GoogleMap res_map;
    private View mapsView;
    //private FusedLocationProviderClient flc;
    private ArrayList<LatLng> markerSet;
    private Location currentLocation;
    private LatLng onMoveCenter;
    private float onMoveZoom;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public user_home_maps() {
        markerSet = new ArrayList<LatLng>();
    }
    @SuppressLint("ValidFragment")
    public user_home_maps(LatLng marker){
        this.onMoveCenter = marker;
        markerSet = new ArrayList<LatLng>();
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user_home_maps.
     */
    // TODO: Rename and change types and number of parameters
    public static user_home_maps newInstance(String param1, String param2) {
        user_home_maps fragment = new user_home_maps();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[],int[] grantResults){
        if(requestCode == MY_PERMISSIONS_REQUEST_LOCATION){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                FusedLocationProviderClient flc = flc = LocationServices.getFusedLocationProviderClient(getActivity());
                flc.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        currentLocation = location;
                        locationSetup();
                    }
                });
            }
            else{
                currentLocation=null;
                locationSetup();
            }
        }
    }
    @SuppressLint("MissingPermission")
    public void locationSetup(){
        LatLng startupLocation;
        if(currentLocation!=null){
            res_map.setOnMyLocationButtonClickListener(this);
            res_map.setMyLocationEnabled(true);
            if(onMoveCenter !=null){
                startupLocation = onMoveCenter;
                res_map.moveCamera(CameraUpdateFactory.newLatLng(startupLocation));
                res_map.moveCamera(CameraUpdateFactory.zoomTo(15));
            }
            else{
                startupLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                res_map.moveCamera(CameraUpdateFactory.newLatLng(startupLocation));
                res_map.moveCamera(CameraUpdateFactory.zoomTo(15));
            }
        }
        else {
            startupLocation = new LatLng(39.979832, -95.562702);
            res_map.moveCamera(CameraUpdateFactory.newLatLng(startupLocation));
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap map){
        res_map = map;
        if (checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }
        else{
            FusedLocationProviderClient flc = flc = LocationServices.getFusedLocationProviderClient(getActivity());
            flc.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    currentLocation = location;
                    locationSetup();
                }
            });
        }
        res_map.getUiSettings().setZoomGesturesEnabled(true);
        res_map.getUiSettings().setZoomControlsEnabled(true);
        res_map.getUiSettings().setMapToolbarEnabled(false);
        res_map.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                if (onMoveCenter != null) {
                    boolean cameraMoved = (onMoveCenter.longitude != res_map.getCameraPosition().target.longitude) || (onMoveCenter.latitude != res_map.getCameraPosition().target.longitude);
                    boolean cameraZoomed = onMoveZoom != res_map.getCameraPosition().zoom;
                    if (res_map.getCameraPosition().zoom > 10 && (cameraMoved || cameraZoomed)) {
                        findAreaRestaurants();
                    }
                }
            }
        });
        res_map.setOnCameraMoveStartedListener((new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int reason) {
                if(reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE || reason == GoogleMap.OnCameraMoveStartedListener.REASON_API_ANIMATION){
                    onMoveCenter = res_map.getCameraPosition().target;
                    onMoveZoom = res_map.getCameraPosition().zoom;
                }
            }
        }));
        findAreaRestaurants();
    }

    public void getDirections(Restaurant restaurant){
        Intent mapsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/?api=1&destination="+restaurant.getLatitude()+"%2C"+restaurant.getLongitude()));
        startActivity(mapsIntent);
    }
    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentManager fManager = (FragmentManager) getChildFragmentManager();
        mapsView = inflater.inflate(R.layout.fragment_user_home_maps, container, false);
        SupportMapFragment resMapFrag = (SupportMapFragment) fManager.findFragmentById(R.id.user_home_maps);
        if(resMapFrag == null){
            resMapFrag = new SupportMapFragment();
            FragmentTransaction transaction = fManager.beginTransaction();
            transaction.add(R.id.user_home_maps,resMapFrag);
            transaction.commit();
            fManager.executePendingTransactions();
        }
        resMapFrag.getMapAsync(this);
        return mapsView;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    private static class MapHandler extends Handler {
        private final WeakReference<user_home_maps> mapFragment;
        public MapHandler(user_home_maps mapFragment){
            this.mapFragment = new WeakReference<user_home_maps>(mapFragment);
        }
        @Override
        public void handleMessage(Message msg){
            user_home_maps map = mapFragment.get();
            if(map != null && msg.what != GlobalConstants.EMPTY_JSON){
                JSONArray response = null;
                try {
                    response = ((JSONObject)msg.obj).getJSONArray("Restaurant_In_View");
                    double latitude,longitude;
                    ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
                    String name, address,rating;
                    Restaurant temp;
                    for(int i=0;i<response.length();i++){
                        latitude = (double)((JSONObject)response.get(i)).get("Rest_Coordinate_Lat");
                        longitude = (double)((JSONObject)response.get(i)).get("Rest_Coordinate_Long");
                        address = (String)((JSONObject)response.get(i)).get("Rest_Address");
                        name = (String)((JSONObject)response.get(i)).get("Rest_Name");
                        rating = ((String)((JSONObject)response.get(i)).get("Rest_Rating"));
                        temp = new Restaurant(name,latitude,longitude,address,Double.parseDouble(rating));
                        resultList.add(temp);
                        map.addRestaurantPins(resultList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void addRestaurantPins(ArrayList<Restaurant> resultList) {
        for (int i = 0; i < resultList.size(); i++) {
            if (!markerSet.contains(new LatLng(resultList.get(i).getLatitude(), resultList.get(i).getLongitude()))) {
                Restaurant temp = resultList.get(i);
                markerSet.add(new LatLng(temp.getLatitude(), temp.getLongitude()));
                Marker marker = res_map.addMarker(new MarkerOptions().position(new LatLng(temp.getLatitude(), temp.getLongitude())));
                marker.setTag(temp);
                res_map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(final Marker marker) {
                        View popup = LayoutInflater.from(getActivity()).inflate(R.layout.popupwindow, null);
                        final PopupWindow popupWindow = new PopupWindow(popup, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
                        Point markerPos = res_map.getProjection().toScreenLocation(marker.getPosition());
                        popupWindow.showAtLocation(popup, Gravity.CENTER, 0, 0);
                        ImageButton directions = popup.findViewById(R.id.directionsButton);
                        directions.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                                getDirections(((Restaurant)marker.getTag()));
                            }
                        });
                        Button restaurant = popup.findViewById(R.id.restaurantButton);
                        restaurant.setText(((Restaurant)marker.getTag()).getName());
                        restaurant.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                                ((MainActivity) getActivity()).swapFragments(new user_home_restaurant(((Restaurant)marker.getTag())));
                            }
                        });

                        return false;
                    }
                });
            }
        }
    }
    public void findAreaRestaurants() {
        double minLat, minLong, maxLat, maxLong, minLong2, maxLong2;
        VisibleRegion visibleScreen = res_map.getProjection().getVisibleRegion();
        minLat = visibleScreen.latLngBounds.southwest.latitude;
        maxLat = visibleScreen.latLngBounds.northeast.latitude;
        if (visibleScreen.latLngBounds.southwest.longitude <= visibleScreen.latLngBounds.northeast.longitude) {
            minLong = visibleScreen.latLngBounds.southwest.longitude;
            maxLong = visibleScreen.latLngBounds.northeast.longitude;
            minLong2 = 0;
            maxLong2 = 0;
        } else {
            minLong = visibleScreen.latLngBounds.southwest.longitude;
            maxLong = 180;
            minLong2 = -180;
            maxLong2 = visibleScreen.latLngBounds.northeast.longitude;
        }
        MapHandler handler = new MapHandler(this);
        HttpGET("getRestaurantsInView?max_Lat="+maxLat+"&max_Long="+maxLong+"&min_Lat="+minLat+"&min_Long="+minLong, handler);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
