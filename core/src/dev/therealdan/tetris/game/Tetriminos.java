package dev.therealdan.tetris.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class Tetriminos {

    private Type type;
    private int cellX;
    private int cellY;
    private List<Square> squares = new ArrayList<>();

    public Tetriminos(Type type, int cellX, int cellY) {
        this.type = type;
        this.cellX = cellX;
        this.cellY = cellY;
        for (int[] offsets : getType().getOffsets()) {
            squares.add(new Square(type.getColor(), offsets[0], offsets[1]));
        }
    }

    public void render(ShapeRenderer shapeRenderer, PlayField playField) {
        float size = playField.getCellSize();
        shapeRenderer.setColor(getType().getColor());
        for (Square square : squares)
            shapeRenderer.rect(playField.getX(cellX + square.getX()), playField.getY(cellY + square.getY()), size, size);
    }

    public void moveDown() {
        cellY -= 1;
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

        public int[][] getOffsets() {
            switch (this) {
                case I:
                    return new int[][]{{0, 0}, {1, 0}, {2, 0}, {3, 0}};
                case O:
                    return new int[][]{{0, 0}, {1, 0}, {0, -1}, {1, -1}};
                case T:
                    return new int[][]{{0, 0}, {1, 0}, {2, 0}, {1, -1}};
                case J:
                    return new int[][]{{1, 0}, {1, -1}, {1, -2}, {0, -2}};
                case L:
                    return new int[][]{{0, 0}, {0, -1}, {0, -2}, {1, -2}};
                case S:
                    return new int[][]{{1, 0}, {2, 0}, {0, -1}, {1, -1}};
                case Z:
                    return new int[][]{{0, 0}, {1, 0}, {1, -1}, {2, -1}};
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