package com.heslingtonhustle.state;

import java.util.*;

public class DialogueManager {

    // DialogueBox is a private inner class that can only be interacted with through DialogueManager
    // Public methods within DialogueBox are only accessible to DialogueManager
    private class DialogueBox {
        private String message;
        private List<String> options;
        private int selectedOption;
        private DialogueCallback callback;

        private DialogueBox(String message, List<String> options, DialogueCallback callback) {
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


    // ~~~ DialogueManager's properties and methods ~~~

    private Queue<DialogueBox> dialogueQueue;

    public DialogueManager() {
        dialogueQueue = new LinkedList<DialogueBox>();
    }

    // Public methods
    public boolean isEmpty() {
        return dialogueQueue.isEmpty();
    }

    public void addDialogue(String message, List<String> options, DialogueCallback callback) {
        DialogueBox dialogueBox = new DialogueBox(message, options, callback);
        dialogueQueue.add(dialogueBox);
    }
    public void addDialogue(String message, List<String> options) {
        DialogueCallback callback = null;
        DialogueBox dialogueBox = new DialogueBox(message, options, callback);
        dialogueQueue.add(dialogueBox);
    }
    public void addDialogue(String message) {
        List<String> options = new ArrayList<String>(Arrays.asList("Close"));
        DialogueCallback callback = null;
        DialogueBox dialogueBox = new DialogueBox(message, options, callback);
        dialogueQueue.add(dialogueBox);
    }
    public void addDialogue(String message, DialogueCallback callback) {
        List<String> options = new ArrayList<String>(Arrays.asList("Close"));
        DialogueBox dialogueBox = new DialogueBox(message, options, callback);
        dialogueQueue.add(dialogueBox);
    }

    public String getMessage() {
        if (dialogueQueue.isEmpty()){
            return null;
        }
        DialogueBox dialogueBox = dialogueQueue.peek();
        return dialogueBox.message;
    }

    public List<String> getOptions() {
        if (dialogueQueue.isEmpty()){
            return null;
        }
        DialogueBox dialogueBox = dialogueQueue.peek();
        return dialogueBox.options;
    }

    public int getSelectedOption() {
        if (dialogueQueue.isEmpty()){
            return -1;
        }
        DialogueBox dialogueBox = dialogueQueue.peek();
        return dialogueBox.selectedOption;
    }

    public void decreaseSelection() {
        DialogueBox dialogueBox = getDialogueBox();
        dialogueBox.decreaseSelection();
    }

    public void increaseSelection() {
        DialogueBox dialogueBox = getDialogueBox();
        dialogueBox.increaseSelection();
    }

    public int submit() {
        if (dialogueQueue.isEmpty()) {
            throw new RuntimeException("There are no dialog boxes to submit");
        }
        DialogueBox dialogueBox = dialogueQueue.remove();
        dialogueBox.submit();
        return dialogueBox.selectedOption;
    }

    private DialogueBox getDialogueBox() {
        if (dialogueQueue.isEmpty()) {
            throw new RuntimeException("There are no dialog boxes to get");
        }
        return dialogueQueue.peek();
    }
}
