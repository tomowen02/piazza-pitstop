package com.heslingtonhustle.input;

import com.heslingtonhustle.state.Action;
import com.badlogic.gdx.InputProcessor;

/**
 * Transform the user's raw input to a game Action.
 * Extends LibGDX's InputProcessor for convenience, but this could be omitted.
 */
public interface InputHandler extends InputProcessor {
    Action getAction();
}