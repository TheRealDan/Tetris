package dev.therealdan.tetris.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import dev.therealdan.tetris.TetrisApp;
import dev.therealdan.tetris.game.GameInstance;
import dev.therealdan.tetris.game.Square;
import dev.therealdan.tetris.game.Tetriminos;

public class GameScreen implements Screen, InputProcessor {

    final TetrisApp app;

    private GameInstance instance;

    public GameScreen(TetrisApp app) {
        this.app = app;

        instance = new GameInstance();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        instance.loop(delta);

        app.shapeRenderer.setAutoShapeType(true);
        app.shapeRenderer.begin();
        app.shapeRenderer.setColor(Color.WHITE);
        instance.playField.render(app.shapeRenderer);
        for (Tetriminos tetriminos : instance.tetriminos)
            tetriminos.render(app.shapeRenderer, instance.playField);
        for (Square square : instance.squares)
            square.render(app.shapeRenderer, instance.playField);
        instance.fallingTetrimino.render(app.shapeRenderer, instance.playField);
        app.shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        instance.playField.resize(width, height);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
