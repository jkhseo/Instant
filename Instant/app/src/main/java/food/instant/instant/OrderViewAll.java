package food.instant.instant;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderViewAll extends Fragment {

    private TextView txtDisplay;


    public OrderViewAll() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_order_view_all, container, false);
        txtDisplay = view.findViewById(R.id.text_display);
        readOrders();
        return view;
    }

    private void readOrders()
    {
        OrderDbHelper orderDbHelper = new OrderDbHelper(getActivity());
        SQLiteDatabase database = orderDbHelper.getReadableDatabase();


        //TODO: take off of main thread
        Cursor cursor = orderDbHelper.readOrders(database);

        String info = "";
        while(cursor.moveToNext())
        {
            String id = Integer.toString(cursor.getInt(cursor.getColumnIndex(OrderContract.OrderEntry.ORDER_ID)));
            String name = cursor.getString(cursor.getColumnIndex(OrderContract.OrderEntry.FOOD_NAME));
            String email = cursor.getString(cursor.getColumnIndex(OrderContract.OrderEntry.EMAIL));
            info = info + "\n\n" + "ID : " + id + "\n Name : " + name + "\n Email : " + email;
        }

        txtDisplay.setText(info);

        orderDbHelper.close();
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
