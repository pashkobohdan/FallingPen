package com.pashkobohdan.fallingpen.stages.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Bohdan Pashko on 24.07.16.
 */
public abstract class StateTouched implements StateBase {
    protected OrthographicCamera camera;
    protected Vector3 mouse;
    protected GameStateManager gsm;

    public StateTouched(GameStateManager gsm) {
        this.gsm = gsm;

        camera = new OrthographicCamera();
        mouse = new Vector3();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            if (Gdx.input.getX() < Gdx.graphics.getWidth() / 2) {
                leftSideClick();
            } else {
                rightSideClick();
            }

            screenClick();
        }
    }

    public abstract void leftSideClick();

    public abstract void rightSideClick();

    public abstract void screenClick();
}
