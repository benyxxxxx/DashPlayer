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

    private ArrayAdapter<String> m_adapter;
    private ArrayList<String> m_demoLinks;
    private ArrayList<String> m_demoTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listview = (ListView)findViewById(R.id.listView);

        m_demoLinks = new ArrayList<String>();
        m_demoTitle = new ArrayList<String>();

        m_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, m_demoTitle);

        listview.setAdapter(m_adapter);


        m_demoLinks.add("http://demo.unified-streaming.com/video/smurfs/smurfs.ism/smurfs.mpd");
        m_demoLinks.add("http://dash.edgesuite.net/envivio/dashpr/clear/Manifest.mpd");
        m_demoTitle.add("Dash Video 1");
        m_demoTitle.add("Dash Video 2");

        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 onClick(m_demoLinks.get(position));
               }
            });
    }

    
    private void onClick(String uri) {
        Intent intent = new Intent(this, Player.class).setData(Uri.parse(uri));
        startActivity(intent);
    }
    
}
