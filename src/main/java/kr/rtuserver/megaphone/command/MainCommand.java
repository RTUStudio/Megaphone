package kr.rtuserver.megaphone.command;

import kr.rtuserver.framework.bukkit.api.command.RSCommand;
import kr.rtuserver.framework.bukkit.api.command.RSCommandData;
import kr.rtuserver.framework.bukkit.api.format.ComponentFormatter;
import kr.rtuserver.framework.bukkit.api.registry.CustomItems;
import kr.rtuserver.megaphone.Megaphone;
import kr.rtuserver.megaphone.configuration.MegaphoneConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MainCommand extends RSCommand<Megaphone> {

    private final MegaphoneConfig megaphoneConfig;

    public MainCommand(Megaphone plugin) {
        super(plugin, plugin.getMegaphoneConfig().getCommand());
        this.megaphoneConfig = plugin.getMegaphoneConfig();
    }

    @Override
    public boolean execute(RSCommandData data) {
        Player player = player();
        if (player == null) {
            chat().announce(sender(), message().getCommon("onlyPlayer"));
            return true;
        }
        ItemStack itemStack = CustomItems.from(megaphoneConfig.getItem());
        if (itemStack != null) {
            if (player.getInventory().containsAtLeast(itemStack, 1)) {
                player.getInventory().removeItem(itemStack);
                String message = String.join(" ", data.args());
                Component component = ComponentFormatter.mini(megaphoneConfig.getFormat())
                        .replaceText(TextReplacementConfig.builder().matchLiteral("{message}").replacement(message).build());
                chat().broadcastAll(component);
            } else chat().announce(player, message().get(player, "needItem"));
        } else chat().announce(player, message().get(player, "notFound.item"));
        return true;
    }

    @Override
    public void reload(RSCommandData data) {
        megaphoneConfig.reload();
    }
}
