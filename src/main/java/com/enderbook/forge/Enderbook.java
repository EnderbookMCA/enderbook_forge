package com.enderbook.forge;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.enderbook.forge.util.ConfigManager;
import de.tudbut.io.StreamReader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Enderbook.MODID)
public class Enderbook {
    public static final String MODID = "enderbook_forge";
    public static Enderbook INSTANCE; {INSTANCE = this;}

    public Enderbook() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::init);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void init(FMLCommonSetupEvent event) {
        try(FileInputStream stream = new FileInputStream(ConfigManager.FILE)) {
            StreamReader reader = new StreamReader(stream);
            ConfigManager.readString(reader.readAllAsString());
        } catch (IOException e) {
            // Do nothing, config doesn't exist yet, it'll be created at shutdown.
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                try(FileOutputStream stream = new FileOutputStream(ConfigManager.TMP_FILE)) {
                    stream.write(ConfigManager.getString().getBytes());
                }
                ConfigManager.TMP_FILE.renameTo(ConfigManager.FILE);
            } catch (IOException e) {
                // Unable to save config, this indicates a read-only file system, in which
                // case, how did Minecraft even start?!
                throw new RuntimeException("Invalid File System state", e);
            }
        }, MODID + " shutdown thread"));
    }

}
