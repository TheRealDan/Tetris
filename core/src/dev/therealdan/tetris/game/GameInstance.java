package dev.therealdan.tetris.game;

import java.util.HashSet;

public class GameInstance {

    public PlayField playField;
    public HashSet<Tetrimino> tetriminos = new HashSet<>();
    public HashSet<Square> squares = new HashSet<>();

    private Tetrimino fallingTetrimino;
    public Tetrimino.Type nextType = Tetrimino.Type.T;

    private long fallInterval = 1000;
    private long lastPieceFall = System.currentTimeMillis();

    public GameInstance() {
        playField = new PlayField(10, 20);
    }

    public void loop(float delta) {
        handleFallingPieces();
    }

    private void handleFallingPieces() {
        if (System.currentTimeMillis() - lastPieceFall < fallInterval) return;
        lastPieceFall = System.currentTimeMillis();

        if (canFallFurther()) {
            getFallingTetrimino().moveDown();
        } else {
            tetriminos.add(getFallingTetrimino());
            fallingTetrimino = null;
        }
    }

    private boolean canFallFurther() {
        return true;
    }

    public Tetrimino getFallingTetrimino() {
        if (fallingTetrimino == null) fallingTetrimino = new Tetrimino(nextType, nextType.getSpawnXOffset(playField.getCellsWide()), playField.getCellsHigh());
        return fallingTetrimino;
    }
}