package main.backend;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Displayable {
    protected ImageView image;

    protected Displayable(double x, double y, int maxsize, String imagePath) {
        this.image = new ImageView(new Image(imagePath));
        this.image.setPreserveRatio(true);
        this.image.setFitWidth(maxsize);
        this.image.setFitHeight(maxsize);
        setImagePosition(x, y);
    }

    protected Displayable(double x, double y, double length, 
                          double height, String imagePath) {
        this.image = new ImageView(new Image(imagePath));
        this.image.setPreserveRatio(false);
        this.image.setFitWidth(length);
        this.image.setFitHeight(height);
        setImagePosition(x, y);
    }

    protected void setImage(String imagePath) {
        double x = image.getTranslateX();
        double y = image.getTranslateY();
        double maxsize = Math.max(image.getFitHeight(), image.getFitWidth());
        this.image = new ImageView(new Image(imagePath));
        this.image.setPreserveRatio(true);
        this.image.setFitHeight(maxsize);
        this.image.setFitWidth(maxsize);
        setImagePosition(x, y);
    }
    
    protected void setImagePosition(double x, double y) {
        this.image.setTranslateX(x - Controller.getLength() / 2);
        this.image.setTranslateY(y - Controller.getHeight() / 2);
    }

    public ArrayList<ImageView> getImage() {
        ArrayList<ImageView> images = new ArrayList<>();
        images.add(this.image);
        return images;
    }
}
