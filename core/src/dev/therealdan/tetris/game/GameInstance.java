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
        Tetrimino fallingTetrimino = getFallingTetrimino();
        for (Square fallingSquare : fallingTetrimino.getSquares()) {
            if (fallingTetrimino.getY() + fallingSquare.getY() <= 0) return false;
            for (Tetrimino otherTetrimino : tetriminos) {
                if (otherTetrimino == fallingTetrimino) continue;
                for (Square otherSquare : otherTetrimino.getSquares()) {
                    if (otherTetrimino.getX() + otherSquare.getX() == fallingTetrimino.getX() + fallingSquare.getX() && otherTetrimino.getY() + otherSquare.getY() == fallingTetrimino.getY() + fallingSquare.getY() - 1) return false;
                }
            }
            for (Square otherSquare : squares) {
                if (otherSquare.getX() == fallingTetrimino.getX() + fallingSquare.getX() && otherSquare.getY() == fallingTetrimino.getY() + fallingSquare.getY() - 1) return false;
            }
        }

        return true;
    }

    public Tetrimino getFallingTetrimino() {
        if (fallingTetrimino == null) fallingTetrimino = new Tetrimino(nextType, nextType.getSpawnXOffset(playField.getCellsWide()), playField.getCellsHigh());
        return fallingTetrimino;
    }
}