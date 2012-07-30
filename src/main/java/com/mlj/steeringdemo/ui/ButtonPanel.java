package com.mlj.steeringdemo.ui;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public class ButtonPanel extends JPanel {

    private final MainFrame mainFrame;
    private final JTextField boids;

    public ButtonPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 800));
        setBorder(new BevelBorder(BevelBorder.RAISED));
        addButton("Seek");
        addButton("Flee");
        addButton("Arrive");
        addButton("Pursuit");
        addButton("Offset Pursuit");
        addButton("Wander");
        addButton("Obstacle Avoidance");
        addButton("Hide");
        addButton("Sneak");
        addButton("Interpose");
        addButton("Flocking");
        addButton("Goal Based");
        boids = new JTextField("50");
        add(boids);
        addButton("Reset");

    }

    private void addButton(String action) {
        JButton button = new JButton(action);
        button.setActionCommand(action);
        button.addActionListener(mainFrame);
        add(button);
    }

    public int getBoids() {
        return Integer.valueOf(boids.getText());
    }

}
