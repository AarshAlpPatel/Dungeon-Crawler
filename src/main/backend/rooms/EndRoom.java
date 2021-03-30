package main.backend.rooms;

import main.backend.Controller;

public class EndRoom extends Room {
    public EndRoom() {
        super("empty");
    }
    @Override
    public void enter() {
        super.enter();
        Controller.winGame();
    }
}
