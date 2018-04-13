package food.instant.instant;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
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
 * Asynchronous task that creates a socket connection with chat server
 * to send a message.
 * Created by mpauk on 3/20/2018.
 */
public class ChatSocket extends AsyncTask<Message,Void,Void>{
    private static final int PORT_NUMBER = 2222;
    private static final String HOST_NAME = "proj-309-sd-4.cs.iastate.edu";
    private PrintWriter out;
    @SuppressLint("NewApi")
    @Override
    /**
     *Async Method that sends a message to the socket server. This method is called when a new ChatSocket is
     * created using: new ChatSocket().execute(String s)
     */
    protected Void doInBackground(Message... messages) {
        try {
            Socket socket = new Socket(HOST_NAME,PORT_NUMBER);
            System.out.println("connected");
            out = new PrintWriter(socket.getOutputStream(),true);
            out.println("$$$"+messages[0].getFormattedMessage());
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
