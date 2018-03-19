package food.instant.instant;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;
import static food.instant.instant.GlobalConstants.MY_PERMISSIONS_REQUEST_LOCATION;
import static food.instant.instant.HttpRequests.GoogleMapsGET;
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
    SearchHandler handler;
    private Location currentLocation;
    private ProgressBar loadingCircle;
    private ArrayList<RestaurantSearchHelper> searchResults;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public user_home_search() {
        // Required empty public constructor
        searchResults = new ArrayList<RestaurantSearchHelper>();
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
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void handleMessage(Message msg) {
            user_home_search search = searchFragment.get();
            if (search != null && msg.what != GlobalConstants.EMPTY_JSON) {
                JSONArray response = null;
                try {
                    if (msg.what == GlobalConstants.RESTAURANT_SEARCH_CODE) {
                        response = ((JSONObject) msg.obj).getJSONArray("Restaurant_Search_Results");
                        int Rest_ID, Rest_Rating, rank;
                        String Rest_Name, Rest_Address;
                        double Rest_Coordinate_X, Rest_Coordinate_Y;
                        for (int i = 0; i < response.length(); i++) {
                            Rest_ID =  Integer.parseInt((String)((JSONObject)response.get(i)).get("Rest_ID"));
                            Rest_Name = (String) ((JSONObject) response.get(i)).get("Rest_Name");
                            Rest_Address = (String)((JSONObject)response.get(i)).get("Rest_Address");
                            Rest_Coordinate_X = Double.parseDouble((String)((JSONObject)response.get(i)).get("Rest_Coordinate_Lat"));
                            Rest_Coordinate_Y = Double.parseDouble((String)((JSONObject)response.get(i)).get("Rest_Coordinate_Lat"));
                            Rest_Rating = 1;//(int)((JSONObject)response.get(i)).get("Rest_Rating");
                            rank = Integer.parseInt((String)((JSONObject) response.get(i)).get("Rank"));
                            search.searchResults.add(search.new RestaurantSearchHelper(new Restaurant(Rest_ID,Rest_Name, Rest_Coordinate_X, Rest_Coordinate_Y, Rest_Address, Rest_Rating),rank));
                        }
                        if(search.searchResults.size()==0){
                            search.noResults();
                        }
                        else {
                            search.getDistances();
                        }
                    } else if (msg.what == GlobalConstants.FUZZY_SEARCH_CODE) {
                        String[] fuzzyResults = new String[5];
                        response = ((JSONObject) msg.obj).getJSONArray("Fuzzy_Search_Results");
                        for (int i = 0; i < response.length(); i++) {
                            fuzzyResults[i] = (String) ((JSONObject) response.get(i)).get("Rest_Name");

                        }
                        search.updateAutoComplete(fuzzyResults);
                    }else if(msg.what == GlobalConstants.GOOGLE_MAPS_DISTANCES){
                        JSONArray elements = ((JSONObject)msg.obj).getJSONArray("rows").getJSONObject(0).getJSONArray("elements");
                        for(int i=0;i<elements.length();i++){
                            search.searchResults.get(i).getRestaurant().setDistance(((Integer)((JSONObject)((JSONObject)elements.get(i)).get("distance")).get("value"))*0.000621371);
                        }
                        search.sortSearchResults();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class RestaurantSearchHelper {
        private Restaurant restaurant;
        private int rank;

        public RestaurantSearchHelper(Restaurant res, int rank) {
            restaurant = res;
            this.rank = rank;
        }
        public int getRank() {
            return rank;
        }
        public void setRank(int rank) {
            this.rank = rank;
        }
        public Restaurant getRestaurant() {
            return restaurant;
        }
        public void setRestaurant(Restaurant restaurant) {
            this.restaurant = restaurant;
        }
    }
    private static class SearchComparator implements Comparator<RestaurantSearchHelper>{
        @Override
        public int compare(RestaurantSearchHelper o1, RestaurantSearchHelper o2) {
            return o2.rank - o1.rank;
        }
    }
    public void noResults(){
        TextView view = getView().findViewById(R.id.no_results);
        ListView listView = getView().findViewById(R.id.listView);
        listView.setVisibility(View.INVISIBLE);
        view.setVisibility(view.VISIBLE);
        loadingCircle.setVisibility(View.INVISIBLE);

    }
    /**
     * Overrided method that waits for the result of requested location machine, if permission is granted
     * the current location is assigned
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[],int[] grantResults){
        if(requestCode == MY_PERMISSIONS_REQUEST_LOCATION){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                FusedLocationProviderClient flc = flc = LocationServices.getFusedLocationProviderClient(getActivity());
                flc.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        currentLocation= location;
                    }
                });
            }
            else{
                currentLocation=null;
            }
        }

    }

    /**
     * If location permission has been granted, this method query's the google database to get the distances
     * for all of the results of the search, and then calls a method to sort the results
     */
    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getDistances(){
        if(currentLocation==null) {
            String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + currentLocation.getLatitude() + "," + currentLocation.getLongitude()+"&destinations=";
            for(int i=0;i<searchResults.size();i++){
                url+=searchResults.get(i).getRestaurant().getLatitude()+","+searchResults.get(i).getRestaurant().getLongitude();
                if(i!=(searchResults.size()-1))
                    url+="|";
            }
            GoogleMapsGET(url,handler);
        }
        else{
            sortSearchResults();
        }
    }

    /**
     * Sorts the search results based on distance and relevance
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortSearchResults() {
            for(int i=0;i<searchResults.size();i++){
                if(searchResults.get(i).getRestaurant().getDistance()<5)
                    searchResults.get(i).setRank(searchResults.get(i).getRank()+4);
                else if(searchResults.get(i).getRestaurant().getDistance()<10)
                    searchResults.get(i).setRank(searchResults.get(i).getRank()+3);
                else if(searchResults.get(i).getRestaurant().getDistance()<15)
                    searchResults.get(i).setRank(searchResults.get(i).getRank()+2);
                else if(searchResults.get(i).getRestaurant().getDistance()<25)
                    searchResults.get(i).setRank(searchResults.get(i).getRank()+1);
            }
        searchResults.sort(new SearchComparator());
        Restaurant[] resArray = new Restaurant[searchResults.size()];
        for(int i=0;i<resArray.length;i++){
            resArray[i] = searchResults.get(i).getRestaurant();
        }
        searchResults.clear();
        updateListView(resArray);
    }
    /**
     * Updates the autocomplete suggestions view after getting a response from the server
     * @param fuzzyResults
     *      array of suggestions
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void updateAutoComplete(String[] fuzzyResults) {
        final SearchView searchView = getView().findViewById(R.id.searchView);
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"_id","Restaurant"});
        Object[] temp = new Object[2];
        for(int i=0;i<fuzzyResults.length;i++){
            temp[0]=0;
            temp[1]=fuzzyResults[i];
            matrixCursor.addRow(temp);
        }
        AutoCompleteAdapter adapter = new AutoCompleteAdapter(getContext(),matrixCursor,false);
        searchView.setSuggestionsAdapter(adapter);
        int thresholdID = getResources().getIdentifier("android:id/search_src_text",null,null);
        AutoCompleteTextView auto_complete = searchView.findViewById(thresholdID);
        if(searchView.getQuery().length()!=0 && fuzzyResults.length!=0) {
            auto_complete.showDropDown();
        }
        auto_complete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String clicked = ((MatrixCursor)adapterView.getItemAtPosition(i)).getString(1);
                searchView.setQuery(clicked,true);
            }
        });
    }

    /**
     * Updates the list with sorted search results from the database query
     * @param resArray
     */
    public void updateListView(Restaurant[] resArray) {
        TextView noResults = getView().findViewById(R.id.no_results);
        if(noResults.getVisibility()==View.VISIBLE){
            noResults.setVisibility(View.INVISIBLE);
        }
        String[] resName = new String[resArray.length];
        for(int i=0;i<resName.length;i++){
            resName[i] = resArray[i].getName();
        }
        ListView listView = getView().findViewById(R.id.listView);
        listView.setVisibility(View.VISIBLE);
        int thresholdID = getResources().getIdentifier("android:id/search_src_text",null,null);
        AutoCompleteTextView auto_complete = (getView().findViewById(R.id.searchView)).findViewById(thresholdID);
        auto_complete.dismissDropDown();
        RestaurantAdapter listAdapter = new RestaurantAdapter(getContext(),resArray);
        listView.setAdapter(listAdapter);
        loadingCircle.setVisibility(View.INVISIBLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Restaurant clicked = (Restaurant) adapterView.getItemAtPosition(i);
                ((MainActivity)getActivity()).swapFragments(new user_home_restaurant(clicked));
            }
        });

    }
    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_home_search, container, false);

        SearchView searchView = view.findViewById(R.id.searchView);
        loadingCircle = view.findViewById(R.id.progressBar);
        loadingCircle.setVisibility(View.INVISIBLE);
        searchView.setIconified(false);
        handler = new SearchHandler(this);
        TextView noResults = view.findViewById(R.id.no_results);
        noResults.setVisibility(View.INVISIBLE);
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"_id","Restaurant"});
        AutoCompleteAdapter adapter = new AutoCompleteAdapter(getContext(),matrixCursor,false);
        searchView.setSuggestionsAdapter(adapter);
        int thresholdID = getResources().getIdentifier("android:id/search_src_text",null,null);
        AutoCompleteTextView auto_complete = searchView.findViewById(thresholdID);
        auto_complete.setThreshold(1);
        searchView.setQueryHint("Search for a Restaurant");
        if (checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }
        else{
            FusedLocationProviderClient flc = flc = LocationServices.getFusedLocationProviderClient(getActivity());
            flc.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    currentLocation = location;
                }
            });
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                s = s.replaceAll(" ","+");
                HttpGET("getSearchRestaurants?Keywords="+s,handler);
                ListView listView = getView().findViewById(R.id.listView);
                SimpleCursorAdapter adapter;
                loadingCircle.setIndeterminate(true);
                loadingCircle.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //updateAutoComplete(new String[]{"name","name1","name2","name3"});
                HttpGET("getFuzzySearchRestaurants?restaurantName="+s,handler);
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
