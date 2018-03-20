package food.instant.instant;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static food.instant.instant.HttpRequests.HttpGET;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VendorPendingOrdersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VendorPendingOrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VendorPendingOrdersFragment extends Fragment {
    private static String TAG = "MainActivity";
    private PendingOrdersHandler handler;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public VendorPendingOrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VendorPendingOrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VendorPendingOrdersFragment newInstance(String param1, String param2) {
        VendorPendingOrdersFragment fragment = new VendorPendingOrdersFragment();
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
        View view = inflater.inflate(R.layout.fragment_vendor_pending_orders, container, false);
        //http://proj-309-sd-4.cs.iastate.edu:8080/demo/getPendingOrderForRestaurant?Restaurant_ID=1
        handler = new PendingOrdersHandler(VendorPendingOrdersFragment.this);
        HttpGET("getPendingOrderForRestaurant?Restaurant_ID=1", handler);
        // Inflate the layout for this fragment
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

    private static class PendingOrdersHandler extends Handler {
        /***************************************************************************************
         *    Title: Stack Overflow Answer to Question about static handlers
         *    Author: Tomasz Niedabylski
         *    Date: July 10, 2012
         *    Availability: https://stackoverflow.com/questions/11407943/this-handler-class-should-be-static-or-leaks-might-occur-incominghandler
         ***************************************************************************************/
        private final WeakReference<VendorPendingOrdersFragment> pendingOrdersFragment;
        public PendingOrdersHandler(VendorPendingOrdersFragment pendingOrdersFragment) {
            this.pendingOrdersFragment = new WeakReference<VendorPendingOrdersFragment>(pendingOrdersFragment);
        }
        /*** End Code***/
        @Override
        public void handleMessage(Message msg) {
            VendorPendingOrdersFragment pendingOrders = pendingOrdersFragment.get();
            if (pendingOrders != null) {
                JSONArray response = null;
                try {
                    response = ((JSONObject) msg.obj).getJSONArray("All_User_Info");
                    /*ArrayList<Order> orders = new ArrayList<Order>();
                    for(int i = 0; i < response.length(); i++)
                    {
                        int userID = Integer.parseInt((String)((JSONObject) response.get(i)).get("User_ID"));
                        int foodQuantity = Integer.parseInt((String)((JSONObject) response.get(i)).get("Quantity"));
                        int restID = Integer.parseInt((String)((JSONObject) response.get(i)).get("Rest_ID"));
                        String restName = "someRestaurant";
                        String foodName = (String)((JSONObject) response.get(i)).get("Food_ID") + "food";
                        Food food = new Food(restID, foodName, 20.00, "some food", 0, "tag1", "tag2", Integer.parseInt((String)((JSONObject) response.get(i)).get("Food_ID")));
                        String comments = "comments";
                        Order tmpOrder = new Order(userID, food, comments, foodQuantity, restName);
                        orders.add(tmpOrder);
                    }*/
                    Log.d(TAG, "Request made.........................");
                    Log.d(TAG, response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
