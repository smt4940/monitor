package com.iranrayaneh.fileree.monitor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class MainFrame extends JFrame implements ActionListener {
    private static MainFrame mainFrame;
    private static SpringLayout layout = new SpringLayout();
    JTextField cpuInput = new JTextField(4);
    JTextField ramInput = new JTextField(4);
    JTextField diskInput = new JTextField(4);
    JTextField stepDurationInput = new JTextField(4);
    JTextField durationInput = new JTextField(4);
    JTextField cutLinesInput = new JTextField(4);
    JTextField stepsInput = new JTextField(4);
    private File srcFile;
    private File destFile;
    private JLabel srcPath = new JLabel();
    private JLabel destPath = new JLabel();
    private MonitorFactory monitorFactory;
    private Thread myThread;
    private StatusLabel statusLabel = StatusLabel.getInstance();


    private MainFrame(String title) {
        super(title);
        Container contentPane = this.getContentPane();
        this.setSize(800, 600);
        this.setBounds(100, 100, 800, 600);
        this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        contentPane.setSize(800, 600);
        contentPane.setBounds(this.getBounds());
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane.setLayout(layout);

        JLabel cpuLabel = new JLabel("پردازنده");
        JLabel ramLabel = new JLabel("رم");
        JLabel diskLabel = new JLabel("دیسک");
        JLabel stepsLabel = new JLabel("تعداد قدم");
        JLabel stepDurationLabel = new JLabel("مدت زمان مابین قدمها");
        JLabel durationLabel = new JLabel("مدت زمان اجرا");
        JLabel cutLinesLabel = new JLabel("تعداد خطوط برش");

        JButton srcBtn = new JButton("انتخاب مبداء");
        JButton destBtn = new JButton("انتخاب مقصد");
        JButton startBtn = new JButton("شروع");
        JButton stopBtn = new JButton("توقف");

        srcBtn.addActionListener(this);
        destBtn.addActionListener(this);
        startBtn.addActionListener(this);
        stopBtn.addActionListener(this);

        contentPane.add(startBtn);
        contentPane.add(stopBtn);
        contentPane.add(cpuLabel);
        contentPane.add(cpuInput);
        contentPane.add(ramLabel);
        contentPane.add(ramInput);
        contentPane.add(diskLabel);
        contentPane.add(statusLabel);
        contentPane.add(diskInput);
        contentPane.add(stepsInput);
        contentPane.add(stepDurationInput);
        contentPane.add(stepsLabel);
        contentPane.add(stepDurationLabel);
        contentPane.add(cutLinesInput);
        contentPane.add(durationLabel);
        contentPane.add(cutLinesLabel);
        contentPane.add(durationInput);
        contentPane.add(srcBtn);
        contentPane.add(srcPath);
        contentPane.add(destBtn);
        contentPane.add(destPath);
        layout.preferredLayoutSize(contentPane);
        layout.putConstraint(SpringLayout.NORTH, startBtn, 200, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, startBtn, -5, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, stopBtn, 200, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, stopBtn, -100, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, cpuLabel, 100, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, cpuLabel, 65, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, cpuInput, 100, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, cpuInput, 10, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, ramLabel, 135, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, ramLabel, 65, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, ramInput, 135, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, ramInput, 10, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, diskLabel, 170, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, diskLabel, 65, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, diskInput, 170, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, diskInput, 10, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, stepsLabel, 350, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, stepsLabel, -5, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, stepsInput, 350, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, stepsInput, -45, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, stepDurationLabel, 350, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, stepDurationLabel, -115, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, stepDurationInput, 350, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, stepDurationInput, -205, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, cutLinesLabel, 350, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, cutLinesLabel, -285, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, cutLinesInput, 350, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, cutLinesInput, -355, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, durationLabel, 350, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, durationLabel, -425, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, durationInput, 350, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, durationInput, -490, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, srcBtn, 5, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, srcBtn, -5, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, srcPath, 5, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, srcPath, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, destBtn, 35, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, destBtn, -5, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, destPath, 35, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, destPath, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, statusLabel,-100,SpringLayout.SOUTH,contentPane);
        layout.putConstraint(SpringLayout.EAST, statusLabel,-5,SpringLayout.EAST,contentPane);
        contentPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        contentPane.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        this.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        this.revalidate();
        this.repaint();
    }

    public static MainFrame getInstance(String title) {
        if (mainFrame == null) {
            mainFrame = new MainFrame(title);
            return mainFrame;
        } else return mainFrame;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("انتخاب مبداء")) {
            JFileChooser srcFileChooser = new JFileChooser();
            srcFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            srcFileChooser.setDialogTitle("انتخاب فایل لاگ مورد نظر برای برش");
            int srcAnswer = srcFileChooser.showOpenDialog(mainFrame);

            if (srcAnswer == JFileChooser.APPROVE_OPTION) {
                srcFile = srcFileChooser.getSelectedFile();
                srcPath.setText(srcFile.getAbsolutePath());
                srcPath.setForeground(Color.BLACK);
            } else if (srcAnswer == JFileChooser.ERROR_OPTION) {
                srcFileChooser.cancelSelection();
                srcPath.setForeground(Color.RED);
                srcPath.setText("فایل مناسبی یافت نشد!");
            }
        }

        if (command.equals("انتخاب مقصد")) {
            JFileChooser destFileChooser = new JFileChooser();
            destFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            destFileChooser.setDialogTitle("انتخاب فایل مورد نظر برای ذخیره برش");
            int destAnswer = destFileChooser.showOpenDialog(mainFrame);

            if (destAnswer == JFileChooser.APPROVE_OPTION) {
                destFile = destFileChooser.getSelectedFile();
                destPath.setText(destFile.getAbsolutePath());
                destPath.setForeground(Color.BLACK);
            } else if (destAnswer == JFileChooser.ERROR_OPTION) {
                destFileChooser.cancelSelection();
                destPath.setForeground(Color.RED);
                destPath.setText("فایل مناسبی یافت نشد!");
            }
        }

        if (command.equals("شروع")) {
            MonitiorDto monitiorDto = new MonitiorDto.Builder()
                    .cpuTreshold(cpuInput.getText())
                    .ramTreshold(ramInput.getText())
                    .diskTreshold(diskInput.getText())
                    .steps(stepsInput.getText())
                    .stepDuration(stepDurationInput.getText())
                    .lines(cutLinesInput.getText())
                    .duration(durationInput.getText()).build();

            if (InputValidator.validate(monitiorDto)) {
                Runnable monitor = MonitorFactory.init(monitiorDto, srcFile, destFile);
                if(myThread == null)myThread = new Thread(monitor);
                myThread.start();
                statusLabel.updateLabel("وضعیت :");
                statusLabel.appendLabel("شروع بررسی وضعیت منابع انتخاب شده");
            } else JOptionPane.showMessageDialog(this, "مقادیر باید عددی باشند!");
        }
        if (command.equals("توقف")) {
            myThread = null;
            statusLabel.appendLabel("عملیات توسط کاربر متوقف شد");
        }
    }
}
