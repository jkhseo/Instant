package food.instant.instant;

import android.content.Context;
import android.net.Uri;
import android.os.*;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import static food.instant.instant.HttpRequests.HttpGET;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link vendor_messages.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link vendor_messages#newInstance} factory method to
 * create an instance of this fragment.
 */
public class vendor_messages extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<String> rest_names;
    private ArrayList<Integer> rest_IDs;
    private vendor_messages_adapter adapter;
    private static class VendorMessageHandler extends Handler {
        /***************************************************************************************
         *    Title: Stack Overflow Answer to Question about static handlers
         *    Author: Tomasz Niedabylski
         *    Date: July 10, 2012
         *    Availability: https://stackoverflow.com/questions/11407943/this-handler-class-should-be-static-or-leaks-might-occur-incominghandler
         ***************************************************************************************/
        private final WeakReference<vendor_messages> vendorMessages;
        public VendorMessageHandler(vendor_messages vendor_messages) {
            this.vendorMessages = new WeakReference<vendor_messages>(vendor_messages);
        }
        /*** End Code***/
        @Override
        public void handleMessage(android.os.Message msg) {
            final vendor_messages messages = vendorMessages.get();
            try {
                JSONArray array = ((JSONObject)msg.obj).getJSONArray("Restaurant_From_OwnerUserEmail");
                String Rest_Name;
                int Rest_ID;
                for(int i=0;i<array.length();i++){
                    Rest_Name = (String)((JSONObject)array.get(i)).get("Rest_Name");
                    Rest_ID = (Integer)((JSONObject)array.get(i)).get("Rest_ID");
                    messages.rest_names.add(Rest_Name);
                    messages.rest_IDs.add(Rest_ID);
                }
                ListView listView = messages.getView().findViewById(R.id.vendor_messages);
                messages.adapter = new vendor_messages_adapter(messages.getContext(),messages.rest_names,messages.rest_IDs);
                listView.setAdapter(messages.adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                         int rest_id = messages.rest_IDs.get(i);
                        ((MainActivity)messages.getActivity()).swapFragments(new vendor_message(rest_id));
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
    private OnFragmentInteractionListener mListener;

    public vendor_messages() {
        rest_IDs = new ArrayList<>();
        rest_names = new ArrayList<>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment vendor_messages.
     */
    // TODO: Rename and change types and number of parameters
    public static vendor_messages newInstance(String param1, String param2) {
        vendor_messages fragment = new vendor_messages();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public void notifyAdapter(){
        adapter.notifyDataSetChanged();
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
        View view = inflater.inflate(R.layout.fragment_vendor_messages, container, false);
        VendorMessageHandler handler = new VendorMessageHandler(this);
        HttpGET("getRestaurantFromOwnerUserID?User_ID="+SaveSharedPreference.getId(getContext()),handler);
        System.out.println("getRestaurantFromOwnerUserID?User_ID="+SaveSharedPreference.getId(getContext()));
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
