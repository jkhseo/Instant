package food.instant.instant;

import android.content.Context;
import android.net.Uri;
import android.os.*;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static food.instant.instant.HttpRequests.HttpPost;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link vendor_add_restaurant.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link vendor_add_restaurant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class vendor_add_restaurant extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = "vendor_add_restaurant";
    private EditText name;
    private EditText lat;
    private EditText lon;
    private EditText address;
    private EditText main;
    private EditText sec;
    private Button submit;
    private Button cancel;
    private View view;
    private AddRestaurantHandler handler;

    private OnFragmentInteractionListener mListener;

    public vendor_add_restaurant() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment vendor_add_restaurant.
     */
    // TODO: Rename and change types and number of parameters
    public static vendor_add_restaurant newInstance(String param1, String param2) {
        vendor_add_restaurant fragment = new vendor_add_restaurant();
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_vendor_add_restaurant, container, false);
        handler = new AddRestaurantHandler(vendor_add_restaurant.this);
        name = view.findViewById(R.id.add_rest_name);
        lat = view.findViewById(R.id.add_rest_lat);
        lon = view.findViewById(R.id.add_rest_long);
        address = view.findViewById(R.id.add_rest_address);
        main = view.findViewById(R.id.add_rest_main);
        sec = view.findViewById(R.id.add_rest_sec);
        submit = view.findViewById((R.id.bn_add_rest));
        cancel = view.findViewById(R.id.bn_cancel_add_rest);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String add_name = name.getText().toString();
                String add_lat = lat.getText().toString();
                String add_long = lon.getText().toString();
                String add_address = address.getText().toString();
                String add_main = main.getText().toString();
                String add_sec = sec.getText().toString();
                if(add_name.length() == 0 || add_lat.length() == 0 || add_long.length() == 0 || add_address.length() == 0
                        || add_main.length() == 0 || add_sec.length() == 0){
                    Toast.makeText(getContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(sec.getWindowToken(), 0);
                    HttpPost("addRestaurant?Rest_Name=" + add_name + "&Rest_Coordinate_Lat=" + add_lat + "&Rest_Coordinate_Long=" + add_long + "&Rest_Address=" + add_address + "&Rest_Rating=0&Rest_Type_Cuisine_Main=" + add_main + "&Rest_Type_Cuisine_Secondary=" + add_sec + "&Rest_Keywords=" + add_main + "," + add_sec + "&User_ID=" + SaveSharedPreference.getId(getContext()), handler);
                }
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

    private class AddRestaurantHandler extends Handler {
        /***************************************************************************************
         *    Title: Stack Overflow Answer to Question about static handlers
         *    Author: Tomasz Niedabylski
         *    Date: July 10, 2012
         *    Availability: https://stackoverflow.com/questions/11407943/this-handler-class-should-be-static-or-leaks-might-occur-incominghandler
         ***************************************************************************************/
        private final WeakReference<vendor_add_restaurant> addRestaurantFragment;

        public AddRestaurantHandler(vendor_add_restaurant addRestaurantFragment) {
            this.addRestaurantFragment = new WeakReference<vendor_add_restaurant>(addRestaurantFragment);
        }

        /*** End Code***/
        @Override
        public void handleMessage(android.os.Message msg) {
            vendor_add_restaurant add_restaurant = addRestaurantFragment.get();
            JSONObject response = null;
            response = ((JSONObject) msg.obj);
            try {
                String isSuccess = (String)response.get("Add_New_Restaurant");
                Log.d(TAG, "Request made.........................");
                if(isSuccess.equals("True")){
                    Toast.makeText(getContext(), "Restaurant added successfully!", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    lat.setText("");
                    lon.setText("");
                    address.setText("");
                    main.setText("");
                    sec.setText("");
                    submit.setText("");
                    cancel.setText("");
                    ((MainActivity)getContext()).swapFragments(new vendor_restaurant_details());
                }
                else{
                    Toast.makeText(getContext(), "Failed to add the restaurant!", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
