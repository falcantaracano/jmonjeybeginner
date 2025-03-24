package org.fac.jmonkey;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.ConstantVerifierState;
import com.jme3.audio.AudioListenerState;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.Trigger;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
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

import org.fac.jmonkey.appstate.UIDebugKeysAppState;

public class UserInput extends SimpleApplication {

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

    private final static Trigger TRIGGER_COLOR1 = new KeyTrigger (KeyInput.KEY_SPACE);
    private final static Trigger TRIGGER_COLOR2 = new KeyTrigger (KeyInput.KEY_C);
    private final static Trigger TRIGGER_ROTATE = new MouseButtonTrigger(MouseInput.BUTTON_LEFT);
    private final static String MAPPING_COLOR = "Toggle Color";
    private final static String MAPPING_ROTATE = "Rotate";

    private Geometry geom;
    ColorRGBA boxColor = ColorRGBA.Blue;
    String boxColorString;
    private ActionListener actionListener = new IUActionListener (); 
    private AnalogListener analogListener = new UIAnalogListener ();

    public UserInput () {
        super (new StatsAppState(), 
               new FlyCamAppState(), 
               new AudioListenerState(), 
               new UIDebugKeysAppState(),
               new ConstantVerifierState()
        );
    }

    private class IUActionListener implements ActionListener {
        @Override
        public void onAction (String name, boolean isPressed, float tpf) {
            if (name.equals(MAPPING_COLOR) && !isPressed) { 
                boxColor =  (boxColor==ColorRGBA.Blue? ColorRGBA.Red : ColorRGBA.Blue);
                logger.info ("You triggered action: " + name + " to " + (boxColor==ColorRGBA.Blue? "Blue" : "Red"));   
                geom.getMaterial().setColor("Color", boxColor);
            } 
        }
    }

    private class UIAnalogListener implements AnalogListener {
        @Override
        public void onAnalog (String name, float intensity, float tpf) {
            if(name.equals(MAPPING_ROTATE)) {
                logger.info ("You triggered analog: " + name + " with intensity: " + intensity);
                geom.rotate(0,intensity,0);
            }
        }
    }

    private void initKeys() {
        /** register input mappings to input manager */
        inputManager.addMapping(MAPPING_COLOR, TRIGGER_COLOR1, TRIGGER_COLOR2);
        inputManager.addMapping(MAPPING_ROTATE, TRIGGER_ROTATE);
        inputManager.addListener(actionListener, MAPPING_COLOR);
        inputManager.addListener(analogListener, MAPPING_ROTATE);
    }

    @Override
    /** initialize the scene here */
    public void simpleInitApp () {
        // create a cube(blue)-shaped mesh
        Box b = new Box(1, 1, 1);
        // create an object from the mesh
        geom = new Geometry ("Box", b);
        // create a simple blue material
        Material mat = new Material (assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", boxColor);
        // give the object the blue material
        geom.setMaterial(mat);
        // make the object appear in the scene
        rootNode.attachChild(geom);
        initKeys();
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
        
        UserInput app = new UserInput();
        app.start();
    }

}