package com.pashkobohdan.fallingpen.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.pashkobohdan.fallingpen.FallingPen;
import com.pashkobohdan.fallingpen.libGdxWorker.AssetsWorker.AssetsLoader;
import com.pashkobohdan.fallingpen.sprites.Pen;
import com.pashkobohdan.fallingpen.sprites.Web;
import com.pashkobohdan.fallingpen.stages.core.GameStateManager;
import com.pashkobohdan.fallingpen.stages.core.StateTouched;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Bohdan Pashko on 24.07.16.
 */
public class PlayStage extends StateTouched {
    private Pen pen;

    private List<Web> webs;
    private Random random;

    private Vector2 groundPos1, groundPos2;

    private int score = 0;
    private BitmapFont scoreFont = new BitmapFont();

    private ShapeRenderer shapeRenderer;

    private boolean isEnd = false;

    private int fps = 0;
    private int totalFrameCount = 0;
    private float totalFrameTime = 0;

    private float totalConditionTimes = 0;
    private boolean firstLaunch;
    private int currentConditionOfFirstStart = 0;
    private float[] conditionTimes = {0.0f, 1.6f, 0.6f};

    public PlayStage(GameStateManager gsm) {
        super(gsm);

        camera.setToOrtho(false, FallingPen.WIDTH / 2, FallingPen.HEIGHT / 2);///2

        pen = new Pen((int) camera.position.x - AssetsLoader.getPenTexture().getWidth() / 2, (int) (camera.position.y + camera.viewportHeight / 2 - camera.viewportHeight / 6));//(int) camera.viewportWidth / 2


        camera.position.y = pen.getBaseYLine() - camera.viewportHeight + camera.viewportHeight / 6;
        camera.update();


        groundPos1 = new Vector2(0, 0);
        groundPos2 = new Vector2(0, -camera.viewportHeight);

        webs = new LinkedList<Web>();

        random = new Random();

        for (int i = 0; i < 5; i++) {
            webs.add(new Web(random.nextInt((int) (camera.position.x + camera.viewportWidth / 2 - AssetsLoader.getWebTexture().getWidth())),
                    (int) camera.position.y - (int) (camera.viewportHeight * 1.5) - 170 * i));
        }

        Preferences prefs = Gdx.app.getPreferences("totalGamePrefs");
        firstLaunch = prefs.getBoolean("firstLaunch", true);
        prefs.putBoolean("firstLaunch", true);
        prefs.flush();

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

    }

    @Override
    public void leftSideClick() {
        if (firstLaunch) {
            if (currentConditionOfFirstStart == 0 || currentConditionOfFirstStart == 2) {
                return;
            } else if (currentConditionOfFirstStart == 3) {
                currentConditionOfFirstStart = 4;
                firstLaunch = false;
                pen.changeToLeft();
            }
        }

        pen.changeToLeft();
    }

    @Override
    public void rightSideClick() {
        if (firstLaunch) {
            if (currentConditionOfFirstStart == 0 || currentConditionOfFirstStart == 2) {
                return;
            } else if (currentConditionOfFirstStart == 1) {
                totalConditionTimes = 0;
                currentConditionOfFirstStart = 2;
                pen.changeToRight();
            }
        }

        pen.changeToRight();
    }

    @Override
    public void screenClick() {
        if (isEnd) {
            gsm.push(new PlayStage(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

        totalFrameTime += dt;
        if (totalFrameTime > 0.999f) {
            totalFrameTime = 0;
            fps = totalFrameCount;
            totalFrameCount = 0;
        }

        if (firstLaunch) {
            totalConditionTimes += dt;

            if (currentConditionOfFirstStart == 0 && totalConditionTimes > conditionTimes[1]) {
                currentConditionOfFirstStart = 1;
            } else if (currentConditionOfFirstStart == 2 && totalConditionTimes > conditionTimes[2]) {
                currentConditionOfFirstStart = 3;
            }

            if (currentConditionOfFirstStart == 1 || currentConditionOfFirstStart == 3) {
                return;
            }
        }


        if (!isEnd) {
            groundPos1.y += 40 * dt;//180, 40
            groundPos2.y += 40 * dt;

            updateGround();
        } else {
            return;
        }

        pen.update(dt);
        if (pen.getPosition().x < 1 && pen.getVelocity().x < 0) {
            pen.changeVector();
        } else {
            if (pen.getPosition().x > camera.position.x + camera.viewportWidth / 2 - pen.getPenSprite().getWidth() && pen.getVelocity().x > 0) {
                pen.changeVector();
            }
        }

        // var1
//        camera.position.y = pen.getBaseYLine() - camera.viewportHeight + camera.viewportHeight / 6;
//        camera.update();

        for (int i = 0; i < webs.size(); i++) {

            //var2
            webs.get(i).getWebPolygon().getPolygon().setPosition(webs.get(i).getWebPolygon().getPolygon().getX(), webs.get(i).getWebPolygon().getPolygon().getY() + 180 * dt);
            webs.get(i).getPosition().y += 180 * dt;
            webs.get(i).getWebPolygon().transformPolygonToPoints();

            //Intersector.overlapConvexPolygons(webs.get(i).getWebPolygon(), pen.getPenPolygon())

            //isOverlap(webs.get(i).getWebPolygon(), pen.getPenPolygon()
            if (Intersector.overlapConvexPolygons(pen.getPenPolygon().getPolygon(), webs.get(i).getWebPolygon().getPolygon())) {
                isEnd = true;
            }

            if (!webs.get(i).isGone() && webs.get(i).getPosition().y > pen.getPosition().y + pen.getPenSprite().getWidth()) {
                score++;
                webs.get(i).setGone(true);
            }
        }

        for (Web web : webs) {
            if (web.getPosition().y > camera.position.y + camera.viewportHeight / 2) {//web.getPosition().y > camera.position.y + camera.viewportHeight / 2
                webs.remove(web);
                webs.add(new Web(random.nextInt((int) (camera.position.x + camera.viewportWidth / 2 - AssetsLoader.getWebTexture().getWidth())),
                        (int) (webs.get(webs.size() - 1).getPosition().y - 170)));

                break;
            }
        }
    }


    @Override
    public void render(SpriteBatch spriteBatch) {
        totalFrameCount++;

        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();


        //drawing background
        spriteBatch.draw(AssetsLoader.getPlayBackgroundTexture(), groundPos1.x, groundPos1.y, camera.viewportWidth, camera.viewportHeight);
        spriteBatch.draw(AssetsLoader.getPlayBackgroundTexture(), groundPos2.x, groundPos2.y, camera.viewportWidth, camera.viewportHeight);

        //drawing webs
        for (Web web : webs) {
            spriteBatch.draw(AssetsLoader.getWebTexture(), web.getPosition().x, web.getPosition().y);
        }

        //drawing pen
        pen.getPenSprite().draw(spriteBatch);

        //drawing score and fps
        scoreFont.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        scoreFont.draw(spriteBatch, "Score : " + score, camera.position.x - camera.viewportWidth / 2, camera.position.y + camera.viewportHeight / 2 - 10);
        scoreFont.draw(spriteBatch, "FPS : " + fps, camera.position.x + camera.viewportWidth / 4, camera.position.y + camera.viewportHeight / 2 - 10);

        if (isEnd) {
            spriteBatch.draw(AssetsLoader.getPlayButtonTexture(), camera.position.x - AssetsLoader.getPlayButtonTexture().getWidth() / 4, camera.position.y, 105, 35);
        }

        if (firstLaunch) {
            scoreFont.setColor(com.badlogic.gdx.graphics.Color.RED);

            switch (currentConditionOfFirstStart) {
                case 1:
                    scoreFont.draw(spriteBatch, "Press the right side of the screen\n to move the pen right", camera.position.x - camera.viewportWidth / 2 + 10, camera.position.y);
                    break;
                case 3:
                    scoreFont.draw(spriteBatch, "Press the left side of the screen\n to move the pen left", camera.position.x - camera.viewportWidth / 2 + 10, camera.position.y);
                    break;
            }
        }

        spriteBatch.end();


        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        if (firstLaunch && (currentConditionOfFirstStart == 1 || currentConditionOfFirstStart == 3)) {
            shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.RED);
            shapeRenderer.line(camera.position.x, camera.position.y - camera.viewportHeight / 2, camera.position.x, camera.position.y +camera.viewportHeight / 2);
        }

//
//        shapeRenderer.setColor(new Color(255, 0, 0, 0.0f));
//        shapeRenderer.polygon(pen.getPenPolygon().getPolygon().getTransformedVertices());
//
//        shapeRenderer.setColor(new Color(0, 255, 0, 0.0f));
//        for (Web web : webs) {
//            shapeRenderer.polygon(web.getWebPolygon().getPolygon().getTransformedVertices());
//        }
//
        shapeRenderer.end();
    }

    @Override
    public void dispose() {

    }

    private void updateGround() {
        if (groundPos1.y > camera.position.y + camera.viewportHeight / 2) {
            groundPos1.add(0, -2 * camera.viewportHeight);
        }
        if (groundPos2.y > camera.position.y + camera.viewportHeight / 2) {
            groundPos2.add(0, -2 * camera.viewportHeight);
        }
    }
}