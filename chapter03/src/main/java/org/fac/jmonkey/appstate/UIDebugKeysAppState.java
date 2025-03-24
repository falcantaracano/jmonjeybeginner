package org.fac.jmonkey.appstate;

import com.jme3.app.Application;
import com.jme3.app.DebugKeysAppState;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.util.BufferUtils;

public class UIDebugKeysAppState extends AbstractAppState {

    public static final String INPUT_MAPPING_MEMORY = DebugKeysAppState.INPUT_MAPPING_MEMORY;
    
    private Application app;
    private final IUDebugKeyListener keyListener = new IUDebugKeyListener();
    private InputManager inputManager;

    public UIDebugKeysAppState () {
    }    

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        this.app = app;
        this.inputManager = this.app.getInputManager();
        
        if (app.getInputManager() != null) {
            inputManager.addMapping(INPUT_MAPPING_MEMORY, new KeyTrigger(KeyInput.KEY_M));
            inputManager.addListener(keyListener, INPUT_MAPPING_MEMORY);                   
        }               
    }
            
    @Override
    public void cleanup() {
        super.cleanup();

        if (inputManager.hasMapping(INPUT_MAPPING_MEMORY)) {
            inputManager.deleteMapping(INPUT_MAPPING_MEMORY);
        }        
        inputManager.removeListener(keyListener);
    }

    private class IUDebugKeyListener implements ActionListener {

        @Override
        public void onAction(String name, boolean value, float tpf) {
            if (!value) {
                return;
            }

            if (name.equals(INPUT_MAPPING_MEMORY)) {
                BufferUtils.printCurrentDirectMemory(null);
            }
        }
    }

}
