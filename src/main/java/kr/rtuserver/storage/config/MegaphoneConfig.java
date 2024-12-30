package kr.rtuserver.storage.config;

import kr.rtuserver.framework.bukkit.api.config.RSConfiguration;
import kr.rtuserver.storage.Megaphone;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MegaphoneConfig extends RSConfiguration<Megaphone> {

    private String command = "megaphone";
    private String item = "nexo:megaphone";
    private String format = "[<red>Megaphone</red>] {message}";

    public MegaphoneConfig(Megaphone plugin) {
        super(plugin, "Megaphone.yml", 1);
        setup(this);
    }

    private void init() {
        command = getString("command", command, """
                Default command
                기본 명령어""");
        item = getString("item", item, """
                Megaphone Item
                확성기 아이템""");
        format = getString("format", format, """
                Message Format
                메시지 형식""");
    }
}
