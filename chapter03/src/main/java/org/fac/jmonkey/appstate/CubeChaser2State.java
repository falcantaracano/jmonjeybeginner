package org.fac.jmonkey.appstate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.fac.jmonkey.control.CubeChaser2Control;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class CubeChaser2State extends AbstractAppState {

    private static final Logger logger = LogManager.getLogger(CubeChaser2State.class);

    private Ray ray = new Ray ();
    private Camera cam;
    private Node rootNode;
    private SimpleApplication app;
    private AssetManager assetManager;
    private static Box mesh = new Box (1,1,1);
    private final static String PREFIX_CUBE = "Cube";
    private final int DISTANCE_THRESHOLD = 10;
    private int counter = 0;

    public int getCounter () {
        return counter;
    }

    @Override
    public void initialize (AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        this.rootNode = this.app.getRootNode();
        this.cam = this.app.getCamera();
        makeCubes(40);
    }

    @Override
    public void update (float fps) {
        // 1. Reset results list.
        CollisionResults results = new CollisionResults();
        // 2. Aim the ray from camera location in camera direction.
        ray.setOrigin(cam.getLocation());
        ray.setDirection(cam.getDirection());
        // 3. Collect intersections between ray and all nodes in results list.
        rootNode.collideWith(ray, results);
        if (logger.isDebugEnabled()) {
            for (int i = 0; i < results.size(); i++) {
                // (For each “hit”, we know distance, impact point, geometry.)
                float dist = results.getCollision(i).getDistance();
                Vector3f pt = results.getCollision(i).getContactPoint();
                String target = results.getCollision(i).getGeometry().getName();
                logger.debug("Selection #" + i + ": " + target + " at " + pt + ", " + dist + " WU away.");
            }
        }
        // 4. Use the result
        if (results.size() > 0) {
            // The closest result is the target that the player picked:
            Geometry target = results.getClosestCollision().getGeometry();
            // if camera closer than 10...
            if (target.getControl(CubeChaser2Control.class) != null) {
                if (cam.getLocation().distance(target.getLocalTranslation()) < DISTANCE_THRESHOLD) {
                    // ... move the cube in the direction that camera is facing
                    logger.info ("Distance: " + cam.getLocation().distance(target.getLocalTranslation()));
                    target.move(cam.getDirection());
                    counter++;
                    logger.info(
                            target.getControl(CubeChaser2Control.class).hello()
                            + " and I am running away from "
                            + cam.getLocation()
                            +" and direction " + cam.getDirection() 
                            + ", " + counter + " times.");
                }
            }
        }
    }

    @Override
    public void cleanup () {
        super.cleanup();
        rootNode.detachAllChildren();
    }

    private Geometry myBox (String name, Vector3f loc, ColorRGBA color) {
        Geometry geom = new Geometry (name, mesh);
        Material mat = new Material (assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        geom.setMaterial(mat);
        geom.setLocalTranslation(loc);
        return geom;
    }

    private void makeCubes (int number) {
        for (int i = 0; i < number; i++) {
            //randomize 3D coordinates
            Vector3f loc = new Vector3f (
                FastMath.nextRandomInt(-20, 20),
                FastMath.nextRandomInt(-20, 20),
                FastMath.nextRandomInt(-20, 20));
            Geometry geom = (myBox(PREFIX_CUBE+i, loc, ColorRGBA.randomColor()));
            if (FastMath.nextRandomInt(1,4) == 4) {
                geom.addControl (new CubeChaser2Control ());
            }
            rootNode.attachChild(geom);
        }
    }
}
