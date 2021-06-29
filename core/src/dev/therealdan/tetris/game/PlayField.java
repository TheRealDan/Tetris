package dev.therealdan.tetris.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PlayField {

    private float cellsWide;
    private float cellsHigh;

    private float startY;
    private float cellSize;

    public PlayField(int cellsWide, int cellsHigh) {
        this.cellsWide = cellsWide;
        this.cellsHigh = cellsHigh;
    }

    public void render(ShapeRenderer shapeRenderer) {
        for (int x = 0; x < cellsWide; x++) {
            for (int y = 0; y < cellsHigh; y++) {
                shapeRenderer.rect(Gdx.graphics.getWidth() / 3f + x * cellSize, startY + y * cellSize, cellSize, cellSize);
            }
        }
    }

    public void resize(float width, float height) {
        cellSize = Math.min(height / cellsHigh, width / 3f / cellsWide);
        startY = (height - cellSize * cellsHigh) / 2f;
    }

    public float getCellSize() {
        return cellSize;
    }
}