package com.lchrislee.worldplanner.fragments.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryItem;

import java.io.Serializable;

import timber.log.Timber;

public class ItemEffectDialogFragment extends DialogFragment {

    public interface ItemEffectDialogListener
    {
        void onUpdate();
    }

    private static final String INDEX_EFFECT = "ItemEffectDialogFragment.INDEX_EFFECT";
    private static final String ITEM = "ItemEffectDialogFragment.ITEM";

    private StoryItem.StoryItemEffect effect;
    private StoryItem master;

    private EditText name;
    private EditText description;

    private ItemEffectDialogListener listener;

    public ItemEffectDialogFragment() {
        // Required
    }

    public static @NonNull ItemEffectDialogFragment newInstance(@NonNull Serializable item,
                                                                int effectIndex)
    {
        ItemEffectDialogFragment fragment = new ItemEffectDialogFragment();
        Bundle argument = new Bundle();
        argument.putInt(INDEX_EFFECT, effectIndex);
        argument.putSerializable(ITEM, item);
        fragment.setArguments(argument);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        Serializable obj = arguments.getSerializable(ITEM);
        int effectIndex = arguments.getInt(INDEX_EFFECT, -1);


        master = (StoryItem) obj;
        if (master != null)
        {
            effect = master.getEffectAtIndex(effectIndex);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(effect == null ? "Create Item Effect" : "Edit Item Effect");
        builder.setView(R.layout.dialog_effect);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_effect, null);
        name = (EditText) v.findViewById(R.id.dialog_effect_name);
        description = (EditText) v.findViewById(R.id.dialog_effect_description);

        if (effect != null)
        {
            name.setText(effect.getName());
            description.setText(effect.getDescription());
        }

        builder.setView(v);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (effect == null)
                {
                    effect = new StoryItem.StoryItemEffect();
                    effect.setName(name.getText().toString());
                    effect.setDescription(description.getText().toString());
                    effect.setMasterItem(master);
                    master.addEffect(effect);
                    DataManager.getInstance().add(effect);
                    DataManager.getInstance().update(master);
                }
                else
                {
                    DataManager.getInstance().update(effect);
                }
                listener.onUpdate();
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ItemEffectDialogFragment.this.getDialog().cancel();
            }
        });
        if (effect != null)
        {
            builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DataManager.getInstance().delete(effect);
                    listener.onUpdate();
                }
            });
        }

        return builder.create();
    }

    public void setListener(@NonNull ItemEffectDialogListener l)
    {
        listener = l;
    }
}
