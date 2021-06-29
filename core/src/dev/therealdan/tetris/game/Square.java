package dev.therealdan.tetris.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Square {

    private Color color;
    private float x;
    private float y;

    public Square(Color color, float x, float y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public void render(ShapeRenderer shapeRenderer, float size) {
        shapeRenderer.setColor(getColor());
        shapeRenderer.rect(getX(), getY(), size, size);
    }

    public Color getColor() {
        return color;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}