package com.mlj.steeringdemo.ui;

import static com.mlj.steeringdemo.geom.Geometry.createFeelersFor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

import com.mlj.steeringdemo.geom.Rotation;
import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.unit.Boundary;
import com.mlj.steeringdemo.unit.Entity;
import com.mlj.steeringdemo.unit.Obstacle;
import com.mlj.steeringdemo.unit.Vehicle;
import com.mlj.steeringdemo.unit.Wall;

public class Renderer {

    public static enum DisplaySize {
        ZOOM {
            @Override
            public Vector getLeft(int x, int y) {
                return new Vector(x - 7, y + 7);
            }

            @Override
            public Vector getRight(int x, int y) {
                return new Vector(x + 7, y + 7);
            }

            @Override
            public Vector getTip(int x, int y) {
                return new Vector(x, y - 15);
            }
        },
        UN_ZOOM {
            @Override
            public Vector getLeft(int x, int y) {
                return new Vector(x - 5, y + 5);
            }

            @Override
            public Vector getRight(int x, int y) {
                return new Vector(x + 5, y + 5);
            }

            @Override
            public Vector getTip(int x, int y) {
                return new Vector(x, y - 10);
            }
        };

        public abstract Vector getTip(int x, int y);
        public abstract Vector getLeft(int x, int y);
        public abstract Vector getRight(int x, int y);
    }

    private final Graphics2D graphics;
    private final DisplaySize displaySize;
    private boolean showWallNormals;
    private boolean showFeelers;

    public Renderer(DisplaySize displaySize, Graphics2D graphics) {
        this.displaySize = displaySize;
        this.graphics = graphics;
    }

    public Renderer(DisplaySize displaySize, Graphics2D graphics, boolean showWallNormals, boolean showFeelers) {
        this(displaySize, graphics);
        this.showWallNormals = showWallNormals;
        this.showFeelers = showFeelers;
    }

    public void render(Vehicle vehicle, Color color) {
        Color originalColor = graphics.getColor();
        graphics.setColor(color);
        render(vehicle);
        graphics.setColor(originalColor);
    }

    public void render(Vehicle vehicle) {
        int x = (int) vehicle.X();
        int y = (int) vehicle.Y();

        Vector tip = displaySize.getTip(x, y);
        Vector left = displaySize.getLeft(x, y);
        Vector right = displaySize.getRight(x, y);

        int[] xPos = { (int) tip.X(), (int) left.X(), (int) right.X() };
        int[] yPos = { (int) tip.Y(), (int) left.Y(), (int) right.Y() };

        AffineTransform orig = graphics.getTransform();
        Vector up = new Vector(0, -1);
        Vector heading = vehicle.getSmoothedHeading();
        double angle = up.angle(heading);
        Rotation rotation = up.rotationTo(heading);

        AffineTransform rot = AffineTransform.getRotateInstance(rotation.rotate(angle), x, y);
        graphics.transform(rot);
        graphics.fillPolygon(xPos, yPos, 3);
        graphics.setTransform(orig);

        if (showFeelers) {
            Vector[] feelers = createFeelersFor(vehicle);
            for (Vector feeler : feelers) {
                graphics.drawLine(x, y, (int) feeler.X(), (int) feeler.Y());
            }
        }

    }

    public void render(Vector selectedPosition) {
        Color originalColor = graphics.getColor();
        graphics.setColor(Color.RED);
        graphics.fillOval((int) selectedPosition.X() - 5, (int) selectedPosition.Y() - 5, 10, 10);
        graphics.setColor(originalColor);
    }

    public void render(Wall wall) {
        Stroke stroke = graphics.getStroke();
        graphics.setStroke(new BasicStroke(10));
        graphics.drawLine((int) wall.from().X(), (int) wall.from().Y(), (int) wall.to().X(), (int) wall.to().Y());

        if (showWallNormals) {
            graphics.setStroke(new BasicStroke(3));
            Vector centre = wall.centre();
            Vector normal = wall.normal();
            int x = (int) centre.X();
            int y = (int) centre.Y();
            int toX = x + (int) normal.X() * 15;
            int toY = y + (int) normal.Y() * 15;
            graphics.drawLine(x, y, toX, toY);
        }

        graphics.setStroke(stroke);
    }

    public void render(Obstacle obstacle) {
        graphics.drawOval((int)(obstacle.position().X() - obstacle.boundingRadius()),
                (int)(obstacle.position().Y() - obstacle.boundingRadius()),
                (int) (obstacle.boundingRadius() * 2), (int) (obstacle.boundingRadius() * 2));
    }

    public void render(Boundary boundary) {
        Color originalColor = graphics.getColor();
        graphics.setColor(Color.GRAY);
        Stroke originalStroke = graphics.getStroke();
        float[] dashPattern = { 10, 10, 10, 10 };
        graphics.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
        graphics.drawOval((int) (boundary.position().X() - boundary.boundingRadius()),
                (int) (boundary.position().Y() - boundary.boundingRadius()),
                (int) (boundary.boundingRadius() * 2), (int) (boundary.boundingRadius() * 2));
        graphics.setColor(originalColor);
        graphics.setStroke(originalStroke);
    }

    public void renderPointOfInterest(Entity entity) {
        Color originalColor = graphics.getColor();
        graphics.setColor(Color.RED);
        graphics.fillOval((int)(entity.position().X() - entity.boundingRadius()),
                (int)(entity.position().Y() - entity.boundingRadius()),
                (int) (entity.boundingRadius() * 2), (int) (entity.boundingRadius() * 2));
        graphics.setColor(originalColor);

    }
}
