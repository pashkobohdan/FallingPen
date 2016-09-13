package com.pashkobohdan.fallingpen.stages.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by Bohdan Pashko on 24.07.16.
 */
public class GameStateManager {
    private Stack<StateBase> states;
    private StateBase currentState;

    public GameStateManager() {
        this.states = new Stack<StateBase>();
    }

    public void push (StateBase state){
        states.push(state);
    }

    public void pop(){
        states.pop().dispose();
    }

    public void set(StateBase state){
        states.pop().dispose();

        push(state);
    }

    public void update(float  dt){
        states.peek().update(dt);
    }

    public void render(SpriteBatch spriteBatch){
        states.peek().render(spriteBatch);
    }
}
