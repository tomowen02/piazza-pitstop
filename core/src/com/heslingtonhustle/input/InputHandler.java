package com.heslingtonhustle.input;

import com.heslingtonhustle.Action;
import com.badlogic.gdx.InputProcessor;

public interface InputHandler extends InputProcessor {
    public Action getAction();
}