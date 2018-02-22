package food.instant.instant;


import android.database.Cursor;
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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderViewAll extends Fragment {

    private TextView noOrders;
    private ListView ordersList;


    public OrderViewAll() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_order_view_all, container, false);
        noOrders = view.findViewById(R.id.td_NoOrders);
        ordersList = view.findViewById(R.id.lv_orders);
        readOrders();
        return view;
    }

    private void readOrders()
    {
        OrderDbHelper orderDbHelper = new OrderDbHelper(getActivity());
        SQLiteDatabase database = orderDbHelper.getReadableDatabase();


        Cursor cursor = orderDbHelper.readOrders(database);

        //String info = "";
        ArrayList<Order> orders = new ArrayList<Order>();
        while(cursor.moveToNext())
        {
            ArrayList<String> tmp = new ArrayList<String>();
            tmp.add(cursor.getString(cursor.getColumnIndex(OrderContract.OrderEntry.FOOD_NAME)));
            orders.add(new Order(cursor.getInt(cursor.getColumnIndex(OrderContract.OrderEntry.ORDER_ID)), 1, tmp, "comments"));
            //String id = Integer.toString(cursor.getInt(cursor.getColumnIndex(OrderContract.OrderEntry.ORDER_ID)));
            //String name = cursor.getString(cursor.getColumnIndex(OrderContract.OrderEntry.FOOD_NAME));
            //String email = cursor.getString(cursor.getColumnIndex(OrderContract.OrderEntry.EMAIL));
            //info = info + "\n\n" + "ID : " + id + "\n Name : " + name + "\n Email : " + email;
        }

        //txtDisplay.setText(info);
        updateListView(orders);

        orderDbHelper.close();
    }

    public void updateListView(ArrayList<Order> resArray) {
        if(noOrders.getVisibility() == View.VISIBLE){
            noOrders.setVisibility(View.INVISIBLE);
        }
        ordersList.setVisibility(View.VISIBLE);
        OrderAdapter listAdapter = new OrderAdapter(getContext(),resArray);
        ordersList.setAdapter(listAdapter);
        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Restaurant clicked = (Restaurant) adapterView.getItemAtPosition(i);
                ((MainActivity)getActivity()).swapFragments(new user_home_restaurant(clicked));
            }
        });*/

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
