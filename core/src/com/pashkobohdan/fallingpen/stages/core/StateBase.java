package com.pashkobohdan.fallingpen.stages.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Bohdan Pashko on 24.07.16.
 */
public interface StateBase {
    void handleInput();

    void update(float dt);

    void render(SpriteBatch spriteBatch);

    void dispose();
}
