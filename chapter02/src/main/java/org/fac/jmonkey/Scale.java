package org.fac.jmonkey;

import com.jme3.app.SimpleApplication; 
import com.jme3.material.Material; 
import com.jme3.math.ColorRGBA; 
import com.jme3.math.Vector3f; 
import com.jme3.renderer.RenderManager; 
import com.jme3.scene.Geometry; 
import com.jme3.scene.shape.Box;

public class Scale extends SimpleApplication {

    @Override
    /** initialize the scene here */
    public void simpleInitApp () {
        // create a cube(blue)-shaped mesh
        Box b = new Box(1, 1, 1);
        // create an object from the mesh
        Geometry geo1 = new Geometry ("Box1", b);
        // create a simple blue material
        Material mat = new Material (assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        // give the object the blue material
        geo1.setMaterial(mat);
        // make the object appear in the scene
        rootNode.attachChild(geo1);

        // create second cube (yellow)-shaped mesh
        Box b2 = new Box(1, 1, 1);
        // create an object from the mesh
        Geometry geo2 = new Geometry ("Box2", b2);
        // create a simple blue material
        Material mat2 = new Material (assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Yellow);
        // give the object the blue material
        geo2.setMaterial(mat2);        
        Vector3f v = new Vector3f(2.0f, 1.0f, -3.0f);
        geo2.setLocalTranslation(v);        // make the object appear in the scene
        rootNode.attachChild(geo2);

        /* Jugar con comentar y descomentar estos dos conjuntos
         * de lineas de programación para ver como funcionan
         * los metodos setLocalTranlation y move. 
         * Dejo todo descomentado. El primer metodo hace una tranlacion
         * local, luego dos translaciones a partir de la última posición.
         * Esto es una característica para todas las operaciones básicas de
         * traslación, rotación y escalado.
         */
        /* TEST 1 */
        geo2.setLocalScale(0.5f);

        /* TEST 2 */ 
        geo2.scale(2.0f);
        geo2.scale(2.0f);
    }

    @Override
    /** (optiona) Interact with update loop here */
    public void simpleUpdate(float tpf) {}

    @Override
    /** (optional) Advanced renderer/framebuffer modifications */
    public void simpleRender(RenderManager rm) {}

    /** Start the jMonkeyEngine application */
    public static void main (String[] args) {
        Scale app = new Scale();
        app.start();
    }
}