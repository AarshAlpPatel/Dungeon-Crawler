package main.backend.collidables;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.backend.Controller;
import main.backend.Displayable;

public abstract class Collidable extends Displayable {
    
    protected Rectangle box;
    private double offsetX;
    private double offsetY;

    /**
     * used exclusively to create walls
     * @param x
     * @param y
     * @param length
     * @param height
     * @param imagePath
     * @param translateX
     * @param translateY
     */
    protected Collidable(double x, double y, double length, double height,
                         String imagePath, double translateX, double translateY) {
        super(x, y, length, height, imagePath);
        box = new Rectangle(x, y, length, height);
        box.setTranslateX(x - translateX);
        box.setTranslateY(y - translateY);
    }

    protected Collidable(double x, double y, int maxsize,
                         String imagePath) {
        super(x, y, maxsize, imagePath);
        double imageWidth = image.getBoundsInParent().getWidth();
        double imageHeight = image.getBoundsInParent().getHeight();
        offsetX = imageWidth/2;
        offsetY = imageHeight/2;
        box = new Rectangle(x - offsetX, y-offsetY, imageWidth, imageHeight);
        box.setTranslateX(x - Controller.getLength() / 2);
        box.setTranslateY(y - Controller.getHeight() / 2);
        this.box.setFill(Color.GREEN);
    }

    protected Rectangle getWall() {
        return box;
    }

    protected void setPosition(Point2D p) {
        this.box.setX(p.getX() - offsetX);
        this.box.setY(p.getY() - offsetY);
        this.setImagePosition(p.getX(), p.getY());
    }

    protected void setRotate(double angle) {
        this.box.setRotate(angle);
        this.image.setRotate(angle);
    }

    protected boolean collidesWith(Collidable obj) {
        return this.box.intersects(obj.box.getBoundsInParent());
    }
}
