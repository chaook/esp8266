package com.chao.control;

import androidx.appcompat.app.AppCompatActivity;

import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.service.autofill.OnClickAction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    EditText clientIp;
    Button connect, open, close;
    SocketHandler socketHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        socketHandler = new SocketHandler();
        clientIp = findViewById(R.id.ip);
        connect = findViewById(R.id.connect);
        open = findViewById(R.id.open);
        close = findViewById(R.id.close);
        connect.setOnClickListener(socketHandler);
        open.setOnClickListener(socketHandler);
        connect.setOnClickListener(socketHandler);

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}

class SocketHandler extends Thread implements View.OnClickListener {
    private final static int CONNECT_PORT = 8888;
    private final static String CONNECT_IP = "45.63.71.177";
    private PrintWriter printWriter = null;
    private Socket socket;

    private void connect(){
        try {
            socket = new Socket(CONNECT_IP, CONNECT_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void run(){
        String key = "";
        InputStreamReader inputStreamReader = null;
        String line = "";
        char[] buff = new char[256];
        int len;

        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            printWriter.println(99999999);
            while((len = inputStreamReader.read(buff))!=-1) {
                if (!socket.isConnected()){
                    try {
                        if (printWriter!=null)
                            printWriter.close();
                        if (socket!=null)
                            socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }

                line = new String(buff, 0, len);


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.connect:
                connect();
                break;
            case R.id.open:
                if (printWriter!=null)
                    printWriter.println(5);
                break;
            case R.id.close:
                if (printWriter!=null)
                    printWriter.println(6);
                break;
        }
    }
}



