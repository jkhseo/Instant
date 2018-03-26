package food.instant.instant;

import android.app.ActionBar;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import static food.instant.instant.HttpRequests.HttpGET;
import static food.instant.instant.HttpRequests.HttpPost;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link user_home_orders.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class user_home_orders extends Fragment{
    private OrdersHandler handler;
    private PopupWindow orderConfirmation;
    private ProgressBar sendingOrder;
    private static class OrdersHandler extends Handler {
        /***************************************************************************************
         *    Title: Stack Overflow Answer to Question about static handlers
         *    Author: Tomasz Niedabylski
         *    Date: July 10, 2012
         *    Availability: https://stackoverflow.com/questions/11407943/this-handler-class-should-be-static-or-leaks-might-occur-incominghandler
         ***************************************************************************************/
        private final WeakReference<user_home_orders> ordersFragment;
        public OrdersHandler(user_home_orders ordersFragment) {
            this.ordersFragment = new WeakReference<user_home_orders>(ordersFragment);
        }
        /*** End Code***/
        @Override
        public void handleMessage(Message msg) {
            user_home_orders orders = ordersFragment.get();
            try{
                   ArrayList<ArrayList<Order>> ordersByRes = new ArrayList<>();
                    /*ordersByRes.add(new ArrayList<Order>());
                    ordersByRes.add(new ArrayList<Order>());
                    ordersByRes.add(new ArrayList<Order>());
                    Food tempFood,tempFood2,tempFood3,TempFood4;
                    tempFood = new Food(1,"TestFood",9.99,4);
                    tempFood2 = new Food(1,"TestFood2",1.99,3);
                    tempFood3 = new Food(2,"TestFood3",2.50,2);
                    ordersByRes.get(0).add(new Order(0,Integer.parseInt(SaveSharedPreference.getId(orders.getContext())),tempFood,"",2,"TestRestaurant",'C'));
                    ordersByRes.get(0).add(new Order(0,Integer.parseInt(SaveSharedPreference.getId(orders.getContext())),tempFood2,"",1,"TestRestaurant",'C'));
                    ordersByRes.get(1).add(new Order(0,Integer.parseInt(SaveSharedPreference.getId(orders.getContext())),tempFood3,"",1,"TestRestaurant1",'X'));
                    ordersByRes.get(2).add(new Order(0,Integer.parseInt(SaveSharedPreference.getId(orders.getContext())),tempFood,"",1,"TestRestaurant",'P'));*/
                    HashMap<Integer, Integer> categories = new HashMap<>();
                    String Rest_Name, Food_Name, Comments,PickupDate;
                    int Rest_ID, Food_ID, Food_Quantity, Order_Group_ID;
                    double Food_Price;
                    char status;
                    String orderStatus;
                    Order tempOrder;
                    Food tempFood;
                    JSONArray orderArray = ((JSONObject)msg.obj).getJSONArray("All_Orders_For_User");
                    int counter = 0;
                    for (int i = 0; i < orderArray.length(); i++) {
                        Rest_Name = (String) ((JSONObject) orderArray.get(i)).get("Rest_Name");
                        Food_Name = (String) ((JSONObject) orderArray.get(i)).get("Food_Name");
                        Comments = (String) ((JSONObject) orderArray.get(i)).get("Comments");
                        Rest_ID = (Integer) ((JSONObject) orderArray.get(i)).get("Rest_ID");
                        Food_ID = (Integer)((JSONObject) orderArray.get(i)).get("Food_ID");
                        Food_Quantity = (Integer) ((JSONObject) orderArray.get(i)).get("Quantity");
                        Food_Price = Double.parseDouble((String)((JSONObject) orderArray.get(i)).get("Food_Price"));
                        PickupDate = (String)((JSONObject)orderArray.get(i)).get("Order_Date_Pick_Up");
                        tempFood = new Food(Rest_ID, Food_Name, Food_Price, Food_ID);
                        orderStatus = (String) ((JSONObject) orderArray.get(i)).get("Order_Status");
                        Order_Group_ID = (Integer) ((JSONObject) orderArray.get(i)).get("Order_ID");
                        if(orderStatus.equals("Pending"))
                            status = 'C';
                        else if(orderStatus.equals("Canceled"))
                            status = 'X';
                        else
                            status = 'C';
                        tempOrder = new Order(Order_Group_ID, Integer.parseInt(SaveSharedPreference.getId(orders.getContext())), tempFood, Comments, Food_Quantity, Rest_Name, status,PickupDate);

                        if (categories.containsKey(Order_Group_ID)) {
                            ordersByRes.get(categories.get(Order_Group_ID)).add(tempOrder);
                        } else {
                            ordersByRes.add(new ArrayList<Order>());
                            ordersByRes.get(counter).add(tempOrder);
                            categories.put(Order_Group_ID, counter);
                            counter++;
                        }
                    }
                    orders.updateHistoricalOrders(ordersByRes);

           } catch(JSONException e){
                e.printStackTrace();
                }
            }

    }

    private OnFragmentInteractionListener mListener;

    public user_home_orders() {
        // Required empty public constructor
    }
    public void updateHistoricalOrders(ArrayList<ArrayList<Order>> orders){
        ListView completedOrders = getView().findViewById(R.id.completedOrders);
        user_home_orders_adapter pendingAdapter = new user_home_orders_adapter(getContext(),orders);
        completedOrders.setAdapter(pendingAdapter);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ArrayList<ArrayList<Order>> ordersInProgress = new ArrayList<>();
        HashMap<Integer,Integer> orderCategories = new HashMap<>();
        ArrayList<ArrayList<Order>> orderHistory = new ArrayList<>();
        OrderDbHelper orderDbHelper = new OrderDbHelper(getActivity());
        SQLiteDatabase database = orderDbHelper.getReadableDatabase();
        Cursor cursor = orderDbHelper.readOrders(database);
        cursor.moveToFirst();
        int Rest_ID,Food_ID, Food_Quantity, Order_ID;
        double Food_Price;
        String Rest_Name,Food_Name,comments;
        Food tempFood;
        Order tempOrder;
        char order_status;
        int counter=0;
        while(!cursor.isAfterLast()) {
            Order_ID = cursor.getInt(cursor.getColumnIndex("rowid"));
            Rest_ID = cursor.getInt(cursor.getColumnIndex(OrderContract.OrderEntry.RESTAURANT_ID));
            Food_ID = cursor.getInt(cursor.getColumnIndex(OrderContract.OrderEntry.FOOD_ID));
            Food_Quantity = cursor.getInt(cursor.getColumnIndex(OrderContract.OrderEntry.FOOD_QUANTITY));
            Food_Price = cursor.getDouble(cursor.getColumnIndex(OrderContract.OrderEntry.FOOD_PRICE));
            Rest_Name = cursor.getString(cursor.getColumnIndex(OrderContract.OrderEntry.RESTAURANT_NAME));
            Food_Name = cursor.getString(cursor.getColumnIndex(OrderContract.OrderEntry.FOOD_NAME));
            comments = cursor.getString(cursor.getColumnIndex(OrderContract.OrderEntry.COMMENTS));
            order_status = cursor.getString(cursor.getColumnIndex(OrderContract.OrderEntry.ORDER_STATUS)).charAt(0);
            tempFood = new Food(Rest_ID,Food_Name,Food_Price,Food_ID);
            tempOrder = new Order(Order_ID,Integer.parseInt(SaveSharedPreference.getId(getContext())),tempFood,comments,Food_Quantity,Rest_Name,order_status,null);
            if(orderCategories.containsKey(Rest_ID)){
                ordersInProgress.get(orderCategories.get(Rest_ID)).add(tempOrder);
            }
            else{
                orderCategories.put(Rest_ID,counter);
                ordersInProgress.add(new ArrayList<Order>());
                ordersInProgress.get(counter).add(tempOrder);
                counter++;
            }
            cursor.moveToNext();
        }
        orderDbHelper.close();
        View view = inflater.inflate(R.layout.fragment_user_home_orders, container, false);
        ListView pendingOrders = view.findViewById(R.id.ordersInProgress);
        handler = new OrdersHandler(this);
        user_home_orders_adapter pendingAdapter = new user_home_orders_adapter(getActivity(),ordersInProgress);
        pendingOrders.setAdapter(pendingAdapter);
        HttpGET("getAllOrdersForUser?User_ID="+SaveSharedPreference.getId(getContext()),handler);
        return view;
    }
    public void orderInProgress(boolean inProgress,String message){
        if(inProgress) {
            sendingOrder.setVisibility(View.VISIBLE);
        }
        else{
            sendingOrder.setVisibility(View.INVISIBLE);
            View popup = LayoutInflater.from(getActivity()).inflate(R.layout.orders_popup_window, null);
            ((TextView)popup.findViewById(R.id.message)).setText(message);
            orderConfirmation = new PopupWindow(popup, ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT,true);
            orderConfirmation.showAtLocation(popup, Gravity.CENTER,0,0);

        }
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
}
