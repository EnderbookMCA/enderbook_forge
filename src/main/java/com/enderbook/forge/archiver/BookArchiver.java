package com.enderbook.forge.archiver;

import com.enderbook.forge.util.TCNFlattener;

import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WrittenBookItem;
import tudbut.parsing.TCN;
import tudbut.parsing.TCNArray;

public class BookArchiver {
    
    public static TCN makeData(ItemStack item) {
        TCN tcn = new TCN();
        tcn.set("book_title", item.getTag().getString(WrittenBookItem.TAG_TITLE));
        ListTag list = item.getTag().getList(WrittenBookItem.TAG_PAGES, 8);
        TCNArray arr = new TCNArray();
        for (int i = 0; i < list.size(); i++) {
            arr.add(TCNFlattener.wrap("content", Component.Serializer.fromJson(list.getString(i)).getString()));
        }
        tcn.set("book_pages_attributes", arr);
        tcn.set("author", item.getTag().getString(WrittenBookItem.TAG_AUTHOR));
        tcn.set("generation", item.getTag().getInt(WrittenBookItem.TAG_GENERATION));
        return tcn;
    }
}
