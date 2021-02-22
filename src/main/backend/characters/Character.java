package main.backend.characters;

import java.io.File;

public class Character {
    private String name;
    private File imagePath;

    public Character(String name, File imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public void setImagePath(File imagePath) {
        this.imagePath = imagePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getImagePath() {
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
