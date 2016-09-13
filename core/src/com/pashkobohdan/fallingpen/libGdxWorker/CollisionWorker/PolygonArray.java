package com.pashkobohdan.fallingpen.libGdxWorker.CollisionWorker;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Bohdan Pashko on 24.08.2016.
 */
public class PolygonArray {
    private Polygon polygon;
    private float[] vertices;
    private Array<Vector2> verticesPoints;

    public PolygonArray(Polygon polygon) {
        this.setPolygon(polygon);

        transformPolygonToPoints();
    }

    public PolygonArray(float[] vertices) {
        this.setVertices(vertices);

        setPolygon(new Polygon());
        getPolygon().setVertices(vertices);

        transformPolygonToPoints();
    }

    public boolean transformPolygonToPoints() {
        vertices = polygon.getTransformedVertices();

        if (getVertices().length < 1 || getVertices().length % 2 != 0) {
            return false;
        }
        setVerticesPoints(new Array<Vector2>());

        for (int i = 0; i < getVertices().length - 1; i += 2) {
            getVerticesPoints().add(new Vector2(getVertices()[i], getVertices()[i + 1]));
        }

        return true;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public float[] getVertices() {
        return vertices;
    }

    public void setVertices(float[] vertices) {
        this.vertices = vertices;
    }

    public Array<Vector2> getVerticesPoints() {
        return verticesPoints;
    }

    public void setVerticesPoints(Array<Vector2> verticesPoints) {
        this.verticesPoints = verticesPoints;
    }
}
