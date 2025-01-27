package cn.gtemc.ip2region.command;

import cn.gtemc.ip2region.Ip2region;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try {
            Ip2region.getInstance().reloadConfig();
            Component parsed = MiniMessage.miniMessage().deserialize("<green>重载成功");
            sender.sendMessage(parsed);
            return true;
        } catch (Exception e) {
            Component parsed = MiniMessage.miniMessage().deserialize("<red>执行命令时出现错误：" + e.getMessage());
            sender.sendMessage(parsed);
            return false;
        }
    }
}
