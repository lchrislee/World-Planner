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

    public void addCharacter(@NonNull StoryCharacter c)
    {
        importantCharacters.addObject(c);
    }

    public void removeCharacter(@NonNull StoryCharacter c)
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
}
