package food.instant.instant;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;


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
    private ArrayList<Conversation> conversations;

    private OnFragmentInteractionListener mListener;

    public user_home_messages() {
        // Required empty public constructor
        conversations = new ArrayList<>();
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
        //((MainActivity)getActivity()).swapFragments(new user_home_chat("Custo",7));
        //((MainActivity)getActivity()).swapFragments(new user_home_chat("Vendo",10));
        View view = inflater.inflate(R.layout.fragment_user_home_messages, container, false);
        ListView messageList = view.findViewById(R.id.messages_list);

        getAllMessages();
        messages_adapter adapter = new messages_adapter(getContext(),conversations);
        messageList.setAdapter(adapter);
        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Conversation conversation = (Conversation) adapterView.getItemAtPosition(i);

            }
        });


        return view;
    }

    private void getAllMessages() {
        MessageDbHelper dbHelper = new MessageDbHelper(getContext());
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.readMessages(database);
        dbHelper.close();
        String Message,SenderType,RecieverType;
        int SenderID,RecieverID;
        Message temp;
        HashMap<String,Integer> conversationMap = new HashMap<String,Integer>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Message = cursor.getString(cursor.getColumnIndex(MessageContract.MessageEntry.MESSAGE));
            SenderType = cursor.getString(cursor.getColumnIndex(MessageContract.MessageEntry.SENDER_TYPE));
            RecieverType = cursor.getString(cursor.getColumnIndex(MessageContract.MessageEntry.RECIEVER_TYPE));
            SenderID = cursor.getInt(cursor.getColumnIndex(MessageContract.MessageEntry.SENDER_ID));
            RecieverID = cursor.getInt(cursor.getColumnIndex(MessageContract.MessageEntry.RECIEVER_ID));
            temp = new Message(RecieverID,RecieverType,Message,SenderType,SenderID);
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
                    conversations.add(new Conversation(list,SenderType,SenderID));
                }
                else {
                    conversationMap.put(RecieverType+RecieverID,conversations.size());
                    conversations.add(new Conversation(list, RecieverType, RecieverID));

                }
            }
        }

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
