package com.heslingtonhustle.state;

import java.util.List;

public class DialogBox {
    private String message;
    private List<String> options;
    private int selectedOption;
    private boolean isSubmitted;

    public DialogBox(String message, List<String> options) {
        this.message = message;
        this.options = options;
    }

    public void submit() {
        isSubmitted = true;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getSelectedOption() {
        return selectedOption;
    }

    public void decreaseSelection() {
        selectedOption = (selectedOption+options.size()-1) % options.size();
    }

    public void increaseSelection() {
        selectedOption = (selectedOption+1)%options.size();
    }
}
