package food.instant.instant;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static food.instant.instant.HttpRequests.HttpGET;
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
    private class OrderHandler extends Handler {
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
            if (msg.what == GlobalConstants.ORDER_SUBMISSION_RESPONSE) {
                JSONObject response = null;
                response = ((JSONObject) msg.obj);
                try {
                    String isSuccess = (String)response.get("Update_Order_Status_Success");
                    Log.d(TAG, "Request made.........................");
                    if(isSuccess.equals("True")){
                        Toast.makeText(getContext(), "Order Update Success!", Toast.LENGTH_SHORT).show();
                        ((MainActivity) order.getActivity()).swapFragments(new vendor_orders());
                    }
                    else{
                        Toast.makeText(getContext(), "Order Update Failed!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
    private static String TAG = "vendor_home_order";

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
        Button cancel = view.findViewById(R.id.b_cancel_order);
        Button confirm = view.findViewById(R.id.b_confirm_order);
        if(order.get(0).getStatus() == 'f')
        {
            cancel.setVisibility(View.GONE);
            confirm.setVisibility(View.GONE);
        }
        else if(order.get(0).getStatus() == 'c')
        {
            confirm.setText("Complete Order");
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpPost("updateOrderStatus?Order_ID=" + order.get(0).getOrder_ID() + "&Rest_ID=" + order.get(0).getRest_ID() + "&Order_Status=Cancelled", handler);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(order.get(0).getStatus() == 'p'){
                    //updateOrderStatus?Order_ID=" + order.get(0).getOrder_ID() + "&Rest_ID=" + order.get(0).getRest_ID() + "&Order_Status=Confirmed
                    HttpPost("updateOrderStatus?Order_ID=" + order.get(0).getOrder_ID() + "&Rest_ID=" + order.get(0).getRest_ID() + "&Order_Status=Confirmed", handler);
                }else {
                    IntentIntegrator integrator = new IntentIntegrator(getActivity());
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                    integrator.setPrompt("Scan Code");
                    integrator.setCameraId(0);
                    integrator.setBeepEnabled(true);
                    integrator.setBarcodeImageEnabled(false);
                    integrator.initiateScan();
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(intentResult != null) {
            if(intentResult.getContents() == null) {
                Log.d("MainActivity", "Cancelled");
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();

            } else {
                Log.d("MainActivity", "Scanned");
                int code = Integer.parseInt(intentResult.getContents());
                if(code == order.get(0).getOrder_Confirmation_Code()) {
                    Toast.makeText(getContext(), "Order Confirmation Success!", Toast.LENGTH_SHORT).show();
                    HttpPost("updateOrderStatus?Order_ID=" + order.get(0).getOrder_ID() + "&Rest_ID=" + order.get(0).getRest_ID() + "&Order_Status=Completed", handler);
                }
                else{
                    Toast.makeText(getContext(), "Invalid Code!" + code, Toast.LENGTH_SHORT).show();
                }
            }
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
