package com.example.toggeli_champion;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
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
        final ArrayMap<Integer, String[]> tempMap = new ArrayMap<>();
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
                                createTableRow(hilfArray);
                                tempMap.put(i, hilfArray);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                i++;

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });
    }

    private void createTableRow(String[] hilfArray) {
        TableLayout table = (TableLayout)findViewById(R.id.tableLayout);
        TableRow row = new TableRow(this);
        TextView tv1 = new TextView(this);
        tv1.setText(hilfArray[0]);
        TextView tv2 = new TextView(this);
        tv2.setText(hilfArray[1]);
        TextView tv3 = new TextView(this);
        tv3.setText(hilfArray[3]);
        row.addView(tv1);
        row.addView(tv2);
        row.addView(tv3);
        table.addView(row);
    }


    private void setSupportActionBar(Toolbar toolbar) {

    }

    public void sendHerausf(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> docData = new HashMap<>();
        docData.put("Herausforderer", mAuth.getCurrentUser().getEmail().toString());
        docData.put("Ziel", "pablo.suess@hotmail.com");
        db.collection("Herausforderungen").add(docData);
    }

}
