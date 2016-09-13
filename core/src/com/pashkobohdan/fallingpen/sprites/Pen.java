package com.pashkobohdan.fallingpen.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.pashkobohdan.fallingpen.libGdxWorker.AssetsWorker.AssetsLoader;
import com.pashkobohdan.fallingpen.libGdxWorker.CollisionWorker.PolygonArray;

/**
 * Created by Bohdan Pashko on 24.07.16.
 */
public class Pen {
    public static final int PEN_X_SPEED = 150;
    public static final int MAX_Y_DELTA_PEN = 250;//250
    public static final float PEN_Y_SPEED = -180;

    private Vector3 position;
    private Vector3 velocity;

    private Sprite penSprite;
    private PolygonArray penPolygon;

    private double totalTime = Math.PI / 2;//1.73

    private int baseYLine = 0;

    private float deltaY;

    private float multiPlus = 1;

    private int rotation = 0;

    public Pen(int x, int y) {
        baseYLine = y;
        position = new Vector3(x, -(float) Math.abs(Math.sin(totalTime)) * MAX_Y_DELTA_PEN + baseYLine, 0);

        penSprite = new Sprite(AssetsLoader.getPenTexture(), 0, 0, AssetsLoader.getPenTexture().getWidth(), AssetsLoader.getPenTexture().getHeight());
        penSprite.setPosition(position.x, position.y);

        float[] vertices = new float[]{
                13, 27,
                32, 27,
                42, 20,
                33, 12,
                16, 12,
                2, 19,
                2, 21
        };

        penPolygon = new PolygonArray(vertices);
        penPolygon.getPolygon().setOrigin(22f, 19.5f);

        velocity = new Vector3(PEN_X_SPEED, PEN_Y_SPEED, 0);
    }

    public Vector3 getPosition() {
        return position;
    }

    public void update(float dt) {
        totalTime += multiPlus * dt / 1.1f;

        //var1
        //baseYLine += velocity.y * dt;

        deltaY = (float) Math.abs(Math.sin(totalTime)) * MAX_Y_DELTA_PEN;

        position.y = baseYLine - deltaY;
        position.x += velocity.x * dt;
        penSprite.setPosition(position.x, position.y);

        penPolygon.getPolygon().setPosition(position.x, position.y);

        setRotation((int) (Math.cos(totalTime) * -45.0));
        penSprite.setRotation(getRotation());

        penPolygon.getPolygon().setRotation(getRotation());

        penPolygon.transformPolygonToPoints();
    }

    public void changeVector() {
        multiPlus *= -1.0f;
        velocity.x *= -1.0f;
    }

    public void changeToLeft() {
        if (getVelocity().x > 0) {
            changeVector();
        }
    }

    public void changeToRight() {
        if (getVelocity().x < 0) {
            changeVector();
        }
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public int getBaseYLine() {
        return baseYLine;
    }

    public void setBaseYLine(int baseYLine) {
        this.baseYLine = baseYLine;
    }

    public int getRotation() {
        return rotation;
    }

    public Sprite getPenSprite() {
        return penSprite;
    }

    public void setPenSprite(Sprite penSprite) {
        this.penSprite = penSprite;
    }

    public PolygonArray getPenPolygon() {
        return penPolygon;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}
