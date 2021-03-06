package dev.therealdan.tetris.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.*;

public class GameInstance {

    private Random random = new Random();

    public PlayField playField;
    public HashSet<Tetrimino> tetriminos = new HashSet<>();
    public HashSet<Square> squares = new HashSet<>();
    public Deque<Tetrimino.Type> tetriminoQueue = new ArrayDeque<>();
    public int score = 0;

    private long fallInterval = 1000;
    private long moveDownInterval = 200;

    private Tetrimino fallingTetrimino;
    private long lock = System.currentTimeMillis();
    private long moveDown = System.currentTimeMillis();
    public boolean gameover = false;
    private int linesCleared = 0;
    private int tetris = 0;

    public GameInstance() {
        playField = new PlayField(10, 20);
    }

    public void loop(float delta) {
        if (gameover) return;

        fillQueue();
        handleFallingPieces();
        checkClearLines();
        checkStackOverflow();

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            if (System.currentTimeMillis() - moveDown < moveDownInterval) return;
            moveDown = System.currentTimeMillis();
            if (getFallingTetrimino().canMoveDown(this))
                getFallingTetrimino().moveDown();
        }
    }

    private void fillQueue() {
        while (tetriminoQueue.size() < 4)
            tetriminoQueue.add(Tetrimino.Type.values()[random.nextInt(Tetrimino.Type.values().length)]);
    }

    private void checkStackOverflow() {
        for (Tetrimino tetrimino : tetriminos) {
            for (Square square : tetrimino.getSquares()) {
                if (tetrimino.getY() + square.getY() >= playField.getCellsHigh()) {
                    gameover = true;
                    return;
                }
            }
        }

        for (Square square : squares) {
            if (square.getY() >= playField.getCellsHigh()) {
                gameover = true;
                return;
            }
        }
    }

    private void handleFallingPieces() {
        if (System.currentTimeMillis() - lock < fallInterval) return;
        lock = System.currentTimeMillis();

        if (getFallingTetrimino().canMoveDown(this)) {
            getFallingTetrimino().moveDown();
        } else {
            tetriminos.add(getFallingTetrimino());
            fallingTetrimino = null;
        }
    }

    public void lock() {
        lock = System.currentTimeMillis() - fallInterval * 2;
        handleFallingPieces();
    }

    private void checkClearLines() {
        next:
        for (int y = 0; y < playField.getCellsHigh(); y++) {
            for (int x = 0; x < playField.getCellsWide(); x++) {
                if (!isCellOccupied(x, y))
                    continue next;
            }
            linesCleared++;
            score += 100;
            if (linesCleared >= 4) {
                linesCleared = 0;
                score += tetris > 0 ? 800 : 400;
                tetris++;
            }
            clearLine(y);
            return;
        }

        if (linesCleared > 0) tetris = 0;
        linesCleared = 0;
    }

    private void clearLine(int y) {
        for (int x = 0; x < playField.getCellsWide(); x++) {
            Tetrimino tetrimino = getTetrimino(x, y);
            if (tetrimino == null) continue;
            tetriminos.remove(tetrimino);
            for (Square square : tetrimino.getSquares()) {
                if (tetrimino.getY() + square.getY() == y) continue;
                squares.add(new Square(tetrimino.getType().getColor(), tetrimino.getX() + square.getX(), tetrimino.getY() + square.getY()));
            }
        }
        for (Square square : new ArrayList<>(squares))
            if (square.getY() == y)
                squares.remove(square);

        for (Tetrimino tetrimino : tetriminos)
            if (tetrimino.getY() > y)
                tetrimino.moveDown();

        for (Square square : squares)
            if (square.getY() > y)
                square.moveDown();
    }

    public boolean isCellOccupied(int cellX, int cellY) {
        for (Tetrimino tetrimino : tetriminos)
            for (Square square : tetrimino.getSquares())
                if (tetrimino.getX() + square.getX() == cellX && tetrimino.getY() + square.getY() == cellY) return true;

        for (Square square : squares)
            if (square.getX() == cellX && square.getY() == cellY) return true;

        return false;
    }

    public Tetrimino getTetrimino(int cellX, int cellY) {
        for (Tetrimino tetrimino : tetriminos)
            for (Square square : tetrimino.getSquares())
                if (tetrimino.getX() + square.getX() == cellX && tetrimino.getY() + square.getY() == cellY)
                    return tetrimino;

        return null;
    }

    public Tetrimino getFallingTetrimino() {
        if (fallingTetrimino == null) {
            Tetrimino.Type nextType = tetriminoQueue.poll();
            fallingTetrimino = new Tetrimino(nextType, nextType.getSpawnXOffset(playField.getCellsWide()), playField.getCellsHigh());
        }
        return fallingTetrimino;
    }
}