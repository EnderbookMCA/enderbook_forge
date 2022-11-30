package com.enderbook.forge.archiver;

import com.enderbook.forge.util.TCNFlattener;

import net.minecraft.world.entity.player.Player;
import tudbut.parsing.TCN;
import tudbut.parsing.TCNArray;

public class PlayerArchiver {
    
    public static TCN makeData(Player player) {
        TCN tcn = new TCN();
        tcn.set("title", player); // overwrite Archive#title
        TCNArray images = new TCNArray();
        images.add("/dev/sda"); // TODO remove this terrible joke
        tcn.set("gallery_image_attributes", TCNFlattener.wrap("attachment", images));
        return tcn;
    }
}
