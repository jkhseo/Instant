package food.instant.instant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.ref.WeakReference;

import static food.instant.instant.HttpRequests.HttpGET;
import static food.instant.instant.HttpRequests.HttpPost;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link vendor_edit_restaurant.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link vendor_edit_restaurant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class vendor_edit_restaurant extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "vendor_edit_restaurant";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private vendor_edit_restaurant thisFragment = this;
    private View view;
    private TextView name;
    private TextView address;
    private TextView mainCuisine;
    private TextView secCuisine;
    private TextView addressLab;
    private TextView maincLab;
    private TextView secLab;
    private TextView ratLab;
    private RatingBar rating;
    private EditText et_address;
    private EditText et_main;
    private EditText et_sec;
    private TextView address2;
    private TextView maincLab2;
    private TextView secLab2;
    private TextView ratLab2;
    private RatingBar rating2;
    private Button editRest;
    private Button cancel;
    private Button submit;
    private Button delete;

    private Restaurant restaurant;
    private EditRestaurantsHandler handler;

    private OnFragmentInteractionListener mListener;

    public vendor_edit_restaurant() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public vendor_edit_restaurant(Restaurant rest){
        this.restaurant = rest;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment vendor_edit_restaurant.
     */
    // TODO: Rename and change types and number of parameters
    public static vendor_edit_restaurant newInstance(String param1, String param2) {
        vendor_edit_restaurant fragment = new vendor_edit_restaurant();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        handler = new EditRestaurantsHandler(vendor_edit_restaurant.this);
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_vendor_edit_restaurant, container, false);
        name = view.findViewById(R.id.tv_rest_name);
        address = view.findViewById(R.id.tv_address_data);
        mainCuisine = view.findViewById(R.id.tv_cuisine_main_data);
        secCuisine = view.findViewById(R.id.tv_cuisine_sec_data);
        addressLab = view.findViewById(R.id.tv_address_label);
        maincLab = view.findViewById(R.id.tv_cuisine_main_label);
        secLab = view.findViewById(R.id.tv_cuisine_sec_label);
        ratLab = view.findViewById(R.id.tv_rating_label);
        rating = view.findViewById(R.id.rb_rest_rating);
        rating.setEnabled(false);

        et_address = view.findViewById(R.id.et_rest_address);
        et_main = view.findViewById(R.id.et_rest_main);
        et_sec = view.findViewById(R.id.et_rest_sec);
        address2 = view.findViewById(R.id.tv_address_label2);
        maincLab2 = view.findViewById(R.id.tv_cuisine_main_label2);
        secLab2 = view.findViewById(R.id.tv_cuisine_sec_label2);
        ratLab2 = view.findViewById(R.id.tv_rating_label2);
        rating2 = view.findViewById(R.id.rb_rest_rating2);
        rating2.setEnabled(false);

        editRest = view.findViewById(R.id.bn_edit_rest);
        cancel = view.findViewById(R.id.bn_cancel_edit_rest);
        submit = view.findViewById(R.id.bn_submit_edit_rest);
        delete = view.findViewById(R.id.bn_delete_rest);

        HttpGET("getRestaurantFromID?Rest_ID=" + restaurant.getRest_ID(), handler);

        editRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete.setVisibility(View.GONE);
                editRest.setVisibility(View.GONE);
                address.setVisibility(View.GONE);
                mainCuisine.setVisibility(View.GONE);
                secCuisine.setVisibility(View.GONE);
                addressLab.setVisibility(View.GONE);
                maincLab.setVisibility(View.GONE);
                secLab.setVisibility(View.GONE);
                rating.setVisibility(View.GONE);
                ratLab.setVisibility(View.GONE);

                submit.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
                et_address.setVisibility(View.VISIBLE);
                et_main.setVisibility(View.VISIBLE);
                et_sec.setVisibility(View.VISIBLE);
                address2.setVisibility(View.VISIBLE);
                maincLab2.setVisibility(View.VISIBLE);
                secLab2.setVisibility(View.VISIBLE);
                ratLab2.setVisibility(View.VISIBLE);
                rating2.setVisibility(View.VISIBLE);

                et_address.setText(address.getText().toString());
                et_main.setText(mainCuisine.getText().toString());
                et_sec.setText(secCuisine.getText().toString());
                rating2.setRating((float)restaurant.getRating());
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                et_address.setVisibility(View.GONE);
                et_main.setVisibility(View.GONE);
                et_sec.setVisibility(View.GONE);
                address2.setVisibility(View.GONE);
                maincLab2.setVisibility(View.GONE);
                secLab2.setVisibility(View.GONE);
                ratLab2.setVisibility(View.GONE);
                rating2.setVisibility(View.GONE);

                delete.setVisibility(View.VISIBLE);
                editRest.setVisibility(View.VISIBLE);
                address.setVisibility(View.VISIBLE);
                mainCuisine.setVisibility(View.VISIBLE);
                secCuisine.setVisibility(View.VISIBLE);
                addressLab.setVisibility(View.VISIBLE);
                maincLab.setVisibility(View.VISIBLE);
                secLab.setVisibility(View.VISIBLE);
                rating.setVisibility(View.VISIBLE);
                ratLab.setVisibility(View.VISIBLE);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpPost("deleteRestaurant?Rest_ID=" + restaurant.getRest_ID(), handler);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rest_id = restaurant.getRest_ID();
                String rest_name = restaurant.getName();
                double rest_lat = restaurant.getLatitude();
                double rest_long = restaurant.getLongitude();
                String rest_address = et_address.getText().toString();
                double rest_rating = restaurant.getRating();
                String main_cusine = et_main.getText().toString();
                String sec_cuisine = et_sec.getText().toString();
                HttpPost("updateRestaurant?Rest_ID=" + rest_id + "&Rest_Name=" + rest_name + "&Rest_Coordinate_Lat=" + rest_lat + "&Rest_Coordinate_Long=" + rest_long + "&Rest_Address=" + rest_address + "&Rest_Rating=" + rest_rating + "&Rest_Type_Cuisine_Main=" + main_cusine + "&Rest_Type_Cuisine_Secondary=" + sec_cuisine + "&Rest_Keywords=''&User_ID=" + SaveSharedPreference.getId(getContext()), handler);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    private void updateCurrentData(Restaurant rest){
        restaurant = rest;
        name.setText(restaurant.getName());
        address.setText(rest.getAddress());
        mainCuisine.setText(rest.getCuisineMain());
        secCuisine.setText(rest.getCuisineSec());
        rating.setRating((float)rest.getRating());
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

    private class EditRestaurantsHandler extends Handler {
        /***************************************************************************************
         *    Title: Stack Overflow Answer to Question about static handlers
         *    Author: Tomasz Niedabylski
         *    Date: July 10, 2012
         *    Availability: https://stackoverflow.com/questions/11407943/this-handler-class-should-be-static-or-leaks-might-occur-incominghandler
         ***************************************************************************************/
        private final WeakReference<vendor_edit_restaurant> editRestaurantsFragment;

        public EditRestaurantsHandler(vendor_edit_restaurant editRestaurantsFragment) {
            this.editRestaurantsFragment = new WeakReference<vendor_edit_restaurant>(editRestaurantsFragment);
        }

        /*** End Code***/
        @Override
        public void handleMessage(android.os.Message msg) {
            vendor_edit_restaurant edit_restaurant = editRestaurantsFragment.get();
            if(msg.what == GlobalConstants.REFRESH_REST) {
                if (edit_restaurant != null) {
                    JSONArray response = null;
                    try {
                        response = ((JSONObject) msg.obj).getJSONArray("Restaurant_Name");
                        double latitude;
                        double longitude;
                        try {
                            latitude = (Double) ((JSONObject) response.get(0)).get("Rest_Coordinate_Lat");
                        } catch (java.lang.ClassCastException e) {
                            latitude = new Double((Integer) ((JSONObject) response.get(0)).get("Rest_Coordinate_Lat"));
                        }
                        try {
                            longitude = (Double) ((JSONObject) response.get(0)).get("Rest_Coordinate_Long");
                        } catch (java.lang.ClassCastException e) {
                            longitude = new Double((Integer) ((JSONObject) response.get(0)).get("Rest_Coordinate_Long"));
                        }
                        String name = (String) ((JSONObject) response.get(0)).get("Rest_Name");
                        String address = (String) ((JSONObject) response.get(0)).get("Rest_Address");
                        String mainCuis = (String) ((JSONObject) response.get(0)).get("Rest_Type_Cuisine_Main");
                        String secCuis = "";
                        if (((JSONObject) response.get(0)).has("Rest_Type_Cuisine_Secondary")) {
                            secCuis = (String) ((JSONObject) response.get(0)).get("Rest_Type_Cuisine_Secondary");
                        }
                        double rating = Double.parseDouble((String) ((JSONObject) response.get(0)).get("Rest_Rating"));
                        int Rest_ID = (int) ((JSONObject) response.get(0)).get("Rest_ID");
                        Restaurant tmp = new Restaurant(Rest_ID, name, latitude, longitude, address, rating, mainCuis, secCuis);
                        thisFragment.updateCurrentData(tmp);
                        Log.d(TAG, "Request made.........................");
                        Log.d(TAG, response.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if(msg.what == GlobalConstants.UPDATE_REST){
                JSONObject response = null;
                response = ((JSONObject) msg.obj);
                try {
                    String isSuccess = (String)response.get("Update_Restaurant");
                    Log.d(TAG, "Request made.........................");
                    if(isSuccess.equals("True")){
                        Toast.makeText(getContext(), "Restaurant updated successfully!", Toast.LENGTH_SHORT).show();
                        HttpGET("getRestaurantFromID?Rest_ID=" + restaurant.getRest_ID(), handler);
                    }
                    else{
                        Toast.makeText(getContext(), "Failed to update the restaurant!", Toast.LENGTH_SHORT).show();
                    }
                    submit.setVisibility(View.GONE);
                    cancel.setVisibility(View.GONE);
                    et_address.setVisibility(View.GONE);
                    et_main.setVisibility(View.GONE);
                    et_sec.setVisibility(View.GONE);
                    address2.setVisibility(View.GONE);
                    maincLab2.setVisibility(View.GONE);
                    secLab2.setVisibility(View.GONE);
                    ratLab2.setVisibility(View.GONE);
                    rating2.setVisibility(View.GONE);

                    delete.setVisibility(View.VISIBLE);
                    editRest.setVisibility(View.VISIBLE);
                    address.setVisibility(View.VISIBLE);
                    mainCuisine.setVisibility(View.VISIBLE);
                    secCuisine.setVisibility(View.VISIBLE);
                    addressLab.setVisibility(View.VISIBLE);
                    maincLab.setVisibility(View.VISIBLE);
                    secLab.setVisibility(View.VISIBLE);
                    rating.setVisibility(View.VISIBLE);
                    ratLab.setVisibility(View.VISIBLE);
                    HttpGET("getMenu?Restaurant_ID=" + restaurant.getRest_ID(), handler);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else if(msg.what == GlobalConstants.DELETE_REST){
                JSONObject response = null;
                response = ((JSONObject) msg.obj);
                try {
                    String isSuccess = (String)response.get("Delete_Restaurant");
                    Log.d(TAG, "Request made.........................");
                    if(isSuccess.equals("True")){
                        Toast.makeText(getContext(), "Restaurant deleted successfully!", Toast.LENGTH_SHORT).show();
                        HttpGET("getRestaurantFromID?Rest_ID=" + restaurant.getRest_ID(), handler);
                        getFragmentManager().popBackStackImmediate();
                    }
                    else{
                        Toast.makeText(getContext(), "Failed to delete the food restaurant!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
