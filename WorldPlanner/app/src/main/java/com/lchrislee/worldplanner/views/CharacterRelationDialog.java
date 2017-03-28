package com.lchrislee.worldplanner.views;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.models.Character;
import com.lchrislee.worldplanner.models.Relationship;

/**
 * Created by chrisl on 3/28/17.
 */

public class CharacterRelationDialog extends DialogFragment {

    public interface CharacterRelationDialogListener {
        void onDialogPositiveClick(Relationship relationship);
        void onDialogPositiveClick(Relationship relationship, int index);
    }

    private static final String RELATIONSHIP = "CharacterRelationDialog_RELATIONSHIP";
    private static final String INDEX = "CharacterRelationDialog_INDEX";

    CharacterRelationDialogListener listener;
    private Relationship existingRelationship;

    private int index;

    public static CharacterRelationDialog newInstance(@Nullable Relationship r)
    {
        return newInstance(r, -1);
    }

    public static CharacterRelationDialog newInstance(@Nullable Relationship r, int indexFrom)
    {
        CharacterRelationDialog dialog = new CharacterRelationDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(RELATIONSHIP, r);
        bundle.putSerializable(INDEX, indexFrom);
        dialog.setArguments(bundle);
        return dialog;
    }

    public void setCharacterRelationDialogListener(CharacterRelationDialogListener l)
    {
        listener = l;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        existingRelationship = (Relationship) arguments.getSerializable(RELATIONSHIP);
        index = arguments.getInt(INDEX);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add a relation");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View innerView = inflater.inflate(R.layout.layout_character_relation_dialog, null);



        builder.setView(innerView);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null)
                {
                    // TODO: update with more information.
                    if (existingRelationship == null) {
                        Relationship r = new Relationship("REPLACE_ME", new Character("2", ""), new Character("1", ""));
                        listener.onDialogPositiveClick(r);
                    }
                    else
                    {
                        listener.onDialogPositiveClick(existingRelationship, index);
                    }
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }
}
