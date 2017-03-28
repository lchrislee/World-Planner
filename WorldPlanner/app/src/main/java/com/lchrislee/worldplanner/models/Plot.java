package com.lchrislee.worldplanner.models;

import android.support.annotation.NonNull;

/**
 * Created by chrisl on 3/26/17.
 */

public class Plot extends WorldPlannerBaseModel implements ImportanceRelation.Important {

    ImportanceRelation importantCharacters;

    public Plot(@NonNull String title, @NonNull String description) {
        super(title, description);
        importantCharacters = new ImportanceRelation();
    }

    public void addCharacter(@NonNull Character c)
    {
        importantCharacters.addObject(c);
    }

    public void removeCharacter(@NonNull Character c)
    {
        importantCharacters.removeObject(c);
    }

    public @NonNull ImportanceRelation getImportantCharacters() {
        return importantCharacters;
    }

    @NonNull
    @Override
    public ImportanceRelation.ImportantType getImportanceType() {
        return ImportanceRelation.ImportantType.Plot;
    }

    @NonNull
    @Override
    public String getImportantTypeString() {
        return "Plot";
    }
}
