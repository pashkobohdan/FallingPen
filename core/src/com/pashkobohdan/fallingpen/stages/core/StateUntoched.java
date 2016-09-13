package com.pashkobohdan.fallingpen.stages.core;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Bohdan Pashko on 24.07.16.
 */
public abstract class StateUntoched implements StateBase {
    protected OrthographicCamera camera;
    protected Vector3 mouse;
    protected GameStateManager gameStateManager;

    public StateUntoched(GameStateManager gameStateManager){
        this.gameStateManager = gameStateManager;

        camera =new OrthographicCamera();
        mouse = new Vector3();
    }
}
