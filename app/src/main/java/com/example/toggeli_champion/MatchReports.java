package com.example.toggeli_champion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MatchReports extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MatchReport";
    FirebaseAuth mAuth;
    int id = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_matchreports);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        fillContent("t1p1");
        fillContent("t1p2");
        fillContent("t2p1");
        fillContent("t2p2");
    }

    private void fillContent(String field) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Loop through Firestore and Look for Matches
            if (mAuth.getCurrentUser() != null) {
                //Log.d(TAG, mAuth.getCurrentUser().getEmail());
                db.collection("Spielberichte")
                        .whereEqualTo(field, mAuth.getCurrentUser().getEmail())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                //Log.d(TAG, "Query Complete");
                                if(task.isSuccessful()) {
                                    Log.d(TAG, "Query Successful");
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        createButton(id, document);
                                        id++;
                                    }
                                }
                            }
                        });
            }
    }

    private void createButton(int id, QueryDocumentSnapshot document) {
        // Find the ScrollView
        ScrollView scrollView = (ScrollView) findViewById(R.id.match_report_scrollview);

        // Create a LinearLayout element
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.match_report_linearview);
        //linearLayout.setLayoutParams(new ViewGroup.LayoutParams(
                //ViewGroup.LayoutParams.MATCH_PARENT,
                //ViewGroup.LayoutParams.WRAP_CONTENT));
        //linearLayout.removeAllViews();

        // Add Buttons
        final RelativeLayout.LayoutParams buttonLayout = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        Button button = new Button(this);
        button.setId(id);
        button.setText(document.getString("t1p1") + "\n" + document.getString("t2p1"));
        button.setLayoutParams(buttonLayout);
        linearLayout.addView(button);

        // Add the LinearLayout element to the ScrollView
        scrollView.removeAllViews();
        scrollView.addView(linearLayout);

        Button btn = (Button) findViewById(id);
        btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MatchReports.this, DisplayMatchActivity.class);
                intent.putExtra("documentName", document.getId());
                if (document.getBoolean("Bestaetigtt1") != null && document.getBoolean("Bestaetigtt2") != null) {
                    if ((document.getBoolean("Bestaetigtt1") && document.getBoolean("Bestaetigtt2")) || document.getString("t1p1").equals(mAuth.getCurrentUser().getEmail())) {
                        intent.putExtra("ButtonVisibility", false);
                    } else {
                        intent.putExtra("ButtonVisibility", true);
                    }
                }
                startActivity(intent);
            }
        });
    }

    private void setSupportActionBar(Toolbar toolbar) {

    }

    public void initializeNewGameButton(View view) {
        Intent startNewGame = new Intent(MatchReports.this, NewGame.class);
        startActivity(startNewGame);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Start) {
            Intent startStart = new Intent(MatchReports.this, MainActivity.class);
            startActivity(startStart);

        } else if (id == R.id.newGame) {
            Intent startNewGame = new Intent(MatchReports.this, MatchReports.class);
            startActivity(startNewGame);


        } else if (id == R.id.Rangliste) {
            Intent startRangliste = new Intent(MatchReports.this, Rangliste.class);
            startActivity(startRangliste);


        }else if(id == R.id.TippsundTricks){
            Intent startTippsundTricks = new Intent(MatchReports.this, TippsundTricks.class);
            startActivity(startTippsundTricks);

        } else if (id == R.id.Logout){
            mAuth.signOut();
            Intent start = new Intent(MatchReports.this, MainActivity.class);
            startActivity(start);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
