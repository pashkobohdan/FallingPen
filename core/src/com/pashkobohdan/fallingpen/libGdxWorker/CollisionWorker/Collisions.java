package com.pashkobohdan.fallingpen.libGdxWorker.CollisionWorker;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by pashk on 24.08.2016.
 */
public class Collisions {

    /**
     * Non-convex overlap
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean isOverlap(PolygonArray a, PolygonArray b) {
        for (Vector2 v : a.getVerticesPoints()) {
            if (Intersector.isPointInPolygon(b.getVerticesPoints(), v))
                return true;
        }
        for (Vector2 v : b.getVerticesPoints()) {
            if (Intersector.isPointInPolygon(a.getVerticesPoints(), v))
                return true;
        }
        return false;
    }
}
