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
import dev.therealdan.tetris.main.Menu;
import dev.therealdan.tetris.main.TetrisApp;
import dev.therealdan.tetris.main.scoreapi.Score;

public class GameScreen implements Screen, InputProcessor {

    private final TetrisApp app;

    private GameInstance instance;

    private Menu menu;
    private Menu nextUI;
    private Menu scoreUI;
    private Menu highsocresUI;

    private int highscoresFontSize = 12;

    public GameScreen(TetrisApp app) {
        this.app = app;

        instance = new GameInstance();
        menu = new Menu();
        nextUI = new Menu();
        scoreUI = new Menu();
        highsocresUI = new Menu();

        menu.setMinimumSize(350, 260);
        menu.setMaximumSize(350, 260);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        instance.loop(delta);

        app.shapeRenderer.setAutoShapeType(true);
        app.shapeRenderer.begin();

        nextUI.render(app.shapeRenderer);
        scoreUI.render(app.shapeRenderer);
        highsocresUI.render(app.shapeRenderer);

        instance.playField.render(app.shapeRenderer);
        for (Tetrimino tetrimino : instance.tetriminos)
            tetrimino.render(app.shapeRenderer, instance.playField);
        for (Square square : instance.squares)
            square.render(app.shapeRenderer, instance.playField);
        instance.playField.renderPrediction(app.shapeRenderer, instance, instance.getFallingTetrimino());
        instance.getFallingTetrimino().render(app.shapeRenderer, instance.playField);

        float scoreX = scoreUI.getCenterX();
        float scoreY = scoreUI.getCenterY();

        float queueX = nextUI.getCenterX();
        float queueY = scoreY;
        for (Tetrimino.Type type : instance.tetriminoQueue)
            new Tetrimino(type, 0, 0).render(app.shapeRenderer, queueX, queueY -= instance.playField.getCellSize() * 4 * 0.8f, instance.playField.getCellSize() * 0.8f);

        app.shapeRenderer.end();

        app.batch.begin();

        String currentScore = "Score: " + Score.format(instance.score);
        int scoreAndNextFontSize = app.font.calculateFontSize(app.batch, currentScore, scoreUI.getWidth() - 20);
        app.font.center(app.batch, currentScore, scoreX, scoreY, highscoresFontSize);
        app.font.center(app.batch, "Next", queueX, scoreY, scoreAndNextFontSize);

        String longestEntry = "Highscores";
        float highscoreY = highsocresUI.getY() + highsocresUI.getHeight() - 15;
        app.font.center(app.batch, longestEntry, scoreX, highscoreY, highscoresFontSize);
        highscoreY -= highscoresFontSize * 1.5f;
        for (Score score : app.scoreAPI.getScores()) {
            if (score.getEntry().length() > longestEntry.length())
                longestEntry = score.getEntry();
            app.font.center(app.batch, score.getEntry(), scoreX, highscoreY, highscoresFontSize);
            highscoreY -= highscoresFontSize * 1.5f;
            if (highscoreY <= highsocresUI.getY() + 20) break;
        }
        highscoresFontSize = app.font.calculateFontSize(app.batch, longestEntry, highsocresUI.getWidth() - 20);
        app.batch.end();

        if (instance.gameover) {
            app.shapeRenderer.begin();
            menu.render(app.shapeRenderer);
            app.shapeRenderer.end();
            app.batch.begin();
            app.font.center(app.batch, "Game Over!", menu.getCenterX(), menu.getY() + menu.getHeight() * 0.8f, 42);
            app.font.center(app.batch, "Your Score: " + Score.format(instance.score), menu.getCenterX(), menu.getY() + menu.getHeight() * 0.6f, 36);
            app.font.center(app.batch, "Name", menu.getX() + menu.getWidth() / 5f, menu.getY() + menu.getHeight() / 3f, 18);
            app.font.center(app.batch, app.username + (System.currentTimeMillis() % 1000 > 500 ? "|" : ""), menu.getCenterX(), menu.getY() + menu.getHeight() / 3f, 18);
            app.font.center(app.batch, "Submit", menu.getX() + menu.getWidth() / 3f, menu.getY() + menu.getHeight() * 0.2f, 18);
            app.font.center(app.batch, "Restart", menu.getX() + menu.getWidth() - menu.getWidth() / 3f, menu.getY() + menu.getHeight() * 0.2f, 18);
            app.batch.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        instance.playField.resize(width, height);

        menu.resize(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, instance.playField.getCellSize());
        menu.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, true);

        float playFieldStartX = instance.playField.getX(-1);

        nextUI.resize(playFieldStartX / 2f, Gdx.graphics.getHeight() - instance.playField.getCellSize() * 5f, instance.playField.getCellSize());
        nextUI.setPosition(playFieldStartX / 2f, Gdx.graphics.getHeight() / 2f, true);

        float playFieldEndX = instance.playField.getX(instance.playField.getCellsWide() + 1);

        scoreUI.resize((Gdx.graphics.getWidth() - playFieldEndX) / 2f, Gdx.graphics.getHeight() * 0.2f, instance.playField.getCellSize());
        scoreUI.setPosition(playFieldEndX + (Gdx.graphics.getWidth() - playFieldEndX) / 2f, Gdx.graphics.getHeight() - scoreUI.getHeight() - instance.playField.getCellSize() * 2f, true, false);

        highsocresUI.resize((Gdx.graphics.getWidth() - playFieldEndX) / 2f, Gdx.graphics.getHeight() - scoreUI.getHeight() - instance.playField.getCellSize() * 7f, instance.playField.getCellSize());
        highsocresUI.setPosition(playFieldEndX + (Gdx.graphics.getWidth() - playFieldEndX) / 2f, Gdx.graphics.getHeight() - scoreUI.getHeight() - highsocresUI.getHeight() - instance.playField.getCellSize() * 5f, true, false);
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
        if (instance.gameover) {
            switch (keycode) {
                case Input.Keys.ESCAPE:
                    instance = new GameInstance();
                    instance.playField.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                    return true;
                case Input.Keys.BACKSPACE:
                    switch (app.username.length()) {
                        case 0:
                            break;
                        case 1:
                            app.username = "";
                            break;
                        default:
                            app.username = app.username.substring(0, app.username.length() - 1);
                            break;
                    }
                    return true;
                case Input.Keys.ENTER:
                    if (app.username.length() >= 3) {
                        app.scoreAPI.postScore(app.username, instance.score);
                        instance = new GameInstance();
                        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                        app.scoreAPI.retrieveScores();
                    }
                    return true;
                default:
                    String key = Input.Keys.toString(keycode);
                    if ("ABCDEFGHIJKLMNOPQRSTUVWXYZ".contains(key)) {
                        if (app.username.length() < 16) app.username += key;
                        return true;
                    }
            }
        }

        if (!instance.gameover) {
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
            if (Input.Keys.X == keycode) {
                while (instance.getFallingTetrimino().canMoveDown(instance))
                    instance.getFallingTetrimino().moveDown();
                return true;
            }
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
