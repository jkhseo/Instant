package food.instant.instant;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link user_home_orders.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class user_home_orders extends Fragment{

    private OnFragmentInteractionListener mListener;

    public user_home_orders() {
        // Required empty public constructor
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
        int Rest_ID,Food_ID, Food_Quantity;
        double Food_Price;
        String Rest_Name,Food_Name,comments;
        Food tempFood;
        Order tempOrder;
        int counter=0;
        while(!cursor.isAfterLast()) {
            Rest_ID = cursor.getInt(cursor.getColumnIndex(OrderContract.OrderEntry.RESTAURANT_ID));
            Food_ID = cursor.getInt(cursor.getColumnIndex(OrderContract.OrderEntry.FOOD_ID));
            Food_Quantity = cursor.getInt(cursor.getColumnIndex(OrderContract.OrderEntry.FOOD_QUANTITY));
            Food_Price = cursor.getDouble(cursor.getColumnIndex(OrderContract.OrderEntry.FOOD_PRICE));
            Rest_Name = cursor.getString(cursor.getColumnIndex(OrderContract.OrderEntry.RESTAURANT_NAME));
            Food_Name = cursor.getString(cursor.getColumnIndex(OrderContract.OrderEntry.FOOD_NAME));
            comments = cursor.getString(cursor.getColumnIndex(OrderContract.OrderEntry.COMMENTS));
            tempFood = new Food(Rest_ID,Food_Name,Food_Price,Food_ID);
            tempOrder = new Order(0,tempFood,comments,Food_Quantity,Rest_Name);
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
        ExpandableListView pendingOrders = view.findViewById(R.id.ordersInProgress);
        ExpandableListView completedOrders = view.findViewById(R.id.completedOrders);
        user_OrderAdapter pendingAdapter = new user_OrderAdapter(getContext(),ordersInProgress);
        pendingOrders.setAdapter(pendingAdapter);
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
