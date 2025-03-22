package org.fac.jmonkey;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material; 
import com.jme3.math.ColorRGBA; 
import com.jme3.renderer.RenderManager; 
import com.jme3.scene.Geometry; 
import com.jme3.scene.shape.Box;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;

public class Main extends SimpleApplication {

    /**
     * NOTE:
     * Esto no funciona. Se coge antes el LogManager de java.util.logging que el indicado en
     * la property a pesar de poner el estático al principio de esta clase.
     * 
     * Lo que si he conseguido es redirigir los System.out a log4j2
     * 
     */
    static {
        // must set before the Logger
        // loads logging.properties from the classpath
        try (InputStream is = UserInput.class.getClassLoader().getResourceAsStream("logging.properties")) {
            java.util.logging.LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final Logger logger = LogManager.getLogger(UserInput.class);

    private Geometry geom;

    @Override
    /** initialize the scene here */
    public void simpleInitApp () {
        logger.info ("Inicializacion de la escena");
        // create a cube(blue)-shaped mesh
        Box b = new Box(1, 1, 1);
        // create an object from the mesh
        geom = new Geometry ("Box", b);
        // create a simple blue material
        Material mat = new Material (assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        // give the object the blue material
        geom.setMaterial(mat);
        // make the object appear in the scene
        rootNode.attachChild(geom);
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
        
        Main app = new Main();
        logger.info ("Starting application ...");
        app.start();
    }

}