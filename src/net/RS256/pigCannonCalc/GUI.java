package net.RS256.pigCannonCalc;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;

public class GUI implements ActionListener {
    JPanel panel = new JPanel();
    JFrame frame = new JFrame();
    JTextField initialXInput;
    JTextField initialZInput;
    JTextField targetXInput;
    JTextField targetZInput;
    JLabel initialXLabel;
    JLabel initialZLabel;
    JLabel targetXLabel;
    JLabel targetZLabel;
    JLabel outputLabel;
    JTextArea ROM;
    JButton calculateButton;

    public static void main(String[] args) {
        new GUI();
    }

    public GUI() {
        this.panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.panel.setLayout(null);
        Font MonospaceFont = new Font("consolas", Font.PLAIN, 14);

        // initial X の表示

        this.initialXLabel = new JLabel("initial X");
        this.initialXLabel.setBounds(30, 15, 120, 30);
        this.panel.add(this.initialXLabel);
        this.initialXInput = new JTextField();
        this.initialXInput.setBounds(30, 45, 120, 30);
        this.panel.add(this.initialXInput);
        this.initialXInput.setText("0");

        // initial Z の表示

        this.initialZLabel = new JLabel("initial Z");
        this.initialZLabel.setBounds(180, 15, 120, 30);
        this.panel.add(this.initialZLabel);
        this.initialZInput = new JTextField();
        this.initialZInput.setBounds(180, 45, 120, 30);
        this.panel.add(this.initialZInput);
        this.initialZInput.setText("0");

        // target X の表示

        this.targetXLabel = new JLabel("target X");
        this.targetXLabel.setBounds(30, 90, 120, 30);
        this.panel.add(this.targetXLabel);
        this.targetXInput = new JTextField();
        this.targetXInput.setBounds(30, 120, 120, 30);
        this.panel.add(this.targetXInput);
        this.targetXInput.setText("0");

        // target Z の表示

        this.targetZLabel = new JLabel("target Z");
        this.targetZLabel.setBounds(180, 90, 120, 30);
        this.panel.add(this.targetZLabel);
        this.targetZInput = new JTextField();
        this.targetZInput.setBounds(180, 120, 120, 30);
        this.panel.add(this.targetZInput);
        this.targetZInput.setText("0");

        // calculate の表示

        this.calculateButton = new JButton("calculate!");
        this.calculateButton.setBounds(180, 195, 120, 29);
        this.calculateButton.addActionListener(this);
        this.panel.add(this.calculateButton);

        // output の表示

        this.outputLabel = new JLabel("output");
        this.outputLabel.setBounds(30, 240, 120, 30);
        this.panel.add(this.outputLabel);
        
        this.ROM = new JTextArea();
        this.ROM.setEditable(false);
        this.ROM.setBounds(30, 270, 270, 120);
        this.ROM.setBorder(BorderFactory.createEtchedBorder());
        this.ROM.setFont(MonospaceFont);
        this.panel.add(this.ROM);
        this.frame.add(this.panel, "Center");

        // タイトルとウィンドウサイズの指定

        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setTitle("pig cannon calculator");
        this.frame.pack();
        this.frame.setResizable(false);
        this.frame.setSize(345, 455);
        this.frame.setVisible(true);

    }

    public void actionPerformed(ActionEvent actionEvent) {
        int[] initialPos = new int[2];
        int[] targetPos = new int[2];
        double minDistance = 4.350125945; // magic number
        try {

            initialPos[0] = Integer.parseInt(this.initialXInput.getText());
            initialPos[1] = Integer.parseInt(this.initialZInput.getText());
            targetPos[0] = Integer.parseInt(this.targetXInput.getText());
            targetPos[1] = Integer.parseInt(this.targetZInput.getText());
        }
        catch (Exception var11) {
            this.ROM.setText("Illegal argument");
            return;
        }

        //checker
        if(initialPos[0] == 0) {
            initialPos[0] = 253;
            initialPos[1] = -1474;
            targetPos[0] = 0;
            targetPos[1] = 0;
        }

        this.ROM.setText(ROMCalculate(initialPos, targetPos, minDistance));
    }

    private static String ROMCalculate(int[] initialPos, int[] targetPos, double minDistance) {

        int dX = targetPos[0] - initialPos[0];
        int dZ = targetPos[1] - initialPos[1];

        String[] direction = calc.calcDirection(dX, dZ);

        int[] tick = calc.calcTick(dX, dZ, minDistance);
        String[] formattedOutput = calc.tickToFormattedString(tick);
        int[] estimatedTarget = calc.estimateTarget(tick, initialPos, minDistance);
        double[] estimatedMotion = calc.calcMotion(tick);

        return "-" + direction[0]
                + "-" + formattedOutput[0]
                + "-" + formattedOutput[1]
                + "-" + formattedOutput[2]
                + "-\n-" + direction[1]
                + "-" + formattedOutput[3]
                + "-" + formattedOutput[4]
                + "-" + formattedOutput[5]
                + "-\n\nestimated target:\n(" + estimatedTarget[0]
                + ", " + estimatedTarget[1]
                + ")\nestimated motion :\n"
                + Arrays.toString(estimatedMotion)
                ;
    }
}
