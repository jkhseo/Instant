package food.instant.instant;

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

public class ChatService extends Service {
    private static final int PORT_NUMBER = 2222;
    private static final String HOST_NAME = "10.36.18.4";
    private final IBinder binder = new ChatServiceBinder();
    private volatile boolean isRunning = true;
    private Messenger handler;

    /**
     * Creates a new thread that listens for input from the server and when it recieves
     * a message from the server it sends the data to the handler bound to this activity.
     */
    @Override
    public void onCreate(){
        final String ID = "ID:"+SaveSharedPreference.getType(this).substring(0,5)+SaveSharedPreference.getId(this);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message;

                    android.os.Message msg = new android.os.Message();
                    Socket socket = new Socket(HOST_NAME,PORT_NUMBER);
                    System.out.println("Connected");
                    PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out.println(ID);
                    out.flush();
                    //out.close();
                    while(isRunning){
                        if (in.ready()){
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
        });
        t.setDaemon(true);
        t.start();
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
     *Overriden Method from superclass that is called when the activity
     * binds with this service
     * @param intent intent that contains a handler and user ID
     * @return Binder object
     */
    @Override
    public IBinder onBind(Intent intent){
        Bundle extras = intent.getExtras();
        Messenger messenger = (Messenger) extras.get("Handler");
        this.handler = messenger;
        return binder;

    }

    /**
     *Local Binder Class
     */
    public class ChatServiceBinder extends Binder {
        /**
         * Gets the ChatService associated with this Binder Class
         * @return ChatService object
         */
        ChatService getService(){
            return ChatService.this;
        }
    }

}
