package com.lchrislee.worldplanner.fragments.current_world;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lchrislee.worldplanner.R;
import com.lchrislee.worldplanner.activities.ElementListActivity;
import com.lchrislee.worldplanner.fragments.WorldPlannerBaseFragment;
import com.lchrislee.worldplanner.managers.DataManager;

public class WorldElementsFragment extends WorldPlannerBaseFragment {

    public WorldElementsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_world_elements, container, false);

        final ElementTypeListAdapter adapter = new ElementTypeListAdapter(getContext());

        final RecyclerView list = (RecyclerView) v.findViewById(R.id.fragment_world_elements_list);
        list.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        list.setAdapter(adapter);

        return v;
    }
    class ElementTypeListAdapter extends RecyclerView.Adapter<ElementTypeListAdapter.ElementTypeViewHolder>
    {
        class ElementTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            final TextView title;
            ElementTypeViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.list_element_name);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int requestCode = 0;
                switch((int) v.getTag())
                {
                    case 0:
                        requestCode = DataManager.CHARACTER;
                        break;
                    case 1:
                        requestCode = DataManager.LOCATION;
                        break;
                    case 2:
                        requestCode = DataManager.ITEM;
                        break;
                    case 3:
                        requestCode = DataManager.GROUP;
                        break;
                }

                Intent i = new Intent(getContext(), ElementListActivity.class);
                i.putExtra(ElementListActivity.TYPE, requestCode);
                startActivityForResult(i, requestCode);
            }
        }

        private final Context context;
        private final String[] elementTypes = {"Characters", "Locations", "Items", "Groups"};

        ElementTypeListAdapter(Context context) {
            this.context = context;
        }

        @Override
        public ElementTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.list_element, parent, false);
            return new ElementTypeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ElementTypeViewHolder holder, int position) {
            holder.itemView.setTag(position);
            holder.title.setText(elementTypes[position]);
        }

        @Override
        public int getItemCount() {
            return elementTypes.length;
        }
    }

}
