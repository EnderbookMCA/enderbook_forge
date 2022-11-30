package com.enderbook.forge.data;

import com.enderbook.forge.util.TCNMerger;

import tudbut.parsing.TCN;
import tudbut.tools.ConfigSaverTCN2;

public class Archive {
    public final String title;
    public final String archive_type;
    public final int _public;
    public final String description;
    public final int nsfw;
    
    public Archive (String title, ArchiveType archiveType, boolean _public, String description, boolean nsfw) {
        this.title = title;
        this.archive_type = archiveType.name();
        this._public = _public ? 1 : 0;
        this.description = description;
        this.nsfw = nsfw ? 1 : 0;
    }
    
    private TCN serialize0() {
        TCN tcn = (TCN) ConfigSaverTCN2.write(this, true, false);
        tcn.set("public", tcn.get("_public"));
        return tcn;
    }
    
    public TCNMerger serialize() {
        return new TCNMerger(serialize0());
    }
    
    public TCNMerger serialize(TCNMerger merger) {
        merger.append(serialize0(), false);
        return merger;
    }
    
    public enum ArchiveType {
        bannner,
        base,
        blog,
        book,
        kit, ender_chest,
        map,
        player,
        timeline,
    }
}