package com.pashkobohdan.fallingpen.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.pashkobohdan.fallingpen.FallingPen;
import com.pashkobohdan.fallingpen.libGdxWorker.AssetsWorker.AssetsLoader;
import com.pashkobohdan.fallingpen.sprites.Pen;
import com.pashkobohdan.fallingpen.stages.core.GameStateManager;
import com.pashkobohdan.fallingpen.stages.core.StateUntoched;

/**
 * Created by Bohdan Pashko on 24.07.16.
 */
public class MenuState extends StateUntoched {
    private Vector2 groundPos;

    private BitmapFont font;
    private Pen pen;

    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);

        camera.setToOrtho(false, FallingPen.WIDTH / 2, FallingPen.HEIGHT / 2);
        font = new BitmapFont();

        groundPos = new Vector2(0, 0);

        pen = new Pen((int) camera.position.x - AssetsLoader.getPenTexture().getWidth() / 2, (int) (camera.position.y + camera.viewportHeight / 2));
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            gameStateManager.push(new PlayStage(gameStateManager));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

        pen.update(dt);
        if (pen.getPosition().x < 1 && pen.getVelocity().x < 0) {
            pen.changeVector();
        } else {
            if (pen.getPosition().x > camera.position.x + camera.viewportWidth / 2 - pen.getPenSprite().getWidth() && pen.getVelocity().x > 0) {
                pen.changeVector();
            }
        }

        camera.position.y = pen.getBaseYLine() - camera.viewportHeight + camera.viewportHeight / 8;
        camera.update();


        groundPos.y = camera.position.y - camera.viewportHeight / 2;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        spriteBatch.draw(AssetsLoader.getPlayBackgroundTexture(), groundPos.x, groundPos.y, camera.viewportWidth, camera.viewportHeight);
        spriteBatch.draw(AssetsLoader.getPlayButtonTexture(), camera.position.x - AssetsLoader.getPlayButtonTexture().getWidth() / 4, camera.position.y, 105, 35);


        font.setColor(Color.GREEN);
        font.draw(spriteBatch, "Click anywhere to start", camera.position.x - camera.viewportWidth/2 + 30, camera.position.y - camera.viewportHeight / 2 + 50);


        pen.getPenSprite().draw(spriteBatch);

        spriteBatch.end();
    }

    @Override
    public void dispose() {

    }
}
