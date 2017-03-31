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
    Empty;

    public ArrayList<Integer> getHiddenIds()
    {
        ArrayList<Integer> itemsToHide = new ArrayList<>();
        switch(this)
        {
            case Edit:
                itemsToHide.add(R.id.menu_save);
                itemsToHide.add(R.id.menu_delete);
                break;
            case Edit_Delete:
                itemsToHide.add(R.id.menu_save);
                break;
            case Save:
                itemsToHide.add(R.id.menu_edit);
                itemsToHide.add(R.id.menu_delete);
                break;
            case Empty:
                itemsToHide.add(R.id.menu_save);
                itemsToHide.add(R.id.menu_edit);
                itemsToHide.add(R.id.menu_delete);
                break;
        }
        return itemsToHide;
    }
}