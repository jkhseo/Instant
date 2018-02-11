package food.instant.instant;

import android.app.ActionBar;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Arrays;

import static food.instant.instant.HttpRequests.HttpGET;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link user_home_search.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link user_home_search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class user_home_search extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ProgressBar loadingCircle;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public user_home_search() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user_home_search.
     */
    // TODO: Rename and change types and number of parameters
    public static user_home_search newInstance(String param1, String param2) {
        user_home_search fragment = new user_home_search();
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

    private static class SearchHandler extends Handler {
        /***************************************************************************************
         *    Title: Stack Overflow Answer to Question about static handlers
         *    Author: Tomasz Niedabylski
         *    Date: July 10, 2012
         *    Availability: https://stackoverflow.com/questions/11407943/this-handler-class-should-be-static-or-leaks-might-occur-incominghandler
         ***************************************************************************************/
        private final WeakReference<user_home_search> searchFragment;
        public SearchHandler(user_home_search searchFragment) {
            this.searchFragment = new WeakReference<user_home_search>(searchFragment);
        }
        /*** End Code***/
        @Override
        public void handleMessage(Message msg) {
            user_home_search search = searchFragment.get();
            if (search != null) {
                JSONArray response = null;
                try {
                    response = ((JSONObject) msg.obj).getJSONArray("Restaurants");
                    Restaurant[] resArray = new Restaurant[response.length()];
                    int Rest_ID, Rest_Rating;
                    String Rest_Name,Rest_Address;
                    double Rest_Coordinate_X, Rest_Coordinate_Y;
                    for(int i=0;i<resArray.length;i++){
                       Rest_ID =  1;//(int)((JSONObject)response.get(i)).get("Rest_ID");
                       Rest_Name = (String)((JSONObject)response.get(i)).get("Rest_Name");
                       Rest_Address = "";//(String)((JSONObject)response.get(i)).get("Rest_Address");
                       Rest_Coordinate_X = 1;//(double)((JSONObject)response.get(i)).get("Rest_Coordinate_X");
                       Rest_Coordinate_Y = 1;//(double)((JSONObject)response.get(i)).get("Rest_Coordinate_Y");
                       Rest_Rating = 1;//(int)((JSONObject)response.get(i)).get("Rest_Rating");
                       resArray[i] = new Restaurant(Rest_ID,Rest_Name,Rest_Coordinate_X,Rest_Coordinate_Y,Rest_Address,Rest_Rating);
                    }
                    search.updateListView(resArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void updateListView(Restaurant[] resArray) {
        String[] resName = new String[resArray.length];
        for(int i=0;i<resName.length;i++){
            resName[i] = resArray[i].getName();
        }
        ListView listView = getView().findViewById(R.id.listView);
        RestaurantAdapter listAdapter = new RestaurantAdapter(getContext(),resArray);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Restaurant clicked = (Restaurant) adapterView.getItemAtPosition(i);
                ((MainActivity)getActivity()).swapFragments(new user_home_restaurant(clicked));


            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_home_search, container, false);

        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setIconified(false);
        final SearchHandler handler = new SearchHandler(this);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                HttpGET("getRestaurants",handler);
                ListView listView = getView().findViewById(R.id.listView);
                SimpleCursorAdapter adapter;
                loadingCircle = new ProgressBar(getContext());
                loadingCircle.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
                loadingCircle.setIndeterminate(true);
                listView.setEmptyView(loadingCircle);
                ViewGroup content = (ViewGroup)getView().findViewById(R.id.user_home_search);
                content.addView(loadingCircle);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
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
