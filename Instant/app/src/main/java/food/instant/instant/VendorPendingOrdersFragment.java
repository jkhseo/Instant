package food.instant.instant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
    private static String TAG = "VendorPendingOrdersFragment";
    private PendingOrdersHandler handler;
    private ListView lvPendingOrders;
    private static ArrayList<Restaurant> these_restaurants = new ArrayList<Restaurant>();

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
        //http://proj-309-sd-4.cs.iastate.edu:8080/demo/getPendingOrderForRestaurant?Restaurant_ID=7

        lvPendingOrders = view.findViewById(R.id.lv_vendorPendingOrders);
        handler = new PendingOrdersHandler(VendorPendingOrdersFragment.this);
        HttpGET("getRestaurantFromOwnerUserEmail?User_Email=" + SaveSharedPreference.getUserName(getContext()), handler);
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

    private class PendingOrdersHandler extends Handler {
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
            if(msg.what == GlobalConstants.USER_RESTAURANTS) {
                VendorPendingOrdersFragment pendingOrders = pendingOrdersFragment.get();
                if (pendingOrders != null) {
                    JSONArray response = null;
                    try {
                        response = ((JSONObject) msg.obj).getJSONArray("Restaurant_From_OwnerUserEmail");
                        for(int i = 0; i < response.length(); i++)
                        {
                            double latitude = (Double) ((JSONObject) response.get(i)).get("Rest_Coordinate_Lat");
                            double longitude = (Double)  ((JSONObject) response.get(i)).get("Rest_Coordinate_Long");
                            String name = (String) ((JSONObject) response.get(i)).get("Rest_Name");
                            String address = (String) ((JSONObject) response.get(i)).get("Rest_Address");
                            double rating = Double.parseDouble((String) ((JSONObject) response.get(i)).get("Rest_Rating"));
                            int Rest_ID = (int) ((JSONObject) response.get(i)).get("Rest_ID");
                            these_restaurants.add(new Restaurant(Rest_ID, name, latitude, longitude, address, rating));
                        }
                        // + these_restaurants.get(0).getRest_ID()
                        HttpGET("getPendingOrderForRestaurant?Restaurant_ID=7", handler);
                        Log.d(TAG, "Request made.........................");
                        Log.d(TAG, response.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if(msg.what == GlobalConstants.ORDERS) {
                VendorPendingOrdersFragment pendingOrders = pendingOrdersFragment.get();
                if (pendingOrders != null) {
                    JSONArray response = null;
                    try {
                        response = ((JSONObject) msg.obj).getJSONArray("All_Pending_Orders");
                        ArrayList<Order> tmpOrders = new ArrayList<Order>();
                        for (int i = 0; i < response.length(); i++) {
                            int orderID = (int) ((JSONObject) response.get(i)).get("Order_ID");
                            char status = 'q';
                            if (((String) ((JSONObject) response.get(i)).get("Order_Status")).equals("Pending")) {
                                status = 'p';
                            }
                            int Dummy_PK = (int) ((JSONObject) response.get(i)).get("DummyPK");
                            double foodPrice = Double.parseDouble((String) ((JSONObject) response.get(i)).get("Food_Price"));
                            int restID = (int) ((JSONObject) response.get(i)).get("Rest_ID");
                            String comments = (String) ((JSONObject) response.get(i)).get("Comments");
                            int orderConfCode = (int) ((JSONObject) response.get(i)).get("Order_Confirmation_Code");
                            int menuID = Integer.parseInt((String) ((JSONObject) response.get(i)).get("Menu_ID"));
                            int foodQuantity = (int) ((JSONObject) response.get(i)).get("Quantity");
                            String foodTagsMain = (String) ((JSONObject) response.get(i)).get("Food_Tags_Main");
                            String orderDateSubmitted = (String) ((JSONObject) response.get(i)).get("Order_Date_Submitted");
                            String foodTagsSecondary = (String) ((JSONObject) response.get(i)).get("Food_Tags_Secondary");
                            String orderDatePickup = (String) ((JSONObject) response.get(i)).get("Order_Date_Pick_Up");
                            int foodID = (int) ((JSONObject) response.get(i)).get("Food_ID");
                            int userID = (int) ((JSONObject) response.get(i)).get("User_ID");
                            String foodName = (String) ((JSONObject) response.get(i)).get("Food_Name");
                            String foodDesc = (String) ((JSONObject) response.get(i)).get("Food_Desc");
                            String restName = (String) ((JSONObject) response.get(i)).get("Rest_Name");
                            String userFirstName = (String) ((JSONObject) response.get(i)).get("First_Name");
                            String userLastName = (String) ((JSONObject) response.get(i)).get("Last_Name");
                            String userEmail = (String) ((JSONObject) response.get(i)).get("User_Email");
                            Food food = new Food(restID, foodName, foodPrice, foodDesc, menuID, foodTagsMain, foodTagsSecondary, foodID);
                            Order tmpOrder = new Order(orderID, userID, food, comments, foodQuantity, restName, status, Dummy_PK, orderConfCode, orderDateSubmitted, orderDatePickup, userFirstName, userLastName, userEmail, restID);
                            addOrderToDatabase(comments, food, foodQuantity, restName);
                            tmpOrders.add(tmpOrder);
                        }
                        Log.d(TAG, "" + tmpOrders.size());
                        ArrayList<ArrayList<Order>> orders = new ArrayList<ArrayList<Order>>();
                        for (int i = 0; i < tmpOrders.size(); i++) {
                            if (orders.size() == 0) {
                                ArrayList<Order> first = new ArrayList<Order>();
                                first.add(tmpOrders.get(i));
                                orders.add(first);
                            } else {
                                boolean added = false;
                                for (int j = 0; j < orders.size(); j++) {
                                    if (orders.get(j).get(0).getUser_ID() == tmpOrders.get(i).getUser_ID()) {
                                        orders.get(j).add(tmpOrders.get(i));
                                        added = true;
                                        break;
                                    }
                                }
                                if(!added) {
                                    ArrayList<Order> next = new ArrayList<Order>();
                                    next.add(tmpOrders.get(i));
                                    orders.add(next);
                                }
                            }
                        }

                        //ArrayList<ArrayList<Order>> tmp = new ArrayList<ArrayList<Order>>();
                        //tmp.add(tmpOrders);
                        vendor_home_orders_adapter pendingAdapter = new vendor_home_orders_adapter(getContext(), orders);
                        lvPendingOrders.setAdapter(pendingAdapter);
                        Log.d(TAG, "Request made.........................");
                        Log.d(TAG, response.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void addOrderToDatabase(String comments, Food food, int quantity, String Rest_Name) {
        OrderDbHelper orderDbHelper = new OrderDbHelper(getActivity());
        SQLiteDatabase database = orderDbHelper.getWritableDatabase();
        orderDbHelper.addOrder(food.getRest_ID(),food,quantity,comments,Rest_Name,database);
        orderDbHelper.close();
    }
}
