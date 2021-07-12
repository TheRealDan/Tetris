package dev.therealdan.tetris.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;

public class FontManager implements Disposable {

    private FreeTypeFontGenerator freeTypeFontGenerator;

    private HashMap<Integer, BitmapFont> fonts = new HashMap<>();

    public FontManager() {
        freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Rubik-Medium.ttf"));
    }

    public void draw(SpriteBatch batch, String text, float x, float y, int fontSize) {
        getFont(fontSize).draw(batch, text, x, y);
    }

    public void center(SpriteBatch batch, String text, float x, float y, int fontSize) {
        getFont(fontSize).draw(batch, text, x, y, 0, Align.center, false);
    }

    @Override
    public void dispose() {
        freeTypeFontGenerator.dispose();
    }

    private void generateFont(int fontSize) {
        FreeTypeFontGenerator.FreeTypeFontParameter freeTypeFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        freeTypeFontParameter.size = fontSize;
        fonts.put(fontSize, freeTypeFontGenerator.generateFont(freeTypeFontParameter));
    }

    private BitmapFont getFont(int fontSize) {
        if (!fonts.containsKey(fontSize)) generateFont(fontSize);
        return fonts.get(fontSize);
    }

    public float getWidth(SpriteBatch spriteBatch, String text, int fontSize) {
        return getFont(fontSize).draw(spriteBatch, text, 0, -100).width;
    }

    public int calculateFontSize(SpriteBatch batch, String text, float width) {
        for (int fontSize = 6; fontSize <= 40; fontSize++) {
            float longestEntryWidth = getWidth(batch, text, fontSize);
            if (longestEntryWidth >= width) return fontSize - 1;
        }
        return 40;
    }
}