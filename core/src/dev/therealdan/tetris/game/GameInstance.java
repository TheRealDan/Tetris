package dev.therealdan.tetris.game;

public class GameInstance {

    public PlayField playField;

    public GameInstance() {
        playField = new PlayField(10, 20);
    }
}