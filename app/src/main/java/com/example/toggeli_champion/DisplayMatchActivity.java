package com.example.toggeli_champion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DisplayMatchActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    private static final String TAG = "NewGame";
    private String dataFile;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_newgame);
        Bundle extras = getIntent().getExtras();
        getData(extras.getString("documentName"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    private void getData(String documentName) {
        db.collection("Spielberichte").document(documentName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        fillMatchData(document);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void fillMatchData(DocumentSnapshot document) {
        RadioButton radio1 = (RadioButton) findViewById(R.id.radiotwo);
        EditText t1p1 = (EditText) findViewById(R.id.teamoneplayeronename);
        t1p1.setText(document.getString("t1p1"));
        EditText t2p1 = (EditText) findViewById(R.id.teamtwoplayeronename);
        t2p1.setText(document.getString("t2p1"));
        if (document.getLong("Players").intValue() == 2) {
            RadioButton radioTwo = (RadioButton) findViewById(R.id.radiotwo);
            radioTwo.setChecked(true);
        } else {
            RadioButton radioFour = (RadioButton) findViewById(R.id.radiofour);
            radioFour.setChecked(true);
            EditText t1p2 = (EditText) findViewById(R.id.teamoneplayertwoname);
            t1p2.setText(document.getString("t1p2"));
            EditText t2p2 = (EditText) findViewById(R.id.teamtwoplayertwoname);
            t2p2.setText(document.getString("t2p2"));
        }
        EditText t1result = (EditText) findViewById(R.id.resultone);
        t1result.setText(document.getString("t1score"));
        EditText t2result = (EditText) findViewById(R.id.resulttwo);
        t2result.setText(document.getString("t2score"));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
