package org.fac.jmonkey;

import com.jme3.app.SimpleApplication; 
import com.jme3.material.Material; 
import com.jme3.math.ColorRGBA; 
import com.jme3.renderer.RenderManager; 
import com.jme3.scene.Geometry; 
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/** Basic jMonkeyEngine game template */
public class AppSettingsDemo extends SimpleApplication {

    @Override
    /** initialize the scene here */
    public void simpleInitApp () {
		setDisplayFps(false);                         // hide frames-per-sec display
        setDisplayStatView(false);
        // create a cube(blue)-shaped mesh
        Box b = new Box(1, 1, 1);
        // create an object from the mesh
        Geometry geom = new Geometry ("Box", b);
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
		// read GraphicsDevice attributes
        GraphicsDevice device = GraphicsEnvironment
									.getLocalGraphicsEnvironment()
									.getDefaultScreenDevice();
        DisplayMode[] modes = device.getDisplayModes();

        // specify display settings based on DisplayMode
        AppSettings settings = new AppSettings(true);
        settings.setResolution(modes[0].getWidth(), modes[0].getHeight());
        settings.setFrequency(modes[0].getRefreshRate());
        settings.setDepthBits(modes[0].getBitDepth());
        settings.setFullscreen(device.isFullScreenSupported());
        settings.setTitle("My Cool Game"); // only visible if not fullscreen
        settings.setSamples(2);            // anti-aliasing

        AppSettingsDemo app = new AppSettingsDemo();
		app.setSettings(settings);         // apply settings to app
        app.setShowSettings(true);
        app.start();
    }
}