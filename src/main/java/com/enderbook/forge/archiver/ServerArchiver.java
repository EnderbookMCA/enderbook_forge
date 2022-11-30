package com.enderbook.forge.archiver;

import net.minecraft.client.multiplayer.ServerData;
import tudbut.parsing.TCN;

public class ServerArchiver {

    public static TCN makeData(ServerData server) {
        TCN tcn = new TCN();
        tcn.set("title", server.name);
        tcn.set("minecraft_server_address", server.ip);
        return tcn;
    }
}