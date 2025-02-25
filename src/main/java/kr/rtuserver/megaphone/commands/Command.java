package kr.rtuserver.megaphone.commands;

import kr.rtuserver.framework.bukkit.api.command.RSCommand;
import kr.rtuserver.framework.bukkit.api.command.RSCommandData;
import kr.rtuserver.framework.bukkit.api.registry.CustomItems;
import kr.rtuserver.framework.bukkit.api.utility.format.ComponentFormatter;
import kr.rtuserver.framework.bukkit.api.utility.player.PlayerChat;
import kr.rtuserver.megaphone.Megaphone;
import kr.rtuserver.megaphone.config.MegaphoneConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Command extends RSCommand<Megaphone> {

    private final MegaphoneConfig megaphoneConfig;

    public Command(Megaphone plugin) {
        super(plugin, plugin.getMegaphoneConfig().getCommand());
        this.megaphoneConfig = plugin.getMegaphoneConfig();
    }

    @Override
    public boolean execute(RSCommandData data) {
        PlayerChat chat = PlayerChat.of(getPlugin());
        if (getSender() instanceof Player player) {
            ItemStack itemStack = CustomItems.from(megaphoneConfig.getItem());
            if (itemStack != null) {
                if (player.getInventory().containsAtLeast(itemStack, 1)) {
                    player.getInventory().removeItem(itemStack);
                    String message = String.join(" ", data.args());
                    Component component = ComponentFormatter.mini(megaphoneConfig.getFormat())
                            .replaceText(TextReplacementConfig.builder().matchLiteral("{message}").replacement(message).build());
                    chat.broadcastAll(component);
                } else chat.announce(player, getMessage().get(player, "needItem"));
            } else chat.announce(player, getMessage().get(player, "notFound.item"));
        } else chat.announce(getSender(), getCommon().getMessage("onlyPlayer"));
        return true;
    }

    @Override
    public void reload(RSCommandData data) {
        megaphoneConfig.reload();
    }
}
