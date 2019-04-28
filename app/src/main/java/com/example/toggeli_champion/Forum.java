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

public class Forum extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        } else if (id == R.id.newGame) {
            Intent startNewGame = new Intent(Forum.this, NewGame.class);
            startActivity(startNewGame);


        } else if (id == R.id.Rangliste) {
            Intent startRangliste = new Intent(Forum.this, Rangliste.class);
            startActivity(startRangliste);


        } else if (id == R.id.Forum) {
            Intent startNewGame = new Intent(Forum.this, Forum.class);
            startActivity(startNewGame);

        } else if(id == R.id.TippsundTricks){
            Intent startNewGame = new Intent(Forum.this, TippsundTricks.class);
            startActivity(startNewGame);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
