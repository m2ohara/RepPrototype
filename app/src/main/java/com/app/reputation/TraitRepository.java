package com.app.reputation;

import com.example.android.softkeyboard.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by michael on 27/11/17.
 */

public class TraitRepository {

    private List<Integer> TraitIds = Arrays.asList(R.drawable.emoji_1f61d, R.drawable.emoji_1f60e);

    public void AddTraitId(int traidId) {
        TraitIds.add(traidId);
    }

    public List<Integer> GetTraitIds() {
        return TraitIds;
    }

}
