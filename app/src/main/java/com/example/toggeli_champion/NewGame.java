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
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NewGame extends Activity implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseFirestore db;
    FirebaseAuth mauth;
    private static final String TAG = "NewGame";
    private String dataFile;
    private String ziel;
    private String challenger;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mauth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_newgame);
        setPlayerOne();
        createEmptyDataBaseEntry();
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    private void setPlayerOne() {
        EditText t1p1 = (EditText) findViewById(R.id.teamoneplayeronename);
        if (ziel != null) {
            t1p1.setText(ziel);
        } else if (mauth.getCurrentUser() != null){
            t1p1.setText(mauth.getCurrentUser().getEmail());
        }
        EditText t2p1 = (EditText) findViewById(R.id.teamtwoplayeronename);
        if (challenger != null) {
            t2p1.setText(challenger);
        }
    }

    private void createEmptyDataBaseEntry() {
        Map<String, Object> challenge = new HashMap<>();
        challenge.put("Players", 2);
        challenge.put("t1p1", ziel);
        challenge.put("t1p2", null);
        challenge.put("t2p1", challenger);
        challenge.put("t2p2", null);
        challenge.put("t1score", null);
        challenge.put("t2score", null);
        challenge.put("Timestamp", Timestamp.now());
        challenge.put("Bestaetigt", false);

        db.collection("Spielberichte")
                .add(challenge)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        dataFile = documentReference.getId();
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Toast.makeText(NewGame.this, "Bericht erstellt!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NewGame.this, "Bericht konnte nicht erstellt werden!", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    private void setSupportActionBar(Toolbar toolbar) {

    }

    public void onClickSave(View view) {
        Map<String, Object> challenge = new HashMap<>();
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioPlayer);
        int selectedValue = radioGroup.getCheckedRadioButtonId();
        EditText t1p1 = (EditText) findViewById(R.id.teamoneplayeronename);
        challenge.put("t1p1", t1p1.getText().toString());
        EditText t2p1 = (EditText) findViewById(R.id.teamtwoplayeronename);
        challenge.put("t2p1", t2p1.getText().toString());
        if (selectedValue == 2131230915) {
            challenge.put("Players", 2);
        } else {
            challenge.put("Players", 4);
            EditText t1p2 = (EditText) findViewById(R.id.teamoneplayertwoname);
            challenge.put("t1p2", t1p2.getText().toString());
            EditText t2p2 = (EditText) findViewById(R.id.teamtwoplayertwoname);
            challenge.put("t2p2", t2p2.getText().toString());
        }
        EditText t1result = (EditText) findViewById(R.id.resultone);
        challenge.put("t1score", t1result.getText().toString());
        EditText t2result = (EditText) findViewById(R.id.resulttwo);
        challenge.put("t2score", t2result.getText().toString());
        challenge.put("Timestamp", Timestamp.now());
        challenge.put("Bestaetigtt1", true);
        challenge.put("Bestaetigtt2", false);

        db.collection("Spielberichte").document(dataFile)
                .set(challenge)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Toast.makeText(NewGame.this, "Bericht erstellt!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NewGame.this, "Bericht konnte nicht erstellt werden!", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        finish();
    }

    private void createDataBaseEntry() {
    }

    public void disablePlayerTwo(View view) {
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

    public void closeActivity(View view) {
        Log.d(TAG, "Close Activity");
        finish();
    }







    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Start) {
            Intent startStart = new Intent(NewGame.this, MainActivity.class);
            startActivity(startStart);

        } else if (id == R.id.newGame) {
            Intent startNewGame = new Intent(NewGame.this, MatchReports.class);
            startActivity(startNewGame);


        } else if (id == R.id.Rangliste) {
            Intent startRangliste = new Intent(NewGame.this, Rangliste.class);
            startActivity(startRangliste);


        }else if(id == R.id.TippsundTricks){
            Intent startTippsundTricks = new Intent(NewGame.this, TippsundTricks.class);
            startActivity(startTippsundTricks);

        } else if (id == R.id.Logout){
            mauth.signOut();
            Intent start = new Intent(NewGame.this, MainActivity.class);
            startActivity(start);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
