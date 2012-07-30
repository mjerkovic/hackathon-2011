package com.mlj.steeringdemo.ui;

import static java.awt.Color.WHITE;
import static java.lang.Math.max;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class DemoPanel extends JPanel implements Runnable {

    private static final int PWIDTH = 800;
    private static final int PHEIGHT = 800;
    private static final int FRAMES_PER_SECOND = 80;
    private static final long DELAY = 41000000L;

    private static final long NANOS_PER_SECOND = 1000000000L;
    private volatile boolean running = false;

    private volatile boolean gameOver = false;
    private Thread animator;
    private Graphics dbg;
    private Image dbImage = null;
    protected final Mediator mediator;

    public DemoPanel(Mediator mediator) {
        this.mediator = mediator;
        setBackground(WHITE);
        setSize(new Dimension(PWIDTH, PHEIGHT));
        setPreferredSize(new Dimension(PWIDTH, PHEIGHT));
        setFocusable(true);
        setVisible(true);
    }

    public DemoPanel(Mediator mediator, KeyListener keyListener) {
        this(mediator);
        addKeyListener(keyListener);
    }

    public void addNotify() {
        super.addNotify();
        startGame();
    }

    public void stopDemo() {
        running = false;
    }

    public void run() {
        running = true;
        long timeDiff = 0;
        long startTime = System.nanoTime();
        while (running) {
            gameUpdate();
            gameRender();
            paintScreen();
            timeDiff = (startTime + DELAY) - System.nanoTime();
            try {
                //System.out.println("timeDiff/1000000L = " + timeDiff / 1000000L);
                Thread.sleep(max(0, (timeDiff/1000000L)));
            } catch (InterruptedException e) {
            }
            startTime = System.nanoTime();
        }
        //System.exit(0);
    }

    private void paintScreen() {
        Graphics g;
        try {
            g = this.getGraphics();
            if (g != null && dbImage != null) {
                g.drawImage(dbImage, 0, 0, null);
            }
            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        } catch (Exception e) {
            System.out.println("Graphics context error: " + e);
        }
    }

    private void startGame() {
        if (animator == null || !running) {
            animator = new Thread(this);
            animator.start();
        }
    }

    private void gameUpdate() {
        if (!gameOver) {
            mediator.update();
        }
    }

    private void gameRender() {
        if (dbImage == null) {
            dbImage = createImage(PWIDTH, PHEIGHT);
            if (dbImage == null) {
                System.out.println("dbImage is null");
                return;
            } else {
                dbg = dbImage.getGraphics();
            }
        }
        dbg.setColor(WHITE);
        dbg.fillRect(0, 0, PWIDTH, PHEIGHT);
        Graphics2D g = (Graphics2D) dbg;
        g.setColor(Color.BLACK);

        mediator.renderUsing(g);
    }

    public void pauseGame() {
        //To change body of created methods use File | Settings | File Templates.
    }

    public void resumeGame() {
        //To change body of created methods use File | Settings | File Templates.
    }

}
