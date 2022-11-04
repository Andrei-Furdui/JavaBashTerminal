package com.jbashterminal.windows.JFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StartFrame extends JFrame {
    private JTextArea textArea;

    public StartFrame() {
        this.setTitle("Windows Terminal");

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();

        int width = screenSize.width;
        int height = screenSize.height;

        this.setSize(width / 2, height / 2);
        this.setLocation(width / 2 - this.getSize().width / 2,
                height / 2 - this.getSize().height / 2);


        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        textArea = new JTextArea();

        addEnterListener();

        JScrollPane scroll = new JScrollPane(textArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        this.add(scroll);
        this.setVisible(true);
    }

    private void addEnterListener() {
        this.textArea.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {

            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    System.out.println(getLastLine("-" + textArea.getText()));
                }
            }

            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private String getLastLine(String fullText) {
        System.out.println("PPP " + fullText + "###");
        if (fullText.isEmpty()) {
            return null;
        }
        int lastLineIndex = fullText.split("\n").length;
        return fullText.split("\n")[lastLineIndex - 1];
    }

    public static void main(String[] args) {
        new StartFrame();
    }
}
