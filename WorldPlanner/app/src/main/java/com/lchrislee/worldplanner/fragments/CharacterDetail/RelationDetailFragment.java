package com.lchrislee.worldplanner.fragments.CharacterDetail;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.adapters.RelationCharacterListAdapter;
import com.lchrislee.worldplanner.fragments.ToolbarSupportingFragment;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.models.Relationship;
import com.lchrislee.worldplanner.models.WorldPlannerBaseModel;

/**
 * Created by chrisl on 3/28/17.
 */

public class RelationDetailFragment extends WorldPlannerBaseFragment implements RelationCharacterListAdapter.DefaultPlannerObjectSelected, ToolbarSupportingFragment{
    private static final String RELATIONSHIP = "RELATIONDETAILFRAGMENT_RELATIONSHIP";
    private static final String INDEX = "RELATIONDETAILFRAGMENT_INDEX";

    private View mainView;
    private CardView card;
    private RecyclerView list;
    private EditText description;
    private ImageView image;
    private TextView name;
    private Button swap;

    private Relationship existingRelationship;
    private RelationCharacterListAdapter adapter;

    private boolean isEditing;
    private int index;

    public static @NonNull
    RelationDetailFragment newInstance(@Nullable Relationship r)
    {
        RelationDetailFragment dialog = new RelationDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(RELATIONSHIP, r);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        existingRelationship = (Relationship) arguments.getSerializable(RELATIONSHIP);
        index = arguments.getInt(INDEX);
        isEditing = existingRelationship == null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_detail_relationship, container, false);

        card = (CardView) mainView.findViewById(R.id.list_default_card);
        description = (EditText) mainView.findViewById(R.id.fragment_relation_description);

        adapter = new RelationCharacterListAdapter(getContext(), this);
        list = (RecyclerView) mainView.findViewById(R.id.fragment_relation_character_list);
        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        list.setAdapter(adapter);

        image = (ImageView) mainView.findViewById(R.id.list_entity_image);
        name = (TextView) mainView.findViewById(R.id.list_entity_name);
        swap = (Button) mainView.findViewById(R.id.list_default_change);
        swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.setVisibility(View.VISIBLE);
                card.setVisibility(View.INVISIBLE);
                swap.setVisibility(View.INVISIBLE);
            }
        });

        swapEdit();

        return mainView;
    }

    @Override
    public void onItemSelected(int position) {
        list.setVisibility(View.INVISIBLE);
        card.setVisibility(View.VISIBLE);
        swap.setVisibility(View.VISIBLE);
    }

    private void swapEdit()
    {
        if (isEditing)
        {
            Drawable editBackground = ContextCompat.getDrawable(getContext(), android.R.drawable.edit_text);
            description.setBackground(editBackground);
            swap.setVisibility(View.VISIBLE);
        }
        else
        {
            int transparent = ContextCompat.getColor(getContext(), android.R.color.transparent);
            ColorDrawable background = new ColorDrawable(transparent);
            description.setBackground(background);
            swap.setVisibility(View.INVISIBLE);
        }

        description.setFocusable(isEditing);
        description.setClickable(isEditing);
        description.setFocusableInTouchMode(isEditing);
        description.setLongClickable(isEditing);

        mainView.requestLayout();
    }

    @Override
    public void editAction() {
        // TODO Fill Edit Action.
        isEditing = !isEditing;
        swapEdit();
    }

    @NonNull
    @Override
    public WorldPlannerBaseModel getModel() {
        return existingRelationship;
    }
}
