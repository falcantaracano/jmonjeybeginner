package org.fac.jmonkey.control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

public class CubeChaser2Control extends AbstractControl {

    private static final Logger logger = LogManager.getLogger(CubeChaser2Control.class);

    public CubeChaser2Control() {}

    @Override
    protected void controlUpdate(float tpf) {
        spatial.rotate(tpf, tpf, tpf);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        logger.error ("Clone for spatial not supported yet.");
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String hello(){
        return "Hello, my name is "+spatial.getName();
    }

}
