package com.example.toggeli_champion;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChallengeDialogFragment extends DialogFragment {

    private static final String TAG = "ChallengeFragment";
    NoticeDialogListener listener;

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.getString("challenger") != "9999") {
            builder.setMessage("Herausforderung von : " + bundle.getString("challenger"))
                    .setPositiveButton(R.string.accept_challenge, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String documentId = bundle.getString("documentid");
                            listener.onDialogPositiveClick(ChallengeDialogFragment.this);
                            deleteDataBaseEntry(documentId);
                            // Create Bericht
                        }
                    })
                    .setNegativeButton(R.string.decline_challenge, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String documentId = bundle.getString("documentid");
                            listener.onDialogNegativeClick(ChallengeDialogFragment.this);
                            deleteDataBaseEntry(documentId);
                        }
                    });
        }

        // Create the AlertDialog object and return it
        return builder.create();
    }



    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(" must implement NoticeDialogListener");
        }
    }

    private void deleteDataBaseEntry(String documentId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Herausforderungen").document(documentId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }
}
