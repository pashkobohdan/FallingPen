package com.pashkobohdan.fallingpen.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pashkobohdan.fallingpen.FallingPen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = FallingPen.WIDTH;
		config.height= FallingPen.HEIGHT;

		//config.vSyncEnabled = true;

		new LwjglApplication(new FallingPen(), config);
	}
}
