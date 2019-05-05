package com.example.toggeli_champion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Forum extends Activity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_forum);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            }
        });
    }

    private void setSupportActionBar(Toolbar toolbar) {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Start) {
            Intent startStart = new Intent(Forum.this, MainActivity.class);
            startActivity(startStart);

        } else if (id == R.id.newGame) {
            Intent startNewGame = new Intent(Forum.this, NewGame.class);
            startActivity(startNewGame);


        } else if (id == R.id.Rangliste) {
            Intent startRangliste = new Intent(Forum.this, Rangliste.class);
            startActivity(startRangliste);


        }else if(id == R.id.TippsundTricks){
            Intent startTippsundTricks = new Intent(Forum.this, TippsundTricks.class);
            startActivity(startTippsundTricks);

        } else if (id == R.id.Logout){
            mAuth.signOut();
            Intent start = new Intent(Forum.this, MainActivity.class);
            startActivity(start);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
