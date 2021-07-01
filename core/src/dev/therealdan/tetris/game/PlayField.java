package dev.therealdan.tetris.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PlayField {

    private int cellsWide;
    private int cellsHigh;

    private float startX;
    private float startY;
    private float cellSize;

    public PlayField(int cellsWide, int cellsHigh) {
        this.cellsWide = cellsWide;
        this.cellsHigh = cellsHigh;
    }

    public void render(ShapeRenderer shapeRenderer) {
        for (int x = 0; x < cellsWide; x++) {
            for (int y = 0; y < cellsHigh; y++) {
                shapeRenderer.rect(startX + x * getCellSize(), startY + y * getCellSize(), getCellSize(), getCellSize());
            }
        }
    }

    public void resize(float width, float height) {
        cellSize = Math.min(height / cellsHigh, width / 3f / cellsWide);
        startX = width / 2f - cellsWide * cellSize / 2f;
        startY = (height - getCellSize() * cellsHigh) / 2f;
    }

    public float getX(int cellX) {
        return startX + cellX * getCellSize();
    }

    public float getY(int cellY) {
        return startY + cellY * getCellSize();
    }

    public float getCellSize() {
        return cellSize;
    }

    public int getCellsWide() {
        return cellsWide;
    }

    public int getCellsHigh() {
        return cellsHigh;
    }
}