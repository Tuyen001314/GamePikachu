package com.example.gamepikachu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class WinGameDialogFragment extends AppCompatDialogFragment {
    DialogInterface.OnCancelListener onCancelListener;

    public WinGameDialogFragment(DialogInterface.OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog =  new MaterialAlertDialogBuilder(requireContext())
        .setView(R.layout.dialog_win)
                .create();
        dialog.setOnCancelListener(onCancelListener);
        return dialog;
    }
}
