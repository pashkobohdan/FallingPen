package com.pashkobohdan.fallingpen.libGdxWorker.AssetsWorker;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Bohdan Pashko on 24.08.2016.
 */
public class AssetsLoader {
    private static Texture playBackgroundTexture;
    private static Texture penTexture;
    private static Texture webTexture;
    private static Texture playButtonTexture;

    public static boolean loadAllFromSources() {
        try {
            setPlayBackgroundTexture(new Texture("fon1.png"));
            setPenTexture(new Texture("feather.png"));
            setWebTexture(new Texture("Spiderweb_finish.png"));
            setPlayButtonTexture(new Texture("play_button_finish.png"));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void disposeAll() {
        playBackgroundTexture.dispose();
        penTexture.dispose();
        webTexture.dispose();
        playButtonTexture.dispose();
    }

    public static Texture getPlayBackgroundTexture() {
        return playBackgroundTexture;
    }

    public static void setPlayBackgroundTexture(Texture playBackgroundTexture) {
        AssetsLoader.playBackgroundTexture = playBackgroundTexture;
    }

    public static Texture getPenTexture() {
        return penTexture;
    }

    public static void setPenTexture(Texture penTexture) {
        AssetsLoader.penTexture = penTexture;
    }

    public static Texture getWebTexture() {
        return webTexture;
    }

    public static void setWebTexture(Texture webTexture) {
        AssetsLoader.webTexture = webTexture;
    }

    public static Texture getPlayButtonTexture() {
        return playButtonTexture;
    }

    public static void setPlayButtonTexture(Texture playButtonTexture) {
        AssetsLoader.playButtonTexture = playButtonTexture;
    }
}
