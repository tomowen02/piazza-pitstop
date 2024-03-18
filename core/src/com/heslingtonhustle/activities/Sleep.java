package com.heslingtonhustle.activities;

import com.heslingtonhustle.state.DialogManager;
import com.heslingtonhustle.state.State;

public class Sleep implements Activity {

    private State gameState;

    public Sleep(State gameState) {
        this.gameState = gameState;
    }

    @Override
    public void start() {
        gameState.incrementDay();
    }
}
