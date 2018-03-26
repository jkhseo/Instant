package food.instant.instant;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpauk on 3/20/2018.
 */
public class ChatSocket extends AsyncTask<Void,Void,Void>{
    private static final int PORT_NUMBER = 2222;
    private static final String HOST_NAME = "10.36.18.53";
    private List<Message> adapterList;
    private List<Message> outbox;
    private String ID;
    private chat_adapter adapter;
    private ListView view;

    private BufferedReader in;
    private PrintWriter out;
    public ChatSocket(List<Message> inbox,List<Message> outbox, ListView view,String ID){
        this.adapterList = inbox;
        this.outbox = outbox;
        this.view = view;
        this.ID = ID;
    }
    private int writeMessages(){
        synchronized (outbox) {
            for (Message message : outbox) {
                out.println(message.getFormattedMessage());
                synchronized (adapterList) {
                    adapterList.add(message);
                }
            }
        }
        publishProgress();
        out.flush();
        return 0;
    }
    @Override
    protected void onProgressUpdate(Void... voids){
        //System.out.println("updated"+adapterList.get(0).getMessage());
        ((chat_adapter)view.getAdapter()).notifyDataSetChanged();
        view.invalidate();
    }

    @SuppressLint("NewApi")
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            String input;
            Socket socket = new Socket(HOST_NAME,PORT_NUMBER);
            System.out.println("connected");
            out = new PrintWriter(socket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out.println("ID:" + ID);
                out.flush();
            while(true) {
                if(outbox.size()!=0){
                    System.out.println("wrote");
                    writeMessages();
                    outbox.clear();
                }
                /*input = in.readLine();
                System.out.println("recieved"+input);
                synchronized(adapterList) {
                    adapterList.add(new Message(input));
                }
                publishProgress();*/
               if(in.ready()){
                    System.out.println("recieved");
                    input = in.readLine();
                    synchronized(adapterList) {
                        adapterList.add(new Message(input));
                    }
                    publishProgress();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
