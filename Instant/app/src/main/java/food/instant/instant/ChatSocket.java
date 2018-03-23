package food.instant.instant;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by mpauk on 3/20/2018.
 */
public class ChatSocket extends Service{
    private static final int PORT_NUMBER = 8884;
    private static final String HOST_NAME = "localhost";
    private Socket socket;

    private BufferedReader in;
    private PrintWriter out;
    @Override
    public void onCreate(){

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        try {
            socket = new Socket(HOST_NAME,PORT_NUMBER);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
