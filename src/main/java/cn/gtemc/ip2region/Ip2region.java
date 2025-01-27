package cn.gtemc.ip2region;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ip2region extends JavaPlugin {

    @Override
    public void onEnable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            getLogger().severe("未安装PlaceholderAPI无法启动");
            this.onDisable();
        }
        getLogger().info("插件已启用");
    }

    @Override
    public void onDisable() {
        getLogger().info("插件已禁用");
    }
}
