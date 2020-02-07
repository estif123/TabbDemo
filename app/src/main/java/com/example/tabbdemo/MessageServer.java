package com.example.tabbdemo;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;

import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class MessageServer extends NanoHTTPD {
    private Context context;


    public MessageServer(int port, Context context) {
        super(port);
        this.context = context;
    }
    ServerFragment serverFragment = new ServerFragment();

    @Override
    public Response serve(IHTTPSession session) {
        String msg = "<html><body><h1>Hello server</h1>\n";
        final Map<String, String> parms = session.getParms();
        if (parms.get("title") == null) {
            msg += "<form action='?' method='get'>\n  <p>Message Title: <input type='text' name='content'></p>\n"+"<p>Message Content: <input type='text' name='content'></p>" + "</form>\n";
        } else {
            msg += "<p>Message Title:, " + parms.get("title") + "<p>Message content: " + parms.get("content") +"!</p>";
        }
        Handler handler = new Handler(Looper.getMainLooper());

        handler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {

                serverFragment.SendSMS(parms.get("title"),parms.get("content"),context);

            }
        }, 1000 );

        return newFixedLengthResponse( msg + "</body></html>\n" );
    }
}


