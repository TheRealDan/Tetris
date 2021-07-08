package dev.therealdan.tetris.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import dev.therealdan.tetris.game.GameInstance;
import dev.therealdan.tetris.game.Square;
import dev.therealdan.tetris.game.Tetrimino;
import dev.therealdan.tetris.main.TetrisApp;

import java.text.DecimalFormat;

public class GameScreen implements Screen, InputProcessor {

    private final TetrisApp app;
    private DecimalFormat format = new DecimalFormat("###,###");

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

        instance.playField.render(app.shapeRenderer);
        for (Tetrimino tetrimino : instance.tetriminos)
            tetrimino.render(app.shapeRenderer, instance.playField);
        for (Square square : instance.squares)
            square.render(app.shapeRenderer, instance.playField);
        instance.playField.renderPrediction(app.shapeRenderer, instance, instance.getFallingTetrimino());
        instance.getFallingTetrimino().render(app.shapeRenderer, instance.playField);

        float queueX = instance.playField.getX(0) / 2f;
        float queueY = instance.playField.getY(instance.playField.getCellsHigh()) - instance.playField.getCellSize();
        for (Tetrimino.Type type : instance.tetriminoQueue)
            new Tetrimino(type, 0, 0).render(app.shapeRenderer, queueX, queueY -= instance.playField.getCellSize() * 4, instance.playField.getCellSize());

        app.shapeRenderer.end();

        float scoreX = instance.playField.getX(instance.playField.getCellsWide()) + queueX;
        float scoreY = instance.playField.getY(instance.playField.getCellsHigh()) - instance.playField.getCellSize();
        app.batch.begin();
        app.font.center(app.batch, "Next", queueX, scoreY, 24);
        app.font.center(app.batch, "Score: " + format.format(instance.score), scoreX, scoreY, 24);
        app.batch.end();

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
        if (Input.Keys.LEFT == keycode || Input.Keys.A == keycode) {
            if (instance.getFallingTetrimino().canMoveLeft(instance))
                instance.getFallingTetrimino().moveLeft();
            return true;
        }
        if (Input.Keys.RIGHT == keycode || Input.Keys.D == keycode) {
            if (instance.getFallingTetrimino().canMoveRight(instance))
                instance.getFallingTetrimino().moveRight();
            return true;
        }
        if (Input.Keys.DOWN == keycode || Input.Keys.S == keycode) {
            if (instance.getFallingTetrimino().canMoveDown(instance))
                instance.getFallingTetrimino().moveDown();
            return true;
        }
        if (Input.Keys.R == keycode) {
            instance.getFallingTetrimino().rotate(instance);
            return true;
        }

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
