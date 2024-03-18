package com.heslingtonhustle.state;

import java.util.*;

public class DialogManager {

    // DialogBox is a private inner class that can only be interacted with through DialogManager
    // Public methods within DialogBox are only accessible to DialogManager
    private class DialogBox {
        private String message;
        private List<String> options;
        private int selectedOption;
        private DialogCallback callback;

        private DialogBox(String message, List<String> options, DialogCallback callback) {
            this.message = message;
            this.options = options;
            this.callback = callback;
        }
        private int decreaseSelection() {
            selectedOption = (selectedOption+options.size()-1) % options.size();
            return selectedOption;
        }
        private int increaseSelection() {
            selectedOption = (selectedOption+1)%options.size();
            return selectedOption;
        }
        private void submit() {
            if (callback != null) {
                callback.onSelected(selectedOption);
            }
        }
    }


    // DialogManager's properties and methods
    private Queue<DialogBox> dialogQueue;

    public DialogManager() {
        dialogQueue = new LinkedList<DialogBox>();
    }

    // Public methods
    public boolean isEmpty() {
        return dialogQueue.isEmpty();
    }

    public void addDialog(String message, List<String> options, DialogCallback callback) {
        DialogBox dialogBox = new DialogBox(message, options, callback);
        dialogQueue.add(dialogBox);
    }
    public void addDialog(String message, List<String> options) {
        DialogCallback callback = null;
        DialogBox dialogBox = new DialogBox(message, options, callback);
        dialogQueue.add(dialogBox);
    }
    public void addDialog(String message) {
        List<String> options = new ArrayList<String>(Arrays.asList("Close"));
        DialogCallback callback = null;
        DialogBox dialogBox = new DialogBox(message, options, callback);
        dialogQueue.add(dialogBox);
    }
    public void addDialog(String message, DialogCallback callback) {
        List<String> options = new ArrayList<String>(Arrays.asList("Close"));
        DialogBox dialogBox = new DialogBox(message, options, callback);
        dialogQueue.add(dialogBox);
    }

    public String getMessage() {
        if (dialogQueue.isEmpty()){
            return null;
        }
        DialogBox dialogBox = dialogQueue.peek();
        return dialogBox.message;
    }

    public List<String> getOptions() {
        if (dialogQueue.isEmpty()){
            return null;
        }
        DialogBox dialogBox = dialogQueue.peek();
        return dialogBox.options;
    }

    public int getSelectedOption() {
        if (dialogQueue.isEmpty()){
            return -1;
        }
        DialogBox dialogBox = dialogQueue.peek();
        return dialogBox.selectedOption;
    }

    public void decreaseSelection() {
        DialogBox dialogBox = getDialogBox();
        dialogBox.decreaseSelection();
    }

    public void increaseSelection() {
        DialogBox dialogBox = getDialogBox();
        dialogBox.increaseSelection();
    }

    public int submit() {
        if (dialogQueue.isEmpty()) {
            throw new RuntimeException("There are no dialog boxes to submit");
        }
        DialogBox dialogBox = dialogQueue.remove();
        dialogBox.submit();
        return dialogBox.selectedOption;
    }

    private DialogBox getDialogBox() {
        if (dialogQueue.isEmpty()) {
            throw new RuntimeException("There are no dialog boxes to get");
        }
        return dialogQueue.peek();
    }
}
