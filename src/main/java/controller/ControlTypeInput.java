package controller;

import config.LayoutConfig;

import javax.swing.*;

public class ControlTypeInput {
    private static ControlTypeInput controlTypeInput;
    private Object[] options;

    private ControlTypeInput() {
        init();
    }

    public static ControlTypeInput getInstance() {
        return controlTypeInput == null ? controlTypeInput = new ControlTypeInput() : controlTypeInput;
    }

    private void init() {
        options = new Object[]{"File", "Text"};
    }

    public String getNameLayoutInput() {
        int choice = JOptionPane.showOptionDialog(
                null,
                "What type do you want?",
                "Choose type your input",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        return choice == 0 ? LayoutConfig.FILE_INPUT_LAYOUT :
                (choice == 1 ? null : " ");
    }
}
