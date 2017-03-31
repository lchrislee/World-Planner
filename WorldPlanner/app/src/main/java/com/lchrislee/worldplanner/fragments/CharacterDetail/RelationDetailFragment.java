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
import com.lchrislee.worldplanner.adapters.RelationshipPickCharacterListAdapter;
import com.lchrislee.worldplanner.fragments.ToolbarSupportingFragment;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryCharacter;
import com.lchrislee.worldplanner.models.StoryElement;
import com.lchrislee.worldplanner.models.StoryRelationship;

/**
 * Created by chrisl on 3/28/17.
 */

public class RelationDetailFragment extends WorldPlannerBaseFragment implements RelationshipPickCharacterListAdapter.DefaultPlannerObjectSelected, ToolbarSupportingFragment{
    private static final String RELATIONSHIP_INDEX = "RELATIONDETAILFRAGMENT_REL_INDEX";
    private static final String OWNER_INDEX = "RELATIONDETAILFRAGMENT_CHAR_INDEX";

    private View mainView;
    private CardView card;
    private RecyclerView list;
    private EditText description;
    private ImageView image;
    private TextView name;
    private Button swap;

    private StoryRelationship existingStoryRelationship;
    private RelationshipPickCharacterListAdapter adapter;
    private StoryCharacter owner;
    private StoryCharacter otherCharacter;

    private boolean isEditing;
    private long relationshipIndex;

    public static @NonNull
    RelationDetailFragment newInstance(long rel, long character)
    {
        RelationDetailFragment dialog = new RelationDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(RELATIONSHIP_INDEX, rel);
        bundle.putLong(OWNER_INDEX, character);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        relationshipIndex = arguments.getLong(RELATIONSHIP_INDEX);
        long ownerIndex = arguments.getLong(OWNER_INDEX);
        isEditing = relationshipIndex == -1;
        existingStoryRelationship = DataManager.getInstance().getRelationshipForCharacterAtIndex(ownerIndex, relationshipIndex);
        if (existingStoryRelationship == null)
        {
            owner = DataManager.getInstance().getCharacterAtIndex(ownerIndex);
            if (owner != null)
            {
                existingStoryRelationship = new StoryRelationship();
                existingStoryRelationship.setFirstStoryCharacter(owner);
                existingStoryRelationship.setDescription("");
                existingStoryRelationship.setWorld(DataManager.getInstance().getCurrentWorld());
            }
        }
        else
        {
            owner = DataManager.getInstance().getCharacterAtIndex(ownerIndex);
            if (existingStoryRelationship.getFirstStoryCharacter() == owner)
            {
                otherCharacter = existingStoryRelationship.getSecondStoryCharacter();
            }
            else
            {
                otherCharacter = existingStoryRelationship.getFirstStoryCharacter();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_detail_relationship, container, false);

        card = (CardView) mainView.findViewById(R.id.list_default_card);
        description = (EditText) mainView.findViewById(R.id.fragment_relation_description);

        description.setText(existingStoryRelationship.getDescription());

        adapter = new RelationshipPickCharacterListAdapter(getContext(), this, null);
        list = (RecyclerView) mainView.findViewById(R.id.fragment_relation_character_list);
        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        list.setAdapter(adapter);

        image = (ImageView) mainView.findViewById(R.id.list_entity_image);
        name = (TextView) mainView.findViewById(R.id.list_entity_name);

        if (existingStoryRelationship.getFirstStoryCharacter() == owner)
        {
            StoryCharacter other = existingStoryRelationship.getSecondStoryCharacter();
            if (other != null)
            {
                name.setText(other.getName());
            }
        }
        else
        {
            name.setText(existingStoryRelationship.getFirstStoryCharacter().getName());
        }

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
    public void onItemSelected(long position) {
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

        existingStoryRelationship.setDescription(description.getText().toString());
        if (relationshipIndex == -1 || existingStoryRelationship.getFirstStoryCharacter() == owner)
        {
            existingStoryRelationship.setSecondStoryCharacter(otherCharacter);
            relationshipIndex = DataManager.getInstance().add(existingStoryRelationship);
        }
        else
        {
            existingStoryRelationship.setFirstStoryCharacter(otherCharacter);
            DataManager.getInstance().update(existingStoryRelationship);
        }

        mainView.requestLayout();
    }

    @Override
    public long editAction() {
        isEditing = !isEditing;
        swapEdit();
        return -1;
    }

    @NonNull
    @Override
    public StoryElement getModel() {
        return existingStoryRelationship;
    }
}
