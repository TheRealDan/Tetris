package dev.therealdan.tetris.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class Tetrimino {

    private Type type;
    private int cellX;
    private int cellY;
    private List<Square> squares = new ArrayList<>();

    public Tetrimino(Type type, int cellX, int cellY) {
        this.type = type;
        this.cellX = cellX;
        this.cellY = cellY;
        for (int[] offsets : getType().getOffsets()) {
            squares.add(new Square(type.getColor(), offsets[0], offsets[1]));
        }
    }

    public void render(ShapeRenderer shapeRenderer, PlayField playField) {
        float size = playField.getCellSize();
        for (Square square : squares) {
            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(getType().getColor());
            shapeRenderer.rect(playField.getX(cellX + square.getX()), playField.getY(cellY + square.getY()), size, size);
            shapeRenderer.set(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.rect(playField.getX(cellX + square.getX()), playField.getY(cellY + square.getY()), size, size);
        }
    }

    public void moveDown() {
        cellY--;
    }

    public void moveLeft() {
        cellX--;
    }

    public void moveRight() {
        cellX++;
    }

    public void rotate() {
        List<Square> squares = new ArrayList<>();
        for (Square square : getSquares())
            squares.add(new Square(square.getColor(), square.getY(), -square.getX()));
        this.squares = squares;
    }

    public boolean canMoveDown(GameInstance gameInstance) {
        for (Square square : getSquares()) {
            if (getY() + square.getY() <= 0) return false;
            if (gameInstance.isCellOccupied(getX() + square.getX(), getY() + square.getY() - 1)) return false;
        }

        return true;
    }

    public boolean canMoveLeft(GameInstance gameInstance) {
        for (Square square : getSquares()) {
            if (getX() + square.getX() <= 0) return false;
            if (gameInstance.isCellOccupied(getX() + square.getX() - 1, getY() + square.getY())) return false;
        }

        return true;
    }

    public boolean canMoveRight(GameInstance gameInstance) {
        for (Square square : getSquares()) {
            if (getX() + square.getX() >= gameInstance.playField.getCellsWide() - 1) return false;
            if (gameInstance.isCellOccupied(getX() + square.getX() + 1, getY() + square.getY())) return false;
        }

        return true;
    }

    public Type getType() {
        return type;
    }

    public int getX() {
        return cellX;
    }

    public int getY() {
        return cellY;
    }

    public List<Square> getSquares() {
        return squares;
    }

    public enum Type {
        I, O, T, J, L, S, Z;

        public int getSpawnXOffset(int cellsWidth) {
            switch (this) {
                case I:
                    return cellsWidth / 2 - 2;
                case O:
                case T:
                case J:
                case L:
                case S:
                case Z:
                    return cellsWidth / 2 - 1;
            }
            return 0;
        }

        public int[][] getOffsets() {
            switch (this) {
                case I:
                    return new int[][]{{-2, 0}, {-1, 0}, {0, 0}, {1, 0}};
                case O:
                    return new int[][]{{-1, 0}, {0, 0}, {-1, -1}, {0, -1}};
                case T:
                    return new int[][]{{0, 0}, {-1, 0}, {1, 0}, {0, 1}};
                case J:
                    return new int[][]{{-1, 1}, {-1, 0}, {0, 0}, {1, 0}};
                case L:
                    return new int[][]{{-1, 0}, {0, 0}, {1, 0}, {1, 1}};
                case S:
                    return new int[][]{{-1, 0}, {0, 0}, {0, 1}, {1, 1}};
                case Z:
                    return new int[][]{{-1, 1}, {0, 1}, {0, 0}, {1, 0}};
            }
            return null;
        }

        public Color getColor() {
            switch (this) {
                case I:
                    return Color.CYAN;
                case O:
                    return Color.YELLOW;
                case T:
                    return Color.MAGENTA;
                case J:
                    return Color.BLUE;
                case L:
                    return Color.ORANGE;
                case S:
                    return Color.GREEN;
                case Z:
                    return Color.RED;
            }
            return null;
        }
    }
}