package dev.therealdan.tetris.game;

import java.util.HashSet;

public class GameInstance {

    public PlayField playField;
    public HashSet<Tetriminos> tetriminos = new HashSet<>();
    public HashSet<Square> squares = new HashSet<>();

    public GameInstance() {
        playField = new PlayField(10, 20);
    }
}