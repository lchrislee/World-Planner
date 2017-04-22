package com.lchrislee.worldplanner.fragments.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.managers.DataManager;
import com.lchrislee.worldplanner.models.StoryEvent;

public class SingleSelectEventTypeDialog extends SingleSelectDialog {

    public static @NonNull
    SingleSelectEventTypeDialog newInstance(@Nullable String title)
    {
        SingleSelectEventTypeDialog fragment = new SingleSelectEventTypeDialog();
        Bundle arguments = new Bundle();
        arguments.putInt(LAYOUT, R.layout.dialog_select);
        arguments.putString(TITLE, title);
        fragment.setArguments(arguments);
        return fragment;
    }

    @NonNull
    @Override
    protected SingleSelectListAdapter obtainAdapter() {
        return new EventTypeSingleSelectAdapter(getContext());
    }

    private class EventTypeSingleSelectAdapter extends SingleSelectListAdapter
    {
        EventTypeSingleSelectAdapter(Context context) {
            super(context);
        }

        @Override
        protected void additionalBinding(SingleSelectViewHolder holder, int position) {
            StoryEvent.StoryEventType type = DataManager.getInstance().getEventTypeAtIndex(position);
            if (type != null) {
                holder.name.setText(type.getName());
                holder.itemView.setBackgroundColor(type.getColor());
            }
        }

        @Override
        protected void updateElements() {
            notifyItemRangeChanged(0, DataManager.getInstance().getEventTypeCountForWorld());
        }

        @Override
        public int getItemCount() {
            return DataManager.getInstance().getEventTypeCountForWorld();
        }
    }
}
