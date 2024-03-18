package com.heslingtonhustle.input;

import com.heslingtonhustle.state.Action;
import com.badlogic.gdx.InputProcessor;

public interface InputHandler extends InputProcessor {
    Action getAction();
}