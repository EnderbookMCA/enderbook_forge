package com.enderbook.forge.util;

import java.io.File;

import com.enderbook.forge.Enderbook;

import tudbut.parsing.JSON;
import tudbut.parsing.TCN;
import tudbut.parsing.JSON.JSONFormatException;
import tudbut.tools.ConfigSaverTCN2;

public class ConfigManager {

    public static final File FILE     = new File("config/enderbook_forge.json");
    public static final File TMP_FILE = new File("config/enderbook_forge.json.tmp");

    public static TCN getTCN() {
        TCN config = new TCN();
        config.set("mod", ConfigSaverTCN2.write(Enderbook.class, false, true));
        return config;
    }

    public static String getString() {
        return JSON.write(getTCN());
    }

    public static void readTCN(TCN config) {
        try {
            ConfigSaverTCN2.read(config.getSub("mod"), Enderbook.INSTANCE);
        } catch (Exception e) {
            // All good, this just means the config is invalid.
        }
    }

    public static void readString(String s) {
        try {
            readTCN(JSON.read(s));
        } catch (JSONFormatException e) {
            // All good, this just means the config is invalid.
        }
    }
}
