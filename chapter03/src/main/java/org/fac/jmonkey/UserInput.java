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

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;

import org.fac.jmonkey.appstate.UIDebugKeysAppState;

public class UserInput extends SimpleApplication {

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
        String path = UserInput.class.getClassLoader().getResource("logging.properties").getFile();
        System.setProperty("java.util.logging.config.file", path);
        System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
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
                boxColorString = (boxColor==ColorRGBA.Blue? "Blue" : "Red");
                logger.debug ("You triggered action: " + name + " to " + boxColorString);   
                geom.getMaterial().setColor("Color", boxColor);
            } 
        }
    }

    private class UIAnalogListener implements AnalogListener {
        @Override
        public void onAnalog (String name, float intensity, float tpf) {
            if(name.equals(MAPPING_ROTATE)) {
                logger.debug ("You triggered analog: " + name + " with intensity: " + intensity);
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
    public void simpleUpdate(float tpf) {
        /** unregister one default input mapping, so you can refine Key_C */ 
        // inputManager.deleteMapping(INPUT_MAPPING_CAMERA_POS);    // Key_C
        /**
         * NOTE;
         * Se realiza el borrado del mapping en este método, ya que cuando se hace la inicialización
         * de los mappings en el método simpleInitApp todavía no tiene definidos todos los mappings por
         * defecto, por eso da un error de que no se encuentra definido el mapping. 
         * 
         * Tiene pinta por el código de InputManager y las estructuras de datos utilizadas que 
         * la actualización de los mappings de los distintos estados de la aplicacion se 
         * realizan en threads distintos. Por eso, los mappings INPUT_MAPPING_MEMORY e 
         * INPUT_MAPPING_CAMERA_POS (ambos se definen en el estado DEBUG de la aplicación) 
         * solo estan disponibles para poder quitarlos en el metodo simpleUpdate.
         * 
         * Hay que seguir profundizando en el aprendizaje del motor para ver como se puede inicializar
         * todo esto de una forma diferente.
         */ 
        if (inputManager.hasMapping(INPUT_MAPPING_CAMERA_POS)) {
            inputManager.deleteMapping(INPUT_MAPPING_CAMERA_POS);    // Key_C 
        }
    }

    @Override
    /** (optional) Advanced renderer/framebuffer modifications */
    public void simpleRender(RenderManager rm) {}

    /** Start the jMonkeyEngine application */
    public static void main (String[] args) {
        logger.info ("java.util.logging.config.file: " + System.getProperty("java.util.logging.config.file"));
        logger.info ("java.util.logging.manager: " + System.getProperty("java.util.logging.manager"));
        logger.info ("handlers: " + System.getProperty("handlers"));
        System.setOut(IoBuilder
                        .forLogger(LogManager.getLogger("system.out"))
                        .setLevel(Level.INFO)
                        .buildPrintStream()
        );
        
        UserInput app = new UserInput();
        app.start();
    }

}