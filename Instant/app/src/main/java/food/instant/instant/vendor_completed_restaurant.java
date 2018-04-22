package food.instant.instant;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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
 * {@link vendor_completed_restaurant.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link vendor_completed_restaurant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class vendor_completed_restaurant extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private CompletedRestHandler handler;
    private ArrayList<Restaurant> these_restaurants = new ArrayList<Restaurant>();
    private ListView rest_list;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public vendor_completed_restaurant() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment vendor_completed_restaurant.
     */
    // TODO: Rename and change types and number of parameters
    public static vendor_completed_restaurant newInstance(String param1, String param2) {
        vendor_completed_restaurant fragment = new vendor_completed_restaurant();
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
        View view = inflater.inflate(R.layout.fragment_vendor_completed_restaurant, container, false);
        these_restaurants.clear();
        rest_list = view.findViewById(R.id.lv_completed_restaurant);
        handler = new CompletedRestHandler(vendor_completed_restaurant.this);
        HttpGET("getAllRestaurantsWCompletedOrderForOwner?User_ID=" + SaveSharedPreference.getId(getContext()), handler);
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

    private class CompletedRestHandler extends Handler {
        /***************************************************************************************
         *    Title: Stack Overflow Answer to Question about static handlers
         *    Author: Tomasz Niedabylski
         *    Date: July 10, 2012
         *    Availability: https://stackoverflow.com/questions/11407943/this-handler-class-should-be-static-or-leaks-might-occur-incominghandler
         ***************************************************************************************/
        private final WeakReference<vendor_completed_restaurant> completedRestActivity;
        public CompletedRestHandler(vendor_completed_restaurant completedRestActivity) {
            this.completedRestActivity = new WeakReference<vendor_completed_restaurant>(completedRestActivity);
        }
        /*** End Code***/
        @Override
        public void handleMessage(android.os.Message msg) {
            vendor_completed_restaurant completedRest = completedRestActivity.get();
            if (completedRest != null) {
                JSONArray response = null;
                try {
                    response = ((JSONObject) msg.obj).getJSONArray("Order_Status");
                    for(int i = 0; i < response.length(); i++)
                    {
                        String name = (String) ((JSONObject) response.get(i)).get("Rest_Name");
                        int Rest_ID = (int) ((JSONObject) response.get(i)).get("Rest_ID");
                        these_restaurants.add(new Restaurant(Rest_ID, name, (double)0, (double)0, "", (double)0, "", ""));
                    }
                    Restaurant[] temp = new Restaurant[these_restaurants.size()];
                    for(int i = 0; i < these_restaurants.size(); i++)
                    {
                        temp[i] = these_restaurants.get(i);
                    }
                    pending_rest_adapter adapter = new pending_rest_adapter(getContext(), temp, "completed");
                    rest_list.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
