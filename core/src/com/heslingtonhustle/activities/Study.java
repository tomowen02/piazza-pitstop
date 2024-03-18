package com.heslingtonhustle.activities;

import com.badlogic.gdx.Gdx;
import com.heslingtonhustle.state.DialogManager;
import com.heslingtonhustle.state.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Study implements Activity {

    private State gameState;

    public Study (State gameState) {
        this.gameState = gameState;
    }

    public void start() {
        DialogManager dialogManager = gameState.getDialogManager();
        List<String> options = new ArrayList<String>(Arrays.asList("Yes", "No"));
        dialogManager.addDialog("Would you like to study here?", options, selectedOption -> {
            switch (selectedOption) {
                case 0:
                    Gdx.app.debug("ACTIVITY", "You have just studied");
                    break;
                case 1:
                    break;
            }
        });
    }
}
