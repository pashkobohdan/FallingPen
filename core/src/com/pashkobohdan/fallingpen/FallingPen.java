package com.pashkobohdan.fallingpen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pashkobohdan.fallingpen.libGdxWorker.AssetsWorker.AssetsLoader;
import com.pashkobohdan.fallingpen.stages.MenuState;
import com.pashkobohdan.fallingpen.stages.core.GameStateManager;

public class FallingPen extends ApplicationAdapter {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	private SpriteBatch batch;
	private GameStateManager gameStateManeger;

	@Override
	public void create() {
		AssetsLoader.loadAllFromSources();

		batch = new SpriteBatch();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		gameStateManeger = new GameStateManager();
		gameStateManeger.push(new MenuState(gameStateManeger));
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gameStateManeger.update(Gdx.graphics.getDeltaTime());
		gameStateManeger.render(batch);
	}

	@Override
	public void dispose() {
		batch.dispose();

		AssetsLoader.disposeAll();
	}
}
