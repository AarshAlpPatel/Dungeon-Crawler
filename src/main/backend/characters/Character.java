package main.backend.characters;

import java.io.File;

public class Character {
    private String name;
    private String imagePath;

    public Character(String name) {
        this.name = name;
        imagePath = null;
    }

    public Character(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("Character: %s", name);
    }
}
