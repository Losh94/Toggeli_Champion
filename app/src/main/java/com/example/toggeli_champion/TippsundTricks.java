package com.example.toggeli_champion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class TippsundTricks extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        } else if (id == R.id.Rangliste) {
            Intent startRangliste = new Intent(TippsundTricks.this, Rangliste.class);
            startActivity(startRangliste);


        } else if (id == R.id.Forum) {
            Intent startNewGame = new Intent(TippsundTricks.this, Forum.class);
            startActivity(startNewGame);

        } else if(id == R.id.TippsundTricks){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
