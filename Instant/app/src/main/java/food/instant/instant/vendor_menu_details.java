package food.instant.instant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.*;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import static food.instant.instant.HttpRequests.HttpGET;
import static food.instant.instant.HttpRequests.HttpPost;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link vendor_menu_details.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link vendor_menu_details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class vendor_menu_details extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Restaurant restaurant;
    private static String TAG = "vendor_menu_details";

    private EditText foodName;
    private EditText foodPrice;
    private EditText foodDesc;
    private EditText foodTagsMain;
    private EditText foodTagsSec;
    private Button submit;

    private vendorMenuDetailsHandler handler;

    public vendor_menu_details() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public vendor_menu_details(Restaurant rest){
        this.restaurant = rest;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment vendor_menu_details.
     */
    // TODO: Rename and change types and number of parameters
    public static vendor_menu_details newInstance(String param1, String param2) {
        vendor_menu_details fragment = new vendor_menu_details();
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
        this.handler = new vendorMenuDetailsHandler(this);
        HTTPGET("")

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_vendor_menu_details, container, false);
        foodName = view.findViewById(R.id.et_food_name);
        foodPrice = view.findViewById(R.id.et_food_price);
        foodDesc = view.findViewById(R.id.et_food_desc);
        foodTagsMain = view.findViewById(R.id.et_food_tags_main);
        foodTagsSec = view.findViewById(R.id.et_food_tags_sec);
        submit = view.findViewById(R.id.bn_add);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = foodName.getText().toString();
                String price = foodPrice.getText().toString();
                String desc = foodDesc.getText().toString();
                String mainTags = foodTagsMain.getText().toString();
                String secTags = foodTagsSec.getText().toString();
                Double dPrice;

                if(name.length() == 0 || price.length() == 0 || desc.length() == 0 || mainTags.length() == 0){
                    Toast.makeText(getContext(), "Please fill in all required fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                dPrice = Double.parseDouble(price);

                HttpPost("addFood?Rest_ID=" + restaurant.getRest_ID() + "&Food_Name=" + name + "&Food_Price=" + dPrice + "&Food_Desc=" + desc + "&Menu_ID=1&Food_Tags_Main=" + mainTags +"&Food_Tags_Secondary=" + secTags, handler);


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

    private class vendorMenuDetailsHandler extends Handler {
        /***************************************************************************************
         *    Title: Stack Overflow Answer to Question about static handlers
         *    Author: Tomasz Niedabylski
         *    Date: July 10, 2012
         *    Availability: https://stackoverflow.com/questions/11407943/this-handler-class-should-be-static-or-leaks-might-occur-incominghandler
         ***************************************************************************************/
        private final WeakReference<vendor_menu_details> vendorMenuDetails;
        public vendorMenuDetailsHandler(vendor_menu_details vendorMenuDetails) {
            this.vendorMenuDetails = new WeakReference<vendor_menu_details>(vendorMenuDetails);
        }
        /*** End Code***/
        @Override
        public void handleMessage(android.os.Message msg) {
           if(msg.what == GlobalConstants.ADD_FOOD) {
                JSONObject response = null;
                    response = ((JSONObject) msg.obj);
            try {
                String isSuccess = (String)response.get("Add_Food_Item_Success");
                Log.d(TAG, "Request made.........................");
                foodName.setText("");
                foodPrice.setText("");
                foodDesc.setText("");
                foodTagsMain.setText("");
                foodTagsSec.setText("");
                if(isSuccess.equals("True")){
                    Toast.makeText(getContext(), "Food added successfully!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "Failed to add the food item!", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
           }
            else if(msg.what == GlobalConstants.USERINFO)
           {

           }
        }
    }
}
