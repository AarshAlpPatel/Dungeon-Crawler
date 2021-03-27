package main.backend.rooms;

import main.backend.Controller;

public class EndRoom extends Room {
    public EndRoom() {
        super("empty");
    }
    @Override
    public void enter() {
        setStatusTrue();
        Controller.endGame();
    }
}
