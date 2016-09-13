package com.pashkobohdan.fallingpen.sprites;

import com.badlogic.gdx.math.Vector2;
import com.pashkobohdan.fallingpen.libGdxWorker.CollisionWorker.PolygonArray;

/**
 * Created by bohdan on 06.08.16.
 */
public class Web {
    private Vector2 position;
    private PolygonArray webPolygon;
    private boolean isGone = false;

    public Web(int x, int y) {
        setPosition(new Vector2(x, y));

        float[] vertices = new float[]{
                15 + position.x, 3 + position.y,
                34 + position.x, 3 + position.y,
                46 + position.x, 15 + position.y,
                46 + position.x, 34 + position.y,
                35 + position.x, 45 + position.y,
                14 + position.x, 45 + position.y,
                3 + position.x, 34 + position.y,
                3 + position.x, 15 + position.y
        };
        webPolygon = new PolygonArray(vertices);
        webPolygon.getPolygon().setOrigin(33.5f, 33.5f);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public PolygonArray getWebPolygon() {
        return webPolygon;
    }

    public void setWebPolygon(PolygonArray webPolygon) {
        this.webPolygon = webPolygon;
    }

    public boolean isGone() {
        return isGone;
    }

    public void setGone(boolean gone) {
        isGone = gone;
    }
}
