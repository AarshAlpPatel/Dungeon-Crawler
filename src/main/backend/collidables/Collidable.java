package main.backend.collidables;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.*;
import main.backend.Controller;

public abstract class Collidable {
    protected ImageView image;
    protected Rotate rotation = new Rotate(0);
    protected Translate offset;

    private void setPivot(Point2D position) {
        rotation.setPivotX(position.getX());
        rotation.setPivotY(position.getY());
    }
    /**
     * used exclusively to create walls
     * @param x initial x position of image
     * @param y initial y position of image
     * @param length length of image
     * @param height height of image
     * @param imagePath file path to image
     * @param translateX how much to move horizontally
     * @param translateY how much to move vertically
     */
    protected Collidable(double x, double y, double length, double height,
                         String imagePath, double translateX, double translateY) {
        this.image = new ImageView(new Image("/main/design/images/" + imagePath));
        this.image.setPreserveRatio(false);
        this.image.setFitWidth(length);
        this.image.setFitHeight(height);
        image.setTranslateX(x - translateX);
        image.setTranslateY(y - translateY);
    }

    /**
     * used exclusively for sprites
     * @param x
     * @param y
     * @param maxsize
     * @param imagePath
     */
    protected Collidable(double x, double y, int maxsize, String imagePath,
                         double translateX, double translateY) {
        this.image = new ImageView(new Image("/main/design/images/" + imagePath));
        this.image.setPreserveRatio(true);
        this.image.setFitWidth(maxsize);
        //this.image.setFitHeight(maxsize);
        setImagePosition(new Point2D(x, y));

        double width = this.image.getBoundsInParent().getWidth();
        double height = this.image.getBoundsInParent().getHeight();
        System.out.print("WIDTH: " + width + ", HEIGHT: " + height + ", ");
        offset = new Translate((translateX == 0) ? 0 : width/translateX,
                               (translateY == 0) ? 0 : height/translateY);
        this.image.getTransforms().addAll(rotation, offset);
    }

    public void setImage(String imagePath) {
        double x = image.getTranslateX();
        double y = image.getTranslateY();
        double maxsize = Math.max(image.getFitHeight(), image.getFitWidth());
        this.image = new ImageView(new Image("/main/design/images/" + imagePath));
        this.image.setPreserveRatio(true);
        this.image.setFitHeight(maxsize);
        this.image.setFitWidth(maxsize);
        setImagePosition(new Point2D(x, y));
    }
    
    protected void setImagePosition(Point2D position) {
        this.image.setTranslateX(position.getX() - Controller.getLength() / 2);
        this.image.setTranslateY(position.getY() - Controller.getHeight() / 2);
        setPivot(position);
    }

    public ArrayList<ImageView> getImage() {
        ArrayList<ImageView> images = new ArrayList<>();
        images.add(this.image);
        return images;
    }

    protected void setRotate(double angle) {
        this.image.setRotate(angle);
    }

    protected boolean collidesWith(Collidable obj) {
        return this.image.getBoundsInParent().intersects(obj.image.getBoundsInParent());
    }
}
