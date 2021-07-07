package dev.therealdan.tetris.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.therealdan.tetris.screens.GameScreen;

public class TetrisApp extends Game {

    public FontManager font;

    public ShapeRenderer shapeRenderer;
    public SpriteBatch batch;

    @Override
    public void create() {
        font = new FontManager();

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        font.dispose();
        shapeRenderer.dispose();
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {
        shapeRenderer.dispose();
        batch.dispose();

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        getScreen().resize(width, height);
    }
}