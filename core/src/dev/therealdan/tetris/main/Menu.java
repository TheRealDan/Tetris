package dev.therealdan.tetris.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Menu {

    private float x;
    private float y;
    private float width;
    private float height;
    private float cellSize;

    private int cellsWide;
    private int cellsHigh;
    private float actualWidth;
    private float actualHeight;

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(x - cellSize, y - cellSize, actualWidth, actualHeight);

        for (int xCell = -1; xCell <= cellsWide; xCell++) {
            for (int yCell = -1; yCell <= cellsHigh; yCell++) {
                if (xCell == -1 || xCell == cellsWide || yCell == -1 || yCell == cellsHigh) {
                    shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
                    shapeRenderer.setColor(Color.GRAY);
                    shapeRenderer.rect(x + xCell * cellSize, y + yCell * cellSize, cellSize, cellSize);
                    shapeRenderer.set(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.setColor(Color.DARK_GRAY);
                    shapeRenderer.rect(x + xCell * cellSize, y + yCell * cellSize, cellSize, cellSize);
                }
            }
        }
    }

    public void setPosition(float x, float y) {
        setPosition(x, y, false);
    }

    public void setPosition(float x, float y, boolean center) {
        this.x = x;
        this.y = y;

        if (center) {
            this.x = x - actualWidth / 2f + cellSize;
            this.y = y - actualHeight / 2f + cellSize;
        }
    }

    public void resize(float width, float height, float cellSize) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;

        this.actualWidth = (width + cellSize * 2f) + (cellSize - ((width + cellSize * 2f) % cellSize)) + 1;
        this.actualHeight = (height + cellSize * 2f) + (cellSize - ((height + cellSize * 2f) % cellSize)) + 1;
        this.cellsWide = (int) Math.floor((this.actualWidth - cellSize * 2f) / cellSize);
        this.cellsHigh = (int) Math.floor((this.actualHeight - cellSize * 2f) / cellSize);
    }

    public float getCenterX() {
        return getX() + getWidth() / 2f;
    }

    public float getCenterY() {
        return getY() + getHeight() / 2f;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return actualWidth - cellSize * 2f;
    }

    public float getHeight() {
        return actualHeight - cellSize * 2f;
    }
}