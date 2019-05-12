package com.example.toggeli_champion;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

public class TippsundTricks extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_tippsundtricks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    private void setSupportActionBar(Toolbar toolbar) {

    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Start) {

            Intent start = new Intent(TippsundTricks.this, MainActivity.class);
            startActivity(start);

        } else if (id == R.id.newGame) {

            Intent startNewGame = new Intent(TippsundTricks.this, TippsundTricks.class);
            startActivity(startNewGame);


        } else if (id == R.id.rangliste) {
            Intent startRangliste = new Intent(TippsundTricks.this, Rangliste.class);
            startActivity(startRangliste);


        } else if (id == R.id.Forum) {
            Intent startNewGame = new Intent(TippsundTricks.this, Forum.class);
            startActivity(startNewGame);

        } else if (id == R.id.Logout){
            mAuth.signOut();
            Intent start = new Intent(TippsundTricks.this, MainActivity.class);
            startActivity(start);
        } else if (id == R.id.spielBerichte){
            Intent startSpielBerichte = new Intent(TippsundTricks.this, MatchReports.class);
            startActivity(startSpielBerichte);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void sendHttp(View view) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://pastebin.com/raw/XpksFvdd";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        setTextView(response.substring(0));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setTextView("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void setTextView(String text) {
        LinearLayout linearLayout = new LinearLayout(TippsundTricks.this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView textView = new TextView(TippsundTricks.this);
        textView.setText(text);
        linearLayout.addView(textView);
    }

}
