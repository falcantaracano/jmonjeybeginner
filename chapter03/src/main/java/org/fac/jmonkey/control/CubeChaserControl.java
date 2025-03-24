package org.fac.jmonkey.control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

public class CubeChaserControl extends AbstractControl {

    private static final Logger logger = LogManager.getLogger(CubeChaserControl.class);

    private Ray ray = new Ray(); 
    private final Camera cam; 
    private final Node rootNode;
    private final int DISTANCE_THRESHOLD = 10;

    public CubeChaserControl (Camera cam, Node rootNode) {
        this.cam = cam;
        this.rootNode = rootNode;
    }

    @Override
    protected void controlUpdate (float tpf) {
        // 1 Reset result list.
        CollisionResults results = new CollisionResults ();
        // 2 Aim the ray from camera location in camera direction
        ray.setOrigin(cam.getLocation());
        ray.setDirection(cam.getDirection());
        // 3 Collect intersections between ray and all nodes in result list
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
        if (results.size() > 0) {
            // The closest result is the target that the pkayer picked
            Geometry target = results.getClosestCollision().getGeometry();
            if (target.equals(spatial)) {
                // if camera closer than 10 to m3
                if (cam.getLocation().distance(spatial.getLocalTranslation()) < DISTANCE_THRESHOLD) {
                    logger.info ("Distance: " + cam.getLocation().distance(spatial.getLocalTranslation()));
                    logger.info ("Moving cube: " + target.getName() + " to camara direction: " + cam.getDirection());
                    spatial.move(cam.getDirection());
                }
            }
        }
        spatial.rotate(tpf, tpf, tpf); // affected cubes are marked: they rotate
    }
    
    @Override
    protected void controlRender (RenderManager rm, ViewPort vp) {}

    @Override
    public Control cloneForSpatial (Spatial spatial) {
        logger.error ("Clone for spatial not supported yet");
        throw new UnsupportedOperationException ("Not supported yet");
    }

}
