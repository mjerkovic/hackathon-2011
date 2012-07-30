package com.mlj.steeringdemo.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.mlj.steeringdemo.world.FlockingWorld;
import com.mlj.steeringdemo.world.GoalBasedWorld;
import com.mlj.steeringdemo.world.InterposeWorld;
import com.mlj.steeringdemo.world.ObstacleAvoidanceWorld;
import com.mlj.steeringdemo.world.OffsetPursuitWorld;
import com.mlj.steeringdemo.world.PursuitWorld;
import com.mlj.steeringdemo.world.SeekWorld;
import com.mlj.steeringdemo.world.WanderWorld;

public class MainFrame extends JFrame implements WindowListener, ActionListener {

    private JPanel demoArea;
    private DemoPanel currentDemo = null;
    private ButtonPanel buttonPanel;

    public MainFrame() {
        super("Steering Behaviours");
        setPreferredSize(new Dimension(1000, 800));
        setLayout(new BorderLayout());
        addWindowListener(this);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setFocusable(true);
        add(mainPanel, BorderLayout.CENTER);
        addButtonPanelTo(mainPanel);
        addDemoAreaTo(mainPanel);
        pack();
        setVisible(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("e.getKeyCode() = " + e.getKeyCode());
            }
        });
    }

    private void addButtonPanelTo(JPanel panel) {
        buttonPanel = new ButtonPanel(this);
        panel.add(buttonPanel, BorderLayout.WEST);
    }

    private void addDemoAreaTo(JPanel panel) {
        demoArea = new JPanel(new BorderLayout());
        demoArea.setFocusable(true);
        panel.add(demoArea, BorderLayout.CENTER);
    }

    private void seekDemo() {
        clearDemoArea();
        final Mediator mediator = new Mediator(new SeekWorld());
        currentDemo = new DemoPanel(mediator);
        currentDemo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mediator.moveTo(e.getPoint());
            }
        });
        addToDemoArea();
    }

    private void fleeDemo() {
        clearDemoArea();
        final Mediator mediator = new Mediator(new SeekWorld());
        currentDemo = new DemoPanel(mediator);
        currentDemo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mediator.fleeFrom(e.getPoint());
            }
        });
        addToDemoArea();
    }

    private void arriveDemo() {
        clearDemoArea();
        final Mediator mediator = new Mediator(new SeekWorld());
        currentDemo = new DemoPanel(mediator);
        currentDemo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mediator.arriveAt(e.getPoint());
            }
        });
        addToDemoArea();
    }

    private void pursuitDemo() {
        clearDemoArea();
        final PursuitWorld world = new PursuitWorld();
        world.pursuit();
        final Mediator mediator = new Mediator(world);
        currentDemo = new DemoPanel(mediator, new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_S:
                        world.toggleShonkyPursuit();
                        break;
                    default:
                        ;

                }
            }
        });
        addToDemoArea();
    }

    private void offsetPursuitDemo() {
        clearDemoArea();
        final OffsetPursuitWorld world = new OffsetPursuitWorld();
        final Mediator mediator = new Mediator(world);
        currentDemo = new DemoPanel(mediator);
        addToDemoArea();
    }

    private void wanderDemo() {
        clearDemoArea();
        final WanderWorld world = new WanderWorld(10);
        final Mediator mediator = new Mediator(world);
        currentDemo = new DemoPanel(mediator, new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        world.toggleWallNormals();
                        break;
                    case KeyEvent.VK_F:
                        world.toggleFeelers();
                        break;
                    default:
                        ;

                }
            }
        });
        addToDemoArea();
    }

    private void obstacleAvoidanceDemo() {
        clearDemoArea();
        final ObstacleAvoidanceWorld world = new ObstacleAvoidanceWorld(10);
        final Mediator mediator = new Mediator(world);
        currentDemo = new DemoPanel(mediator);
        addToDemoArea();
    }

    private void hideDemo() {
        clearDemoArea();
        final PursuitWorld world = new PursuitWorld();
        world.hide();
        final Mediator mediator = new Mediator(world);
        currentDemo = new DemoPanel(mediator);
        currentDemo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mediator.arriveAt(e.getPoint());
            }
        });
        addToDemoArea();
    }

    private void sneakDemo() {
        clearDemoArea();
        final PursuitWorld world = new PursuitWorld();
        world.sneak();
        final Mediator mediator = new Mediator(world);
        currentDemo = new DemoPanel(mediator);
        addToDemoArea();
    }

    private void interposeDemo() {
        clearDemoArea();
        final InterposeWorld world = new InterposeWorld();
        final Mediator mediator = new Mediator(world);
        currentDemo = new DemoPanel(mediator);
        addToDemoArea();
    }


    private void flockingDemo(int numberOfVehicles) {
        clearDemoArea();
        final FlockingWorld world = new FlockingWorld(numberOfVehicles);
        final Mediator mediator = new Mediator(world);
        currentDemo = new DemoPanel(mediator);
        demoArea.add(currentDemo, BorderLayout.CENTER);
    }

    private void goalBasedDemo() {
        clearDemoArea();
        final GoalBasedWorld world = new GoalBasedWorld();
        final Mediator mediator = new Mediator(world);
        currentDemo = new DemoPanel(mediator);
        currentDemo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mediator.arriveAt(e.getPoint());
            }
        });
        addToDemoArea();
    }


    private void addToDemoArea() {
        demoArea.add(currentDemo, BorderLayout.CENTER);
        currentDemo.requestFocusInWindow();
    }

    private void clearDemoArea() {
        if (currentDemo != null) {
            currentDemo.stopDemo();
            demoArea.remove(currentDemo);
        }
    }

    public void actionPerformed(ActionEvent e) {
        final String action = e.getActionCommand();
        if ("seek".equalsIgnoreCase(action)) {
            seekDemo();
        }
        if ("flee".equalsIgnoreCase(action)) {
            fleeDemo();
        }
        if ("arrive".equalsIgnoreCase(action)) {
            arriveDemo();
        }
        if ("pursuit".equalsIgnoreCase(action)) {
            pursuitDemo();
        }
        if ("offset pursuit".equalsIgnoreCase(action)) {
            offsetPursuitDemo();
        }
        if ("wander".equalsIgnoreCase(action)) {
            wanderDemo();
        }
        if ("obstacle avoidance".equalsIgnoreCase(action)) {
            obstacleAvoidanceDemo();
        }
        if ("hide".equalsIgnoreCase(action)) {
            hideDemo();
        }
        if ("sneak".equalsIgnoreCase(action)) {
            sneakDemo();
        }
        if ("interpose".equalsIgnoreCase(action)) {
            interposeDemo();
        }
        if ("flocking".equalsIgnoreCase(action) || "reset".equalsIgnoreCase(action)) {
            flockingDemo(buttonPanel.getBoids());
        }
        if ("goal based".equalsIgnoreCase(action)) {
            goalBasedDemo();
        }
        resetTitleTo(action);
    }

    private void resetTitleTo(String action) {
        setTitle(String.format("Steering Behaviours - %s", action));
    }

    public void windowActivated(WindowEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void windowOpened(WindowEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    public void windowClosed(WindowEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void windowIconified(WindowEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void windowDeiconified(WindowEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void windowDeactivated(WindowEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
