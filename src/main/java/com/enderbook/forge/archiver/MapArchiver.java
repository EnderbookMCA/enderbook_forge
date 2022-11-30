package com.enderbook.forge.archiver;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import tudbut.parsing.TCN;

public class MapArchiver {
    
    public static TCN makeData(ItemStack item) {
        TCN tcn = new TCN();
        tcn.set("map_screenshot", "/dev/sda1"); // lol eat your entire drive // TODO remove this joke
        tcn.set("map_id", MapItem.getMapId(item));
        return tcn;
    }
}
