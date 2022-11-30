package com.enderbook.forge.util;

import tudbut.obj.DoubleTypedObject;
import tudbut.obj.TLMap;
import tudbut.parsing.TCN;
import tudbut.tools.Queue;

public class TCNFlattener {
    public static TCN flatten(String namespace, TCN original) {
        TCN tcn = new TCN();
        Queue<DoubleTypedObject<TCN, String>> queue = new Queue<>();
        queue.add(new DoubleTypedObject<>(original, namespace));
        while(queue.hasNext()) {
            DoubleTypedObject<TCN, String> next = queue.next();
            for(TLMap.Entry<String, Object> entry : next.o.map.entries()) {
                Object o = entry.val;
                if(o instanceof TCN) {
                    queue.add(new DoubleTypedObject<>((TCN)o, next.t + "[" + entry.key + "]"));
                    continue;
                }
                tcn.set(next.t + "[" + entry.key + "]", o);
            }
        }
        return tcn;
    }
    
    public static TCN wrap(String name, Object toWrap) {
        TCN tcn = new TCN();
        tcn.set(name, toWrap);
        return tcn;
    }
}