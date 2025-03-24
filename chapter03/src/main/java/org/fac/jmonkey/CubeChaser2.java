package org.fac.jmonkey;

import com.jme3.app.SimpleApplication;

import com.jme3.renderer.RenderManager; 


import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;
import org.fac.jmonkey.appstate.CubeChaser2State;

public class CubeChaser2 extends SimpleApplication {

    static {
        // must set before the Logger
        // loads logging.properties from the classpath
        try (InputStream is = UserInput.class.getClassLoader().getResourceAsStream("logging.properties")) {
            java.util.logging.LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final Logger logger = LogManager.getLogger(CubeChaser2.class);

    @Override
    /** initialize the scene here */
    public void simpleInitApp () {
        logger.info ("Inicializacion de la escena");
        flyCam.setMoveSpeed(100f);
        CubeChaser2State state = new CubeChaser2State();
        stateManager.attach(state);
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
        
        CubeChaser2 app = new CubeChaser2();
        logger.info ("Starting application ...");
        app.start();
    }

}