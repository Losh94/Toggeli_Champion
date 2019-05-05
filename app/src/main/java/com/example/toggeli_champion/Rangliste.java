package com.example.toggeli_champion;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rangliste extends Activity {
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    private static final String TAG = "Rangliste";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        getData();
        setContentView(R.layout.activity_rangliste);
    }

    private void getData() {
        db.collection("Rangliste")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;
                            String[] hilfArray = new String[10];
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                int a = 0;
                                for (String key : document.getData().keySet()) {
                                    hilfArray[a] = document.getData().get(key).toString();
                                    a++;
                                }
                                createTableRow(hilfArray, i);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                i++;

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });
    }

    private void createTableRow(String[] hilfArray, int id) {
        TableLayout table = (TableLayout)findViewById(R.id.tableLayout);
        TableRow row = new TableRow(Rangliste.this);
        //TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        //row.setLayoutParams(layoutParams);
        row.setGravity(Gravity.CENTER);

        TextView tv1 = new TextView(this);
        //tv1.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT));
        tv1.setPadding(2, 2, 2, 2);
        tv1.setGravity(Gravity.CENTER);
        tv1.setText(hilfArray[0]);

        TextView tv2 = new TextView(this);
        //tv2.setLayoutParams(new TableLayout.LayoutParams(0 , LayoutParams.WRAP_CONTENT, 2));
        tv2.setPadding(2, 2, 2, 2);
        tv2.setGravity(Gravity.CENTER);
        tv2.setText(hilfArray[1]);

        TextView tv3 = new TextView(this);
        tv3.setLayoutParams(new TableLayout.LayoutParams(0 , LayoutParams.WRAP_CONTENT, 2));
        tv3.setPadding(2, 2, 2, 2);
        tv3.setGravity(Gravity.CENTER);
        tv3.setText(hilfArray[2]);

        Button button = new Button(this);
        //button.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT, 5));
        //button.setPadding(2, 2, 2, 2);
        button.setGravity(Gravity.CENTER);
        button.setId(id);
        button.setText("Challenge");
        button.setHint(hilfArray[0]);

        row.addView(tv1);
        row.addView(tv2);
        row.addView(tv3);
        row.addView(button);
        table.addView(row);
        Button btn = (Button) findViewById(id);
        btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                sendChallenge(mAuth.getCurrentUser().getEmail().toString(), btn.getHint().toString());
                btn.setEnabled(false);
            }
        });
    }

    private void sendChallenge(String challenger, String ziel) {

        ChallengeAsyncTask runner = new ChallengeAsyncTask();
        runner.execute(challenger + ":" + ziel);
    }


    private void setSupportActionBar(Toolbar toolbar) {

    }

    //AsyncTask for sending Challenge
    private class ChallengeAsyncTask extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... dataPairs) {
            for (String data : dataPairs) {
                Map<String, Object> challenge = new HashMap<>();
                String[] dataSplit = data.split(":");
                challenge.put("Herausforderer", dataSplit[0]);
                challenge.put("Ziel", dataSplit[1]);
                challenge.put("Timestamp", Timestamp.now());

                db.collection("Herausforderungen").document()
                        .set(challenge)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                Toast.makeText(Rangliste.this, "Challenge Sent!!!!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Rangliste.this, "Challenge failed!!!!", Toast.LENGTH_SHORT).show();
                                Log.w(TAG, "Error writing document", e);
                            }
                        });

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //Toast.makeText(Rangliste.this, "Async Task finished!!!!", Toast.LENGTH_SHORT).show();
        }
    }

}
