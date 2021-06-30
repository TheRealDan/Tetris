package dev.therealdan.tetris.game;

import java.util.HashSet;

public class GameInstance {

    public PlayField playField;
    public HashSet<Tetriminos> tetriminos = new HashSet<>();
    public HashSet<Square> squares = new HashSet<>();

    public Tetriminos fallingTetrimino;
    public Tetriminos.Type nextType = Tetriminos.Type.T;

    private long fallInterval = 1000;
    private long lastPieceFall = System.currentTimeMillis();

    public GameInstance() {
        playField = new PlayField(10, 20);
    }

    public void loop(float delta) {
        handleFallingPieces();
    }

    private void handleFallingPieces() {
        if (fallingTetrimino == null) fallingTetrimino = new Tetriminos(nextType, (int) playField.getCellsWide() / 2 - 2, (int) playField.getCellsHigh());

        if (System.currentTimeMillis() - lastPieceFall < fallInterval) return;
        lastPieceFall = System.currentTimeMillis();

        fallingTetrimino.moveDown();
    }
}