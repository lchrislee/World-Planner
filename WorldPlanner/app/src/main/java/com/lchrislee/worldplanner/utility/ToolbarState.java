package com.lchrislee.worldplanner.utility;

import com.lchrislee.worldplanner.R;

import java.util.ArrayList;

/**
 * Created by chrisl on 3/28/17.
 */

public enum ToolbarState
{
    Edit,
    Edit_Delete,
    Save,
    Empty,
    Edit_Share_Delete;

    public ArrayList<Integer> getHiddenIds(ToolbarState state)
    {
        ArrayList<Integer> itemsToHide = new ArrayList<>();
        switch(state)
        {
            case Edit:
                itemsToHide.add(R.id.menu_save);
                itemsToHide.add(R.id.menu_share);
                itemsToHide.add(R.id.menu_delete);
                break;
            case Edit_Delete:
                itemsToHide.add(R.id.menu_save);
                itemsToHide.add(R.id.menu_share);
                break;
            case Save:
                itemsToHide.add(R.id.menu_edit);
                itemsToHide.add(R.id.menu_share);
                itemsToHide.add(R.id.menu_delete);
                break;
            case Empty:
                itemsToHide.add(R.id.menu_save);
                itemsToHide.add(R.id.menu_edit);
                itemsToHide.add(R.id.menu_share);
                itemsToHide.add(R.id.menu_delete);
                break;
            case Edit_Share_Delete:
                itemsToHide.add(R.id.menu_save);
                break;
        }
        return itemsToHide;
    }
}