package kr.rtuserver.storage;

import kr.rtuserver.framework.bukkit.api.RSPlugin;
import kr.rtuserver.storage.commands.Command;
import kr.rtuserver.storage.config.MegaphoneConfig;
import lombok.Getter;

public class Megaphone extends RSPlugin {

    @Getter
    private static Megaphone instance;

    @Getter
    private MegaphoneConfig megaphoneConfig;

    @Override
    public void load() {
        instance = this;
    }

    @Override
    public void enable() {
        megaphoneConfig = new MegaphoneConfig(this);
        registerCommand(new Command(this));
    }

}
