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
 * Created by mpauk on 4/1/2018.
 */

public class ChatService extends Service {
    private static final int PORT_NUMBER = 2222;
    private static final String HOST_NAME = "10.26.48.135";
    private Messenger handler;
    public ChatService(Handler handler){
        this.handler=handler;
    }

    @Override
    public void onCreate(){

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message;
                    android.os.Message msg = new android.os.Message();
                    Socket socket = new Socket(HOST_NAME,PORT_NUMBER);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out.println();
                    out.flush();
                    out.close();
                    while(true){
                        if (in.ready()){
                            String text = in.readLine();
                            message= new Message(text);
                            msg.obj = message;
                            handler.send(msg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    @Override
    public IBinder onBind(Intent intent){
        Bundle extras = intent.getExtras();
        Messenger messenger = (Messenger) extras.get("Handler");
        this.handler = messenger;

    }
    public class ChatServiceBinder extends Binder {
        ChatService getService(){
            return ChatService.this;
        }
    }

}
