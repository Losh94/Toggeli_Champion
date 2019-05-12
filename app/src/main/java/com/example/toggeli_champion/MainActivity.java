package com.example.toggeli_champion;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onStart() {
        super.onStart();
        //mAuth.signOut();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.login_panel);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = new Intent(MainActivity.this, ChallengerService.class);
        startService(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }






    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Start) {

        } else if (id == R.id.newGame) {
            Intent startNewGame = new Intent(MainActivity.this, NewGame.class);
            startActivity(startNewGame);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);


        } else if (id == R.id.rangliste) {
            Intent startRangliste = new Intent(MainActivity.this, Rangliste.class);
            startActivity(startRangliste);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);


        } else if (id == R.id.Forum) {
            Intent startNewGame = new Intent(MainActivity.this, Forum.class);
            startActivity(startNewGame);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        } else if(id == R.id.TippsundTricks){
            Intent startNewGame = new Intent(MainActivity.this, TippsundTricks.class);
            startActivity(startNewGame);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        }  else if (id == R.id.Logout){
            mAuth.signOut();
            setContentView(R.layout.login_panel);
        }
        return true;
    }

    public void initializeRanglisteButton(View view) {
       Intent startRangliste = new Intent(MainActivity.this, Rangliste.class);
       startActivity(startRangliste);
    }

    public void initializeNewGameButton(View view) {
        Intent startNewGame = new Intent(MainActivity.this, MatchReports.class);
        startActivity(startNewGame);
    }

    public void initializeForumButton(View view) {
        Intent startNewGame = new Intent(MainActivity.this, Forum.class);
        startActivity(startNewGame);
    }

    public void initializeTippsundTricksButton(View view) {
        Intent startNewGame = new Intent(MainActivity.this, TippsundTricks.class);
        startActivity(startNewGame);
    }

    public void signInSdk(View view) {
        TextView usernameTV = (TextView) findViewById(R.id.username);
        TextView passwordTV = (TextView) findViewById(R.id.password);
        if (usernameTV.getText() != null && passwordTV.getText() != null) {
            String username = usernameTV.getText().toString();
            String password = passwordTV.getText().toString();
            signIn(username, password);
        }
    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        TextView tv = (TextView) findViewById(R.id.textViewSignin);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            setContentView(R.layout.activity_main);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            tv.setText("Failed To Log IN!!!!");
                        }

                        // ...
                    }
                });
    }

    public void registerSdk(View view) {
        setContentView(R.layout.signup);
    }

    public void register(View view) {
        TextView usernameTV = (TextView) findViewById(R.id.su_username);
        TextView passwordTV = (TextView) findViewById(R.id.su_password);
        final String email = usernameTV.getText().toString();
        final String password = passwordTV.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null){
                                createRanglsiteDBEntry(email);
                                signIn(email, password);
                            }
                            Toast.makeText(MainActivity.this, "Erfolgreich registriert!", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            TextView tvError = (TextView) findViewById(R.id.rv_signup);
                            tvError.setText("SignUp failed, try again");
                        }

                        // ...
                    }
                });
    }

    private void createRanglsiteDBEntry(String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Add a new document with a generated id.
        Map<String, Object> data = new HashMap<>();
        data.put("E-Mail", email);
        data.put("Gewonnen", 0);
        data.put("Verloren", 0);

        db.collection("Rangliste")
                .add(data);
    }


}
