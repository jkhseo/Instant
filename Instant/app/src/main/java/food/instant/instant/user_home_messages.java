package food.instant.instant;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static food.instant.instant.EncryptionHelper.generateAESKEY;
import static food.instant.instant.HttpRequests.HttpGET;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link user_home_messages.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link user_home_messages#newInstance} factory method to
 * create an instance of this fragment.
 */
public class user_home_messages extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private messages_adapter adapter;
    private ArrayList<Conversation> conversations;
    private OnFragmentInteractionListener mListener;

    public user_home_messages() {
        // Required empty public constructor
        conversations = new ArrayList<>();
    }
    private static class MessagesHandler extends Handler {
        private final WeakReference<user_home_messages> messages;

        public MessagesHandler(user_home_messages messages) {
            this.messages = new WeakReference<user_home_messages>(messages);
        }

        /*** End Code***/
        @Override
        public void handleMessage(android.os.Message msg) {
            user_home_messages user_messages = messages.get();
            JSONArray Rest_Info = null;
            try {
                Rest_Info = ((JSONObject) msg.obj).getJSONArray("Restaurant_Name");
                JSONObject Rest = (JSONObject) Rest_Info.get(0);
                String rest_name = (String)Rest.get("Rest_Name");
                int  rest_id = (Integer)Rest.get("Rest_ID");
                for(int i=0;i<user_messages.conversations.size();i++){
                    if(user_messages.conversations.get(i).getRest_ID()==rest_id){
                        user_messages.conversations.get(i).setName(rest_name);
                    }
                }
                user_messages.adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user_home_messages.
     */
    // TODO: Rename and change types and number of parameters
    public static user_home_messages newInstance(String param1, String param2) {
        user_home_messages fragment = new user_home_messages();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public void addMessage(Message message){
        for(Conversation c : conversations){
            if(c.getRest_ID()==message.getRest_ID()){
                c.addMessage(message);
                break;
            }
        }
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
        View view = inflater.inflate(R.layout.fragment_user_home_messages, container, false);
        ListView messageList = view.findViewById(R.id.messages_list);

        getAllMessages();
        adapter = new messages_adapter(getContext(),conversations);
        messageList.setAdapter(adapter);
        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Conversation conversation = (Conversation) adapterView.getItemAtPosition(i);
                ((MainActivity)getActivity()).swapFragments(new user_home_chat(conversation));

            }
        });


        return view;
    }

    private void getAllMessages() {
        OrderDbHelper dbHelper = new OrderDbHelper(getContext());
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.readMessages(database);
        String Message,SenderType,RecieverType;
        int SenderID,RecieverID,Rest_ID,Read;
        Message temp;
        HashMap<Integer,Integer> conversationMap = new HashMap<Integer,Integer>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Message = cursor.getString(cursor.getColumnIndex(MessageContract.MessageEntry.MESSAGE));
            SenderType = cursor.getString(cursor.getColumnIndex(MessageContract.MessageEntry.SENDER_TYPE));
            RecieverType = cursor.getString(cursor.getColumnIndex(MessageContract.MessageEntry.RECIEVER_TYPE));
            SenderID = cursor.getInt(cursor.getColumnIndex(MessageContract.MessageEntry.SENDER_ID));
            RecieverID = cursor.getInt(cursor.getColumnIndex(MessageContract.MessageEntry.RECIEVER_ID));
            Rest_ID = cursor.getInt(cursor.getColumnIndex(MessageContract.MessageEntry.REST_ID));
            Read = cursor.getInt(cursor.getColumnIndex(MessageContract.MessageEntry.READ));
            temp = new Message(RecieverID,RecieverType,Message,SenderType,SenderID,Rest_ID,Read);
            if(conversationMap.containsKey(Rest_ID)){
                conversations.get(conversationMap.get(Rest_ID)).addMessage(temp);
            }
            else{
                ArrayList<Message> list = new ArrayList<>();
                list.add(temp);
                conversationMap.put(Rest_ID,conversations.size());
                HttpGET("getRestaurantFromID?Rest_ID="+Rest_ID,new MessagesHandler(this));
                if(SenderType.equals(SaveSharedPreference.getType(getContext()).substring(0,5))) {
                    conversations.add(new Conversation(list, RecieverType,RecieverID, Rest_ID));
                }
                else{
                    conversations.add(new Conversation(list,SenderType,SenderID,Rest_ID));
                }
            }
            cursor.moveToNext();
        }
        dbHelper.close();

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
