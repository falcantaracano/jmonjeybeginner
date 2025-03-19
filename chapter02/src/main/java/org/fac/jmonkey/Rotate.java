package org.fac.jmonkey;

import com.jme3.app.SimpleApplication; 
import com.jme3.material.Material; 
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f; 
import com.jme3.renderer.RenderManager; 
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class Rotate extends SimpleApplication {

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

        /* Uncomment each test separately */

        /* TEST 1: relative rotation by radians */
//        float r = FastMath.DEG_TO_RAD * 45f;          // convert 45 degrees to radians       
//       geo2.rotate(r, 0.0f, 0.0f);                // relative rotation around x axis
//       geo1.rotate(0.0f, r, 0.0f);                // relative rotation around y axis

        /* TEST 2: absolute rotation using Quaternion */
//        Quaternion roll045 = new Quaternion();
//        roll045.fromAngleAxis(45 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X);
//        geo2.setLocalRotation(roll045);

        /* TEST 3: absolute rotation with Quaternion and slerp interpolation */
//        Quaternion q1 = new Quaternion();
//        q1.fromAngleAxis(50 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X);
//        Quaternion q2 = new Quaternion();
//        q2.fromAngleAxis(40 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X);
//        Quaternion q3 = new Quaternion();
//        q3.slerp(q1, q2, 0.5f);                     // .5f = halfways between 50 and 40 deg
//         geo2.setLocalRotation(q3);

        /* TEST 4: Rotate both 45 deg around z around pivot node at origin */
        Node pivot = new Node("pivot node");          // at origin
        pivot.attachChild(geo1);
        pivot.attachChild(geo2);
        pivot.rotate(0, 0, FastMath.DEG_TO_RAD * 45);
        rootNode.attachChild(pivot);
    }

    @Override
    /** (optiona) Interact with update loop here */
    public void simpleUpdate(float tpf) {}

    @Override
    /** (optional) Advanced renderer/framebuffer modifications */
    public void simpleRender(RenderManager rm) {}

    /** Start the jMonkeyEngine application */
    public static void main (String[] args) {
        Rotate app = new Rotate();
        app.start();
    }
}