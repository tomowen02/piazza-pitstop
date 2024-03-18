package com.heslingtonhustle.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.heslingtonhustle.state.Action;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;

/** Transform the user's raw input into a game Action.
 * Maintains a small FIFO buffer of actions.
 * */
public class KeyboardInputHandler extends InputAdapter implements InputHandler {
    public static final int INPUT_BUFFER_LIMIT = 5;
    private final HashMap<Integer, Action> inputMap;
    private final HashSet<Integer> pressedKeys;
    private final Queue<Action> inputBuffer;

    public KeyboardInputHandler() {
        inputMap = new HashMap<>();
        pressedKeys = new HashSet<>();
        inputBuffer = new LinkedList<>();

        inputMap.put(Keys.D, Action.MOVE_RIGHT);
        inputMap.put(Keys.A, Action.MOVE_LEFT);
        inputMap.put(Keys.W, Action.MOVE_UP);
        inputMap.put(Keys.S, Action.MOVE_DOWN);

        inputMap.put(Keys.RIGHT, Action.MOVE_RIGHT);
        inputMap.put(Keys.LEFT, Action.MOVE_LEFT);
        inputMap.put(Keys.UP, Action.MOVE_UP);
        inputMap.put(Keys.DOWN, Action.MOVE_DOWN);

        inputMap.put(Keys.SPACE, Action.INTERACT);
        inputMap.put(Keys.ENTER, Action.INTERACT);

        inputMap.put(Keys.ESCAPE, Action.PAUSE);

        inputMap.put(Keys.COMMA, Action.DEBUGGING_ACTION1); // This is just to quickly trigger an event while debugging
        inputMap.put(Keys.PERIOD, Action.DEBUGGING_ACTION2);
        inputMap.put(Keys.SLASH, Action.DEBUGGING_ACTION3);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (!inputMap.containsKey(keycode)) return false;
        if (inputBuffer.size() >= INPUT_BUFFER_LIMIT) return true;
        pressedKeys.add(keycode);
        return inputBuffer.add(inputMap.get(keycode));
    }

    @Override
    public boolean keyUp(int keycode) {
        if (!inputMap.containsKey(keycode)) return false;
        pressedKeys.remove(keycode);
        switch (pressedKeys.size()) {
            case 0:
                return inputBuffer.add(Action.STOP);
            case 1:
                int key = pressedKeys.iterator().next();
                return inputBuffer.add(inputMap.get(key));
            default:
                return true;
        }
    }

    public Action getAction() {
        if (inputBuffer.isEmpty()) return null;
        return inputBuffer.poll();
    }
}
