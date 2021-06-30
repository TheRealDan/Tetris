package dev.therealdan.tetris.game;

import java.util.HashSet;
import java.util.Random;

public class GameInstance {

    private Random random = new Random();

    public PlayField playField;
    public HashSet<Tetrimino> tetriminos = new HashSet<>();
    public HashSet<Square> squares = new HashSet<>();

    private Tetrimino fallingTetrimino;

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

        if (getFallingTetrimino().canMoveDown(this)) {
            getFallingTetrimino().moveDown();
        } else {
            tetriminos.add(getFallingTetrimino());
            fallingTetrimino = null;
        }
    }

    public boolean isCellOccupied(int cellX, int cellY) {
        for (Tetrimino tetrimino : tetriminos)
            for (Square square : tetrimino.getSquares())
                if (tetrimino.getX() + square.getX() == cellX && tetrimino.getY() + square.getY() == cellY) return true;

        for (Square square : squares)
            if (square.getX() == cellX && square.getY() == cellY) return true;

        return false;
    }

    public Tetrimino getFallingTetrimino() {
        if (fallingTetrimino == null) {
            Tetrimino.Type nextType = getNextType();
            fallingTetrimino = new Tetrimino(nextType, nextType.getSpawnXOffset(playField.getCellsWide()), playField.getCellsHigh());
        }
        return fallingTetrimino;
    }

    private Tetrimino.Type getNextType() {
        return Tetrimino.Type.values()[random.nextInt(Tetrimino.Type.values().length - 1)];
    }
}