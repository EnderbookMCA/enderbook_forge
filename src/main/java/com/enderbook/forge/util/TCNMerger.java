package com.enderbook.forge.util;

import tudbut.obj.TLMap;
import tudbut.parsing.TCN;

public class TCNMerger {

    private TCN state = new TCN();
    
    public TCNMerger(TCN initial) {
        state = initial;
    }
    
    public TCNMerger() {}
    
    public void append(TCN tcn, boolean overwrite) {
        for(TLMap.Entry<String, Object> entry : tcn.map.entries()) {
            if(overwrite)
                state.map.set(entry.key, entry.val);
            else {
                state.map.setIfNull(entry.key, entry.val);
            }
        }
    }
    
    public TCN unwrap() {
        return state;
    }
}