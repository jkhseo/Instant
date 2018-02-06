package food.instant.instant;



import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

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
    private FusedLocationProviderClient flc;
    private boolean locationEnabled;
    private LatLng onMoveCenter;
    private float onMoveZoom;
    private Location myLocation;
    int MY_PERMISSIONS_REQUEST_LOCATION = 42;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public user_home_maps() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public user_home_maps(LatLng marker){
        this.onMoveCenter = marker;
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
    @Override
    public void onMapReady(GoogleMap map){
        res_map = map;
        res_map.getUiSettings().setZoomGesturesEnabled(true);
        res_map.getUiSettings().setZoomControlsEnabled(true);
        if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_LOCATION);
        }
        else{
            locationEnabled=true;
        }
        if(locationEnabled) {
            res_map.setOnMyLocationButtonClickListener(this);
            res_map.setMyLocationEnabled(true);
            res_map.setOnCameraMoveStartedListener((new GoogleMap.OnCameraMoveStartedListener() {
                @Override
                public void onCameraMoveStarted(int reason) {
                    if(reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE || reason == GoogleMap.OnCameraMoveStartedListener.REASON_API_ANIMATION){
                        onMoveCenter = res_map.getCameraPosition().target;
                        onMoveZoom = res_map.getCameraPosition().zoom;
                    }
                }
            }));
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
            flc = LocationServices.getFusedLocationProviderClient(getActivity());
            flc.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            LatLng startupLocation;
                            if(onMoveCenter !=null){
                                startupLocation = onMoveCenter;
                                res_map.moveCamera(CameraUpdateFactory.newLatLng(startupLocation));
                                res_map.moveCamera(CameraUpdateFactory.zoomTo(15));
                            }
                            else if (location != null) {
                                startupLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                myLocation = location;
                                res_map.moveCamera(CameraUpdateFactory.newLatLng(startupLocation));
                                res_map.moveCamera(CameraUpdateFactory.zoomTo(15));
                            }
                            else{
                                startupLocation = new LatLng(39.979832,-95.562702);
                                res_map.moveCamera(CameraUpdateFactory.newLatLng(startupLocation));

                            }
                        }
                    });
            final Restaurant test = new Restaurant(8,"Rancho Grande",42.063463,-94.868466,"323 N Main St, Carroll, IA 51401",5);
            res_map.addMarker(new MarkerOptions().position(new LatLng(test.getLatitude(),test.getLongitude())));//.title(test.getName()).snippet(test.getAddress()));
           /* res_map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    ((user_template)getActivity()).swapFragments(new user_home_restaurant(test));
                }
            });*/
            res_map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    View popup = LayoutInflater.from(getActivity()).inflate(R.layout.popupwindow,null);
                    final PopupWindow popupWindow = new PopupWindow(popup, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT,true);
                    Point markerPos = res_map.getProjection().toScreenLocation(marker.getPosition());
                    popupWindow.showAtLocation(popup, Gravity.CENTER,0,0);
                    ImageButton directions = popup.findViewById(R.id.directionsButton);
                    directions.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                            getDirections(test);
                        }
                    });
                    Button restaurant = popup.findViewById(R.id.restaurantButton);
                    restaurant.setText(test.getName());
                    restaurant.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                            ((user_template)getActivity()).swapFragments(new user_home_restaurant(test));
                        }
                    });

                    return false;
                }
            });
            findAreaRestaurants();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[],int[] grantResults){
        if(requestCode == MY_PERMISSIONS_REQUEST_LOCATION){
            locationEnabled = (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED);

        }
    }
    public void getDirections(Restaurant restaurant){
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+myLocation.getLatitude()+","+myLocation.getLongitude()+"&destination="+restaurant.getLatitude()+","+restaurant.getLongitude()+"&key=AIzaSyDrvQ_A2kpSvu_smP-TnzMLvoCQFoZx5_0";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response){
                try{
                    PolylineOptions directions = new PolylineOptions();
                    JSONObject route = (JSONObject) response.getJSONArray("routes").get(0);
                    JSONArray legs = route.getJSONArray("legs");
                    for(int i=0;i<legs.length();i++){
                        JSONArray steps = ((JSONObject)legs.get(i)).getJSONArray("legs");
                        for(int j=0;j<steps.length();j++){
                            String encodedpolyline = (String)((JSONObject)((JSONObject)steps.get(i)).get("polyline")).get("points");
                            List<LatLng> points = PolyUtil.decode(encodedpolyline);
                            directions.addAll(points);
                        }
                    }
                    res_map.addPolyline(directions);
                }catch(JSONException e){
                   System.out.println(e.getMessage());
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                System.err.println("Error Getting Directions");
            }
        });

    }
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
    public void findAreaRestaurants(){
        double minLat,minLong,maxLat,maxLong,minLong2,maxLong2;
        VisibleRegion visibleScreen = res_map.getProjection().getVisibleRegion();
        minLat = visibleScreen.latLngBounds.southwest.latitude;
        maxLat = visibleScreen.latLngBounds.northeast.latitude;
        if(visibleScreen.latLngBounds.southwest.longitude<=visibleScreen.latLngBounds.northeast.longitude){
            minLong = visibleScreen.latLngBounds.southwest.longitude;
            maxLong = visibleScreen.latLngBounds.northeast.longitude;
            minLong2 = 0;
            maxLong2 = 0;
        }
        else{
            minLong = visibleScreen.latLngBounds.southwest.longitude;
            maxLong = 180;
            minLong2 = -180;
            maxLong2 = visibleScreen.latLngBounds.northeast.longitude;
        }
        String query;
        if(minLong2==0 && maxLong2==0)
            query = "SELECT * FROM db309sd4.Restaurant WHERE (Rest_Coordinate_X,Rest_Coordinate_Y) BETWEEN ("+minLat+"," +minLong+") AND ("+maxLat+","+maxLong+");";
        else
            query = "";
        /*ArrayList<Restaurant> queryResults = HTTPGET.HTTPGetRestaurants(query);
        if(queryResults!=null){
            for(int i=0;i<queryResults.size();i++){
                res_map.addMarker(new MarkerOptions().position(new LatLng(queryResults.get(i).getLatitude(),queryResults.get(i).getLongitude())).title(queryResults.get(i).getName()).snippet(queryResults.get(i).getAddress()));
            }
        }*/
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
