package dev.therealdan.tetris.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Square {

    private Color color;
    private int cellX;
    private int cellY;

    public Square(Color color, int cellX, int cellY) {
        this.color = color;
        this.cellX = cellX;
        this.cellY = cellY;
    }

    public void render(ShapeRenderer shapeRenderer, PlayField playField) {
        shapeRenderer.setColor(getColor());
        shapeRenderer.rect(playField.getX(getX()), playField.getY(getY()), playField.getCellSize(), playField.getCellSize());
    }

    public Color getColor() {
        return color;
    }

    public int getX() {
        return cellX;
    }

    public int getY() {
        return cellY;
    }
}