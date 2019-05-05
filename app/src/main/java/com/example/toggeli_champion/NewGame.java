package com.example.toggeli_champion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class NewGame extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_newgame);
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

            Intent start = new Intent(NewGame.this, MainActivity.class);
            startActivity(start);

        }  else if (id == R.id.Rangliste) {
            Intent startRangliste = new Intent(NewGame.this, Rangliste.class);
            startActivity(startRangliste);


        } else if (id == R.id.Forum) {
            Intent startForum = new Intent(NewGame.this, Forum.class);
            startActivity(startForum);

        } else if(id == R.id.TippsundTricks){
            Intent startTippsundTricks = new Intent(NewGame.this, TippsundTricks.class);
            startActivity(startTippsundTricks);

        } else if (id == R.id.Logout){
            mAuth.signOut();
            Intent start = new Intent(NewGame.this, MainActivity.class);
            startActivity(start);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
