package food.instant.instant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
 * {@link vendor_message.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link vendor_message#newInstance} factory method to
 * create an instance of this fragment.
 */
public class vendor_message extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<Conversation> conversations;
    private int Rest_ID;
    private ArrayList<Integer> User_ID;
    private messages_adapter adapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private static class MessagesHandler extends Handler {
        private final WeakReference<vendor_message> messages;

        public MessagesHandler(vendor_message messages) {
            this.messages = new WeakReference<vendor_message>(messages);
        }

        /*** End Code***/
        @Override
        public void handleMessage(android.os.Message msg) {
            vendor_message user_messages = messages.get();
            JSONArray Rest_Info = null;
            try {
                Rest_Info = ((JSONObject) msg.obj).getJSONArray("Order_Status");
                JSONObject Rest = (JSONObject) Rest_Info.get(0);
                String first_name = (String)Rest.get("First_Name");
                String last_name = (String)Rest.get("Last_Name");
                for(int i=0;i<user_messages.conversations.size();i++){
                    if(user_messages.conversations.get(i).getId()==user_messages.User_ID.get(0)){
                        user_messages.User_ID.remove(0);
                        user_messages.conversations.get(i).setName(first_name+" "+last_name);
                        break;
                    }
                }
                user_messages.adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    public vendor_message() {
        conversations = new ArrayList<>();
        this.User_ID = new ArrayList<>();
    }
    @SuppressLint("ValidFragment")
    public vendor_message(int Rest_ID){
        this.Rest_ID=Rest_ID;
        conversations = new ArrayList<>();
        this.User_ID = new ArrayList<>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment vender_message.
     */
    // TODO: Rename and change types and number of parameters
    public static vendor_message newInstance(String param1, String param2) {
        vendor_message fragment = new vendor_message();
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

    public void addMessage(Message message){
        for(Conversation c : conversations){
            if(c.getId()==message.getSenderID()){
                c.addMessage(message);
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }
    public int getRest_ID(){
        return this.Rest_ID;
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
        Cursor cursor = dbHelper.getRestMessages(database,Rest_ID);
        String Message,SenderType,RecieverType;
        int SenderID,RecieverID,Rest_ID,Read;
        Message temp;
        HashMap<String,Integer> conversationMap = new HashMap<String,Integer>();
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
            if(conversationMap.containsKey(RecieverType+RecieverID)){
                conversations.get(conversationMap.get(RecieverType+RecieverID)).addMessage(temp);
            }
            else if(conversationMap.containsKey(SenderType+SenderID)){
                conversations.get(conversationMap.get(SenderType+SenderID)).addMessage(temp);
            }
            else{
                ArrayList<Message> list = new ArrayList<>();
                list.add(temp);
                if(RecieverType.equals(SaveSharedPreference.getType(getContext()).substring(0,5))){
                    conversationMap.put(SenderType+SenderID,conversations.size());
                    conversations.add(new Conversation(list,SenderType,SenderID,Rest_ID));
                    HttpGET("getFullName?User_ID="+SenderID,new MessagesHandler(this));
                    User_ID.add(SenderID);
                }
                else {
                    conversationMap.put(RecieverType+RecieverID,conversations.size());
                    conversations.add(new Conversation(list, RecieverType, RecieverID,Rest_ID));
                    HttpGET("getFullName?User_ID="+RecieverID,new MessagesHandler(this));
                    User_ID.add(RecieverID);

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
