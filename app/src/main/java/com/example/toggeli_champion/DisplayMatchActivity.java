package com.example.toggeli_champion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayMatchActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    private static final String TAG = "DisplayMatch";
    private String dataFile;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_displaygame);
        Bundle extras = getIntent().getExtras();
        dataFile = extras.getString("documentName");
        getData(dataFile);
        setButtonsVisible(extras.getBoolean("ButtonVisibility"));
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    private void setButtonsVisible(boolean buttonVisibility) {
        Button btSave = findViewById(R.id.sumbit_displaygame);
        if (buttonVisibility) {
            btSave.setVisibility(View.VISIBLE);
        } else {
            btSave.setVisibility(View.GONE);
        }

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
            disablePlayerTwo();
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Start) {

            Intent start = new Intent(DisplayMatchActivity.this, MainActivity.class);
            startActivity(start);
        } else if (id == R.id.newGame) {

            Intent startNewGame = new Intent(DisplayMatchActivity.this, MatchReports.class);
            startActivity(startNewGame);


        } else if (id == R.id.Rangliste) {
            Intent startRangliste = new Intent(DisplayMatchActivity.this, Rangliste.class);
            startActivity(startRangliste);


        } else if (id == R.id.Forum) {
            Intent startNewGame = new Intent(DisplayMatchActivity.this, Forum.class);
            startActivity(startNewGame);

        } else if(id == R.id.TippsundTricks){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void closeActivity(View view) {
        finish();
    }

    public void onClickSave(View view) {
        Map<String, Object> challenge = new HashMap<>();
        String[] t1Players = new String[2];
        String[] t2Players = new String[2];
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioPlayer);
        int selectedValue = radioGroup.getCheckedRadioButtonId();
        EditText t1p1 = (EditText) findViewById(R.id.teamoneplayeronename);
        t1Players[0] = t1p1.getText().toString();
        EditText t2p1 = (EditText) findViewById(R.id.teamtwoplayeronename);
        t2Players[0] = t2p1.getText().toString();
        if (selectedValue == 2131230912) {
        } else {
            EditText t1p2 = (EditText) findViewById(R.id.teamoneplayertwoname);
            t1Players[1] = t1p2.getText().toString();
            EditText t2p2 = (EditText) findViewById(R.id.teamtwoplayertwoname);
            t2Players[1] = t2p2.getText().toString();
        }
        EditText t1result = (EditText) findViewById(R.id.resultone);
        boolean t1Won;
        String result = t1result.getText().toString();
        if (result.equals("10")) {
            increment(t2Players, "Verloren");
            increment(t1Players, "Gewonnen");
        } else {
            increment(t1Players, "Verloren");
            increment(t2Players, "Gewonnen");
        }
        challenge.put("Bestaetigtt1", true);
        challenge.put("Bestaetigtt2", true);

        db.collection("Spielberichte").document(dataFile)
                .update(challenge)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Toast.makeText(DisplayMatchActivity.this, "Bericht bestätigt!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DisplayMatchActivity.this, "Bericht konnte nicht erstellt werden!", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        finish();
    }

    private void increment(String[] players, String field) {
        for (String player : players) {
            //Log.d(TAG, mAuth.getCurrentUser().getEmail());
            getFireStoreid(player, field);
        }
    }

    private void getFireStoreid(String player, String field) {
        db.collection("Rangliste")
                .whereEqualTo("E-Mail", player)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()) {
                            Log.d(TAG, "Query Successful");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                incrementFirestoreField(field, document.getId());
                            }
                        }
                    }
                });
    }

    private void incrementFirestoreField(String field, String documentId) {
        db.collection("Rangliste").document(documentId)
                .update(field, FieldValue.increment(1));
    }

    public void disablePlayerTwo(View view) {
        TableRow tr1 = (TableRow)findViewById(R.id.t1_p2);
        TableRow tr2 = (TableRow)findViewById(R.id.t2_p2);
        tr1.setVisibility(View.GONE);
        tr2.setVisibility(View.GONE);
    }

    public void disablePlayerTwo() {
        TableRow tr1 = (TableRow)findViewById(R.id.t1_p2);
        TableRow tr2 = (TableRow)findViewById(R.id.t2_p2);
        tr1.setVisibility(View.GONE);
        tr2.setVisibility(View.GONE);
    }

    public void enablePlayerTwo(View view) {
        TableRow tr1 = (TableRow)findViewById(R.id.t1_p2);
        TableRow tr2 = (TableRow)findViewById(R.id.t2_p2);
        tr1.setVisibility(View.VISIBLE);
        tr2.setVisibility(View.VISIBLE);
    }
}
