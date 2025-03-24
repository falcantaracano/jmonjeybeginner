package org.fac.jmonkey;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material; 
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
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
import org.fac.jmonkey.control.CubeChaserControl;

public class CubeChaser extends SimpleApplication {

    static {
        // must set before the Logger
        // loads logging.properties from the classpath
        try (InputStream is = UserInput.class.getClassLoader().getResourceAsStream("logging.properties")) {
            java.util.logging.LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final Logger logger = LogManager.getLogger(CubeChaser.class);
    private static Box mesh = new Box (1,1,1);
    private final static String PREFIX_CUBE = "Cube";

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
                geom.addControl (new CubeChaserControl (cam, rootNode));
            }
            rootNode.attachChild(geom);
        }
    }

    @Override
    /** initialize the scene here */
    public void simpleInitApp () {
        logger.info ("Inicializacion de la escena");
        makeCubes(40);
        flyCam.setMoveSpeed(100f);
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
        
        CubeChaser app = new CubeChaser();
        logger.info ("Starting application ...");
        app.start();
    }

}