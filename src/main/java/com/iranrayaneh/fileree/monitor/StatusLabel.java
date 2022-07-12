package com.iranrayaneh.fileree.monitor;

import javax.swing.*;

public class StatusLabel extends JTextPane{
    private static StatusLabel initLabel;

    private StatusLabel(String text) {
        super();
        this.setText(text);
    }

    public static StatusLabel getInstance(){
        if(initLabel == null)initLabel=new StatusLabel("وضعیت:");
        return initLabel;
    }

    public StatusLabel appendLabel(String text){
        initLabel.setText(initLabel.getText() + "\n" + text);
        return initLabel;
    }

    public StatusLabel updateLabel(String text){
        initLabel.setText(text);
        return initLabel;
    }
}
