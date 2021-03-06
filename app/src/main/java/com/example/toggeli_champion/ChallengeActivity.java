package com.example.toggeli_champion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class ChallengeActivity extends FragmentActivity implements ChallengeDialogFragment.NoticeDialogListener{

    private static final String TAG = "ChallengeActivity";
    String challenger;
    String ziel;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create an instance of the dialog fragment and show it
        ChallengeDialogFragment fragment = new ChallengeDialogFragment();
        Bundle bundle = new Bundle();
        challenger = getIntent().getExtras().getString("challenger");
        ziel = getIntent().getExtras().getString("ziel");
        bundle.putString("challenger", challenger);
        bundle.putString("documentid", getIntent().getExtras().getString("documentid"));
        fragment.setArguments(bundle);
        fragment.show(this.getSupportFragmentManager(), "ChallengeDialog");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        finish();
        Intent start = new Intent(ChallengeActivity.this, NewGame.class);
        start.putExtra("ziel", ziel);
        start.putExtra("challenger", challenger);
        startActivity(start);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        finish();
    }
}
