package main.gui;

import main.CactiiAndCastles;
import oracle.jrockit.jfr.JFR;

import javax.swing.*;
import java.awt.*;

public class GameApp extends JFrame
{
    private final static JTextArea out = new JTextArea();
    private final static JTextField in = new JTextField();

    public GameApp()
    {
        super("Cactii And Castles");

        this.setSize(new Dimension(720, 480));
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;

        out.setEditable(false);
        out.setLineWrap(true);
        out.setWrapStyleWord(true);

        this.add(new JScrollPane(out), gbc);

        gbc.gridy = 1;
        gbc.weighty = 0;

        in.addActionListener(e ->
        {
            CactiiAndCastles.handleInput(in.getText());
            in.setText("");
            out.append("\n");
        });

        this.add(in, gbc);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void println(String s)
    {
        out.append(s + "\n");
    }
}
