package food.instant.instant;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static food.instant.instant.HttpRequests.HttpPost;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link vendor_home_order.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link vendor_home_order#newInstance} factory method to
 * create an instance of this fragment.
 */
public class vendor_home_order extends Fragment {
    private static class OrderHandler extends Handler {
        /***************************************************************************************
         *    Title: Stack Overflow Answer to Question about static handlers
         *    Author: Tomasz Niedabylski
         *    Date: July 10, 2012
         *    Availability: https://stackoverflow.com/questions/11407943/this-handler-class-should-be-static-or-leaks-might-occur-incominghandler
         ***************************************************************************************/
        private final WeakReference<vendor_home_order> orderFragment;

        public OrderHandler(vendor_home_order ordersFragment) {
            this.orderFragment = new WeakReference<vendor_home_order>(ordersFragment);
        }

        /*** End Code***/
        @Override
        public void handleMessage(Message msg) {
            vendor_home_order order = orderFragment.get();
            try {
                if (msg.what == GlobalConstants.ORDER_SUBMISSION_RESPONSE) {
                    JSONObject success = (JSONObject) msg.obj;
                    if (success.get("Success").equals("True")) {
                        OrderDbHelper dbHelper = new OrderDbHelper(order.getContext());
                        dbHelper.removePendingOrders(dbHelper.getWritableDatabase());
                        dbHelper.close();
                        order.showPopup("Order Submitted Successfully");
                        ((MainActivity)order.getActivity()).swapFragments(new user_home());
                    } else {
                        order.showPopup("Order Submission Failed");
                    }
                } else {
                    ArrayList<ArrayList<Order>> ordersByRes = new ArrayList<>();
                    ordersByRes.add(new ArrayList<Order>());
                    ordersByRes.add(new ArrayList<Order>());
                    ordersByRes.add(new ArrayList<Order>());
                    Food tempFood, tempFood2, tempFood3, TempFood4;
                    tempFood = new Food(1, "TestFood", 9.99, 4);
                    tempFood2 = new Food(1, "TestFood2", 1.99, 3);
                    tempFood3 = new Food(2, "TestFood3", 2.50, 2);
                    //TODO: ordersByRes.get(0).add(new Order(0, Integer.parseInt(SaveSharedPreference.getId(order.getContext())), tempFood, "", 2, "TestRestaurant", 'C'));
                    //TODO: ordersByRes.get(0).add(new Order(0, Integer.parseInt(SaveSharedPreference.getId(order.getContext())), tempFood2, "", 1, "TestRestaurant", 'C'));
                    //TODO: ordersByRes.get(1).add(new Order(0, Integer.parseInt(SaveSharedPreference.getId(order.getContext())), tempFood3, "", 1, "TestRestaurant1", 'X'));
                    //TODO: ordersByRes.get(2).add(new Order(0, Integer.parseInt(SaveSharedPreference.getId(order.getContext())), tempFood, "", 1, "TestRestaurant", 'P'));
                    HashMap<Integer, Integer> categories = new HashMap<>();
                    String Rest_Name, Food_Name, Comments;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void showPopup(String message) {
        View popup = LayoutInflater.from(getActivity()).inflate(R.layout.orders_popup_window, null);
        ((TextView)popup.findViewById(R.id.message)).setText(message);
        PopupWindow orderConfirmation = new PopupWindow(popup, ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT,true);
        orderConfirmation.showAtLocation(popup, Gravity.CENTER,0,0);
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<Order> order;
    private int Rest_ID;
    private EditText totalPrice;
    private OrderHandler handler;
    private Calendar orderDT;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public vendor_home_order() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public vendor_home_order(ArrayList<Order> orders){
        this.order=orders;
        this.Rest_ID=-1;
    }
    @SuppressLint("ValidFragment")
    public vendor_home_order(int Rest_ID){
        this.Rest_ID = Rest_ID;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment vendor_home_order.
     */
    // TODO: Rename and change types and number of parameters
    public static vendor_home_order newInstance(String param1, String param2) {
        vendor_home_order fragment = new vendor_home_order();
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
    public void getLocalOrders(){
        order = new ArrayList<Order>();
        OrderDbHelper dbHelper = new OrderDbHelper(getActivity());
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.getLocalOrdersByRest(Rest_ID,database);
        cursor.moveToFirst();
        int Order_ID,Food_Quantity,Food_ID;
        String Rest_Name, Comments,Food_Name;
        double Food_Price;
        char Order_Status;

        while(!cursor.isAfterLast()){
            Order_ID = cursor.getInt(0);//cursor.getColumnIndex("_rowid_)));
            Food_Quantity = cursor.getInt(cursor.getColumnIndex(OrderContract.OrderEntry.FOOD_QUANTITY));
            Food_ID = cursor.getInt(cursor.getColumnIndex(OrderContract.OrderEntry.FOOD_ID));
            Rest_Name = cursor.getString(cursor.getColumnIndex(OrderContract.OrderEntry.RESTAURANT_NAME));
            Comments = cursor.getString(cursor.getColumnIndex(OrderContract.OrderEntry.COMMENTS));
            Food_Name = cursor.getString(cursor.getColumnIndex(OrderContract.OrderEntry.FOOD_NAME));
            Food_Price = cursor.getDouble(cursor.getColumnIndex(OrderContract.OrderEntry.FOOD_PRICE));
            Order_Status = cursor.getString(cursor.getColumnIndex(OrderContract.OrderEntry.ORDER_STATUS)).charAt(0);
            //TODO: order.add(new Order(Order_ID,Integer.parseInt(SaveSharedPreference.getId(getContext())),new Food(Rest_ID,Food_Name,Food_Price,Food_ID),Comments,Food_Quantity,Rest_Name,Order_Status));
            cursor.moveToNext();
        }
        dbHelper.close();
    }
    public void updateTotalPrice(){
        double price = 0;
        for(int i=0;i<order.size();i++){
            price+=order.get(i).getFood().getFood_Price();
        }
        totalPrice.setText(""+price);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vendor_home_order, container, false);
        orderDT = Calendar.getInstance();
        handler = new OrderHandler(this);
        if(Rest_ID!=-1){
            getLocalOrders();
        }
        ListView listView = view.findViewById(R.id.orders);
        totalPrice = view.findViewById(R.id.total);
        totalPrice.setClickable(false);
        updateTotalPrice();
        vendor_home_order_adapter adapter = new vendor_home_order_adapter(getActivity(),order,this);
        listView.setAdapter(adapter);
        String[] dropDown = {"Please select a payment option"};
        ArrayAdapter<String> dropDownAdapter = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,dropDown);
        Button leftButton = view.findViewById(R.id.button5);
        final Button rightButton = view.findViewById(R.id.button6);
        if(order.size()!=0){
            if(order.get(0).getStatus()=='L'){
                leftButton.setText("Add to Order");
                leftButton.setTag(getActivity());
                leftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MainActivity)view.getTag()).swapFragments(new user_home_restaurant(new Restaurant(order.get(0).getFood().getRest_ID(),order.get(0).getRestaurant_Name(),0,0,"",0)));
                    }
                });
                rightButton.setText("Submit Order");
                rightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String rowids="";
                        String[] orderInfo = {"","","","",""};
                        for(int j=0;j<order.size();j++){
                            rowids+= order.get(j).getOrder_ID();
                            if(j!=order.size()-1)
                                rowids+=", ";
                            orderInfo[0]= orderInfo[0]+order.get(j).getFood().getRest_ID()+",";
                            orderInfo[1]= orderInfo[1]+order.get(j).getUser_ID()+",";
                            orderInfo[2] = orderInfo[2]+order.get(j).getFood().getFood_ID()+",";
                            orderInfo[3] = orderInfo[3]+order.get(j).getComments()+",";
                            orderInfo[4] = orderInfo[4]+order.get(j).getFood_Quantity()+",";
                            order.get(j).setStatus('P');
                        }
                        String url = "addOrder?Rest_ID="+orderInfo[0].substring(0,orderInfo[0].length()-1)+
                                "&User_ID="+orderInfo[1].substring(0,orderInfo[1].length()-1)+
                                "&Food="+ orderInfo[2].substring(0,orderInfo[2].length()-1)+
                                "&Comments="+orderInfo[3].substring(0,orderInfo[3].length()-1)+
                                "&Quantity="+orderInfo[4].substring(0,orderInfo[4].length()-1);
                        OrderDbHelper dbHelper = new OrderDbHelper(getContext());
                        SQLiteDatabase database = dbHelper.getWritableDatabase();
                        dbHelper.updatePendingOrders(rowids,database);
                        dbHelper.close();
                        HttpPost(url,handler);
                    }
                });
            }
            else if(order.get(0).getStatus()=='P'){
                leftButton.setText("Waiting on Restaurant Confimation");
                leftButton.setClickable(false);
                rightButton.setText("Cancel Order");
                rightButton.setTag(getActivity());
                rightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*OrderDbHelper dbHelper = new OrderDbHelper((MainActivity)view.getTag());
                        SQLiteDatabase database = dbHelper.getWritableDatabase();
                        dbHelper.removeRestOrders(order.get(0).getFood().getRest_ID(),database);
                        dbHelper.close();*/
                        ((MainActivity)view.getTag()).swapFragments(new user_home());
                    }
                });
            }
            else if(order.get(0).getStatus()=='X'){
                leftButton.setVisibility(View.GONE);
                rightButton.setVisibility(View.GONE);

            }
            else if(order.get(0).getStatus()=='C'){
                leftButton.setVisibility(View.GONE);
                rightButton.setText("View Confirmation Code");
                rightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        }
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
}
