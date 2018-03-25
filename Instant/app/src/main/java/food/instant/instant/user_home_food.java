package food.instant.instant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.DecimalFormat;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link user_home_food.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link user_home_food#newInstance} factory method to
 * create an instance of this fragment.
 */
public class user_home_food extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Food food;
    private String Rest_Name;
    private int quantity;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public user_home_food() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public user_home_food(Food temp, String Rest_Name){
        food = new Food(temp);
        this.Rest_Name = Rest_Name;
        quantity=1;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user_home_food.
     */
    // TODO: Rename and change types and number of parameters
    public static user_home_food newInstance(String param1, String param2) {
        user_home_food fragment = new user_home_food();
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
        View view = inflater.inflate(R.layout.fragment_user_home_food, container, false);
        TextView name = view.findViewById(R.id.name);
        name.setText(food.getFood_Name());
        Button addOrder = view.findViewById(R.id.add_order);
        FloatingActionButton addQuantity = view.findViewById(R.id.add_quantity);
        FloatingActionButton removeQuantity = view.findViewById(R.id.remove_quantity);
        final EditText comments = view.findViewById(R.id.editText);
        final EditText quantityText = view.findViewById(R.id.quantity);
        final EditText priceTotal = view.findViewById(R.id.price_total);
        final ToggleButton small = view.findViewById(R.id.small);
        small.setVisibility(View.GONE);
        final ToggleButton medium = view.findViewById(R.id.medium);
        medium.setVisibility(View.GONE);
        final ToggleButton large = view.findViewById(R.id.large);
        large.setVisibility(View.GONE);
        DecimalFormat df = new DecimalFormat("#.00");
        priceTotal.setText(df.format(food.getFood_Price()*quantity));
        priceTotal.setClickable(false);
        priceTotal.setFocusable(false);
        quantityText.setFocusable(false);
        quantityText.setClickable(false);
        quantityText.setText("1");
        small.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    medium.setChecked(false);
                    large.setChecked(false);

                }
            }
        });
        medium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    small.setChecked(false);
                    large.setChecked(false);

                }
            }
        });
        large.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    medium.setChecked(false);
                    small.setChecked(false);

                }
            }
        });
        addQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity++;
                DecimalFormat df = new DecimalFormat("#.00");
                quantityText.setText(Integer.toString(quantity));
                priceTotal.setText(df.format(food.getFood_Price()*quantity));
            }
        });
        removeQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity=Math.max(1,quantity-1);
                DecimalFormat df = new DecimalFormat("#.00");
                quantityText.setText(Integer.toString(quantity));
                priceTotal.setText(df.format(food.getFood_Price()*quantity));
            }
        });
        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addOrderToDatabase(comments.getText().toString());
            }
        });

        return view;
    }

    private void addOrderToDatabase(String comments) {
        OrderDbHelper orderDbHelper = new OrderDbHelper(getActivity());
        SQLiteDatabase database = orderDbHelper.getWritableDatabase();
        orderDbHelper.addOrder(food.getRest_ID(),food,quantity,comments,Rest_Name,database);
        orderDbHelper.close();
        ((MainActivity)getActivity()).swapFragments(new user_home_order(food.getRest_ID()));
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
