package food.instant.instant;



import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.internal.FusedLocationProviderResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link user_home_maps.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link user_home_maps#newInstance} factory method to
 * create an instance of this fragment.
 */

public class user_home_maps extends Fragment implements OnMapReadyCallback {
    private GoogleMap res_map;
    private FusedLocationProviderClient flc;
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
    public void checkPermissions(){
        if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_LOCATION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[],int[] grantResults){
        if(requestCode == MY_PERMISSIONS_REQUEST_LOCATION){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
            else {

            }
        }
    }
    @Override
    public void onMapReady(GoogleMap map){
        res_map = map;
        res_map.getUiSettings().setZoomGesturesEnabled(true);
        res_map.getUiSettings().setZoomControlsEnabled(true);
        //checkPermissions();
        //res_map.setMyLocationEnabled(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentManager fManager = (FragmentManager) getChildFragmentManager();
        View mapsView = inflater.inflate(R.layout.fragment_user_home_maps, container, false);
        SupportMapFragment resMapFrag = (SupportMapFragment) fManager.findFragmentById(R.id.user_home_maps);
        if(resMapFrag == null){
            resMapFrag = new SupportMapFragment();
            FragmentTransaction transaction = fManager.beginTransaction();
            transaction.add(R.id.user_home_maps,resMapFrag);
            transaction.commit();
            fManager.executePendingTransactions();
        }
        resMapFrag.getMapAsync(this);
       /* flc = LocationServices.getFusedLocationProviderClient(this);
        flc.getLastLocation()
                .addOnSuccessListener(this,new OnSuccessListener<Location>(){
                    @Override
                    void onSuccess(Location location){
                        LatLng startupLocation;
                        if(location == null)
                            startupLocation = new LatLng(42.026238,-93.648434);
                        else
                            startupLocation = new LatLng(location.getLatitude(),location.getLongitude());
                        res_map.addMarker(new MarkerOptions().position(startupLocation));
                        res_map.moveCamera(CameraUpdateFactory.newLatLng(startupLocation));
                    }
            });*/


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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
