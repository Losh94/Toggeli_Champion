package com.example.toggeli_champion;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class ChallengerService extends Service {

    private FirebaseAuth mAuth;
    private static final String TAG = "ForegroundService";
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        mAuth = FirebaseAuth.getInstance();
        Log.d(TAG, "My foreground service onCreate().");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null)
        {
            Toast.makeText(this, "Service running", Toast.LENGTH_SHORT).show();
            startForegroundService();
        }
        return Service.START_STICKY;
    }

    private void startForegroundService() {
        Log.d(TAG, "Start foreground service.");
        new Thread(() -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            // Loop through Firestore and Look for challenges
            while(true)
            {
                if (mAuth.getCurrentUser() != null) {
                    //Log.d(TAG, mAuth.getCurrentUser().getEmail());
                    db.collection("Herausforderungen")
                            .whereEqualTo("Ziel", mAuth.getCurrentUser().getEmail())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    //Log.d(TAG, "Query Complete");
                                    if(task.isSuccessful()) {
                                        Log.d(TAG, "Query Successful");
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if (document.getString("Herausforderer") != null) {
                                                Intent dialogIntent = new Intent(ChallengerService.this, ChallengeActivity.class);
                                                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                //Log.d(TAG, document.getString("Herausforderer"));
                                                dialogIntent.putExtra("challenger", document.getString("Herausforderer"));
                                                dialogIntent.putExtra("ziel", mAuth.getCurrentUser().getEmail());
                                                dialogIntent.putExtra("documentid", document.getId());
                                                startActivity(dialogIntent);
                                            }
                                        }
                                    }
                                }
                            });
                }

                try {
                    Log.d(TAG, "Looping!!!");
                    Thread.sleep(60000);
                } catch (InterruptedException ex) {
                    Log.e(TAG, ex.getMessage());
                }
            }

        }).start();
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}