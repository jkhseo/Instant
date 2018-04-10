package food.instant.instant;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;

/**
 * Background Service that listens for chat messages from the server
 * it is bound to MainActivity.
 *
 * Created by mpauk on 4/1/2018.
 */

public class ChatService extends IntentService {
    private static final int PORT_NUMBER = 2222;
    private static final String HOST_NAME = "10.26.53.208";
    private volatile boolean isRunning = true;
    private Messenger handler;

    /**
     * Default Constructor.
     */
    public ChatService() {
        super("ChatService");
    }

    /**
     * Creates a new thread that listens for input from the server and when it recieves
     * a message from the server it sends the data to the handler bound to this activity.
     */
    //@Override
    public void onCreate(){
        final String ID = "ID:"+SaveSharedPreference.getType(this).substring(0,5)+SaveSharedPreference.getId(this);
        super.onCreate();

    }

    /**
     *Overriden Method from superclass that is called when the service
     * is being destroyed
     */
    @Override
    public void onDestroy(){
        isRunning=false;
    }


    /**
     * Handles intent information passed from Activity.
     * @param intent contains information for task to be performed
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            Bundle extras = intent.getExtras();
            this.handler = (Messenger) extras.get("Handler");
            final String ID = "ID:"+SaveSharedPreference.getType(this).substring(0,5)+SaveSharedPreference.getId(this);
            Message message;
            Socket socket = new Socket(HOST_NAME,PORT_NUMBER);
            System.out.println("Connected");
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println(ID);
            out.flush();
            while(isRunning){
                if (in.ready()){
                    android.os.Message msg = new android.os.Message();
                    String text = in.readLine();
                    System.out.println(text);
                    message= new Message(text);
                    msg.obj = message;
                    handler.send(msg);
                }
            }
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}
