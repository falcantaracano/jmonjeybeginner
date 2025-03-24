package org.fac.jmonkey;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.Trigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.AnalogListener;
import com.jme3.material.Material; 
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager; 
import com.jme3.scene.Geometry; 
import com.jme3.scene.shape.Box;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;

public class TargetPickCenter extends SimpleApplication {

    static {
        // must set before the Logger
        // loads logging.properties from the classpath
        try (InputStream is = UserInput.class.getClassLoader().getResourceAsStream("logging.properties")) {
            java.util.logging.LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final Logger logger = LogManager.getLogger(TargetPickCenter.class);

    private final static Trigger TRIGGER_ROTATE = new MouseButtonTrigger(MouseInput.BUTTON_LEFT);
    private final static String MAPPING_ROTATE = "Rotate";
    private final static String RED_CUBE = "Red Cube";
    private final static String BLUE_CUBE = "Blue Cube";
    private final static String CENTER_MARK = "center mark";
    private final static float RADIONS_TO_ROTATE = FastMath.DEG_TO_RAD*1.0f; // Rotate 1 degrre when mouse button lesft is pressed

    private static Box mesh = new Box (1,1,1);
    private AnalogListener analogListener = new TPCAnalogListener ();

    private Geometry myBox (String name, Vector3f loc, ColorRGBA color) {
        Geometry geom = new Geometry (name, mesh);
        Material mat = new Material (assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        geom.setMaterial(mat);
        geom.setLocalTranslation(loc);
        return geom;
    }

    private void attachCenterMark() {
        Geometry c = myBox(CENTER_MARK, Vector3f.ZERO, ColorRGBA.White);
        c.scale(4);
        c.setLocalTranslation(settings.getWidth()/2,settings.getHeight()/2,0);
        guiNode.attachChild(c);
    }

    private void initKeys() {
        /** register input mappings to input manager */
        inputManager.addMapping(MAPPING_ROTATE, TRIGGER_ROTATE);
        inputManager.addListener(analogListener, MAPPING_ROTATE);
    }

    @Override
    /** initialize the scene here */
    public void simpleInitApp () {
        initKeys();
        rootNode.attachChild(myBox(RED_CUBE, new Vector3f(0,1.5f,0), ColorRGBA.Red));
        rootNode.attachChild(myBox(BLUE_CUBE, new Vector3f(0,-1.5f,0), ColorRGBA.Blue));
        attachCenterMark();
    }

    @Override
    /** (optiona) Interact with update loop here */
    public void simpleUpdate(float tpf) {}

    @Override
    /** (optional) Advanced renderer/framebuffer modifications */
    public void simpleRender(RenderManager rm) {}

    /** Start the jMonkeyEngine application */
    public static void main (String[] args) {
        System.setOut(IoBuilder
                        .forLogger(LogManager.getLogger("system.out"))
                        .setLevel(Level.INFO)
                        .buildPrintStream()
        );
        
        TargetPickCenter app = new TargetPickCenter();
        app.start();
    }

    private class TPCAnalogListener implements AnalogListener {
        @Override
        public void onAnalog (String name, float intensity, float tpf) {
            if(MAPPING_ROTATE.equals (name)) {
                CollisionResults results = new CollisionResults();
                Ray ray = new Ray (cam.getLocation(), cam.getDirection());
                rootNode.collideWith(ray, results);
                for (int i = 0; i < results.size(); i++) {    
                    float    dist = results.getCollision(i).getDistance();    
                    Vector3f pt = results.getCollision(i).getContactPoint();    
                    String   target = results.getCollision(i).getGeometry(). getName();    
                    logger.debug ("Selection: #" + i + ": " + target + " at " + pt + ", " + dist + " WU away."); 
                }
                if (results.size() > 0) {
                    Geometry target = results.getClosestCollision().getGeometry();
                    if (RED_CUBE.equals(target.getName())) {
                        // rotate left
                        target.rotate (0, RADIONS_TO_ROTATE, 0);
                    } else if (BLUE_CUBE.equals (target.getName())) {
                        // rotate right
                        target.rotate (0, -RADIONS_TO_ROTATE, 0);
                    }
                } else 
                    logger.debug ("Selection Nothing");
            }
        }
    }

}