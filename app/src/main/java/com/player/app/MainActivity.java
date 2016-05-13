package com.player.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> demoLinks;
    private ArrayList<String> demoTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView listview = (ListView)findViewById(R.id.listView);

        demoLinks = new ArrayList<String>();
        demoTitle = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, demoTitle);

        listview.setAdapter(adapter);


        demoLinks.add("http://demo.unified-streaming.com/video/smurfs/smurfs.ism/smurfs.mpd");
        demoLinks.add("http://dash.edgesuite.net/envivio/dashpr/clear/Manifest.mpd");
        demoTitle.add("Dash Video 1");
        demoTitle.add("Dash Video 2");

        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 onClick(demoLinks.get(position));
               }
            });
    }

    
    private void onClick(String uri) {
        Intent intent = new Intent(this, Player.class).setData(Uri.parse(uri));
        startActivity(intent);
    }
    
}
