package food.instant.instant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrderAddNew.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrderAddNew#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderAddNew extends Fragment {

    private Button bAddOrder;
    EditText orderId, foodName, email;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public OrderAddNew() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderAddNew.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderAddNew newInstance(String param1, String param2) {
        OrderAddNew fragment = new OrderAddNew();
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
        View view = inflater.inflate(R.layout.fragment_order_add_new, container, false);

        bAddOrder = view.findViewById(R.id.bSubmitOrder);
        orderId = view.findViewById(R.id.etOrderId);
        foodName = view.findViewById(R.id.etFoodName);
        email = view.findViewById(R.id.etOrderEmail);

        bAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = orderId.getText().toString();
                String name = foodName.getText().toString();
                String emailStr = email.getText().toString();

                OrderDbHelper orderDbHelper = new OrderDbHelper(getActivity());

                //TODO: move off of the main thread.
                SQLiteDatabase database = orderDbHelper.getWritableDatabase();
                orderDbHelper.addOrder(Integer.parseInt(id), name, emailStr, database);
                orderDbHelper.close();

                orderId.setText("");
                foodName.setText("");
                email.setText("");

                Toast.makeText(getActivity(), "Order Saved Successfully.", Toast.LENGTH_SHORT).show();
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
}
