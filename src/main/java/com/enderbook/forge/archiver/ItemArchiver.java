package com.enderbook.forge.archiver;

import java.util.Map;

import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import tudbut.parsing.TCN;
import tudbut.parsing.TCNArray;

public class ItemArchiver {
    private TCNArray destination;
    
    public ItemArchiver(TCNArray destination) {
        this.destination = destination;
    }
    
    public void append(ItemStack item) {
        TCN data = makeData(item);
        destination.add(data);
        data.set("kit_index", destination.size()); // size is always last_index + 1
    }
    
    public static TCN makeData(ItemStack item) {
        TCN tcn = new TCN();
        tcn.set("quantity", item.getCount());
        // kit_index is added later
        tcn.set("durability", (int)((item.getDamageValue() / (float)item.getMaxDamage()) * 100f));
        tcn.set("item_type", Item.getId(item.getItem())); // should be item_text_type, but the numeric type does not exist in 1.19, so we can't provide it.
        tcn.set("item_meta", 0); // api specifies 1.12.2 values. 0 can never be wrong, so we just use that.
        tcn.set("item_name", item.getDisplayName().getString());
        tcn.set("image_format", "png"); // minecraft uses png for all textures
        TCNArray lore = new TCNArray();
        ListTag list = item.getTag().getList("Lore", 0);
        for (int i = 0; i < list.size(); i++) {
            lore.add(Component.Serializer.fromJson(list.getString(i)).getString());
        }
        tcn.set("lore", lore); // not specified by the API, but i assume it might be useful nonetheless.
        TCNArray enchantments = new TCNArray();
        for (Map.Entry<Enchantment, Integer> entry : EnchantmentHelper.getEnchantments(item).entrySet()) {
            TCN ench = new TCN();
            ench.set("name", EnchantmentHelper.getEnchantmentId(entry.getKey()).toString());
            ench.set("level", entry.getValue());
            enchantments.add(ench);
        }
        tcn.set("enchantment_attributes", enchantments);
        tcn.set("raw", item.getTag().getAsString());
        return tcn;
    }

}