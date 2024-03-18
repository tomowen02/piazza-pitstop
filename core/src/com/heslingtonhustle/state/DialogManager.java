package com.heslingtonhustle.state;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DialogManager {
    private Queue<DialogBox> dialogQueue;
    private boolean isShowing;

    public DialogManager() {
        dialogQueue = new LinkedList<DialogBox>();
    }

    public boolean isEmpty() {
        return dialogQueue.isEmpty();
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void add(DialogBox dialogBox) {
        dialogQueue.add(dialogBox);
    }

    public DialogBox showDialog() {
        if (dialogQueue.isEmpty()) {
            return null;
        }
        isShowing = true;
        DialogBox dialogBox = dialogQueue.peek();
        return dialogBox;
    }

    public boolean submit() {
        if (!isShowing) {
            return false;
        }
        dialogQueue.poll();
        return true;
    }
}
