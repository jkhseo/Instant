package food.instant.instant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link user_home_chat.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link user_home_chat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class user_home_chat extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PORT_NUMBER = 8884;
    private static final String HOST_NAME = "localhost";
    private String sendType;
    private int sendID;
    private BufferedReader in;
    private PrintWriter out;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public user_home_chat() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public user_home_chat(String sendAddress, int sendID){
        this.sendType = sendAddress;
        this.sendID = sendID;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user_home_chat.
     */
    // TODO: Rename and change types and number of parameters
    public static user_home_chat newInstance(String param1, String param2) {
        user_home_chat fragment = new user_home_chat();
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
        View view = inflater.inflate(R.layout.fragment_user_home_chat, container, false);
        Button sendMessage = view.findViewById(R.id.sendButton);
        List<Message> adapterList = Collections.synchronizedList(new ArrayList<Message>());
        List<Message> outbox = Collections.synchronizedList(new ArrayList<Message>());
        chat_adapter adapter = new chat_adapter(getContext(),adapterList);
        ListView listView = view.findViewById(R.id.chatView);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        listView.setAdapter(adapter);
        final EditText messageBox = view.findViewById(R.id.chatBox);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        messageBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.showSoftInput(messageBox,InputMethodManager.SHOW_IMPLICIT);
            }
        });
        final int id = Integer.parseInt(SaveSharedPreference.getId(getContext()));
        final String type = SaveSharedPreference.getType(getContext()).substring(0,5);
        sendMessage.setTag(outbox);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 synchronized (view.getTag()) {
                     ((List<Message>)view.getTag()).add(new Message(sendID, sendType, messageBox.getText().toString(), type, id));
                 }
            }
        });
        new ChatSocket(adapterList,outbox,listView,SaveSharedPreference.getType(getContext()).substring(0,5)+SaveSharedPreference.getId(getContext())).execute();
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
