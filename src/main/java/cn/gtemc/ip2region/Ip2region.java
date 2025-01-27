package cn.gtemc.ip2region;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.lionsoul.ip2region.xdb.Searcher;

import java.io.IOException;

public final class Ip2region extends JavaPlugin {
    public static Ip2region instance;
    public static Searcher searcher;

    @Override
    public void onEnable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            getLogger().severe("未安装PlaceholderAPI无法启动");
            this.onDisable();
        }
        instance = this;
        try {
            searcher = Searcher.newWithFileOnly(getDataFolder().getAbsolutePath() + "/ip2region.xdb");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getLogger().info("插件已启用");
    }

    @Override
    public void onDisable() {
        getLogger().info("插件已禁用");
    }

    public static Ip2region getInstance() {
        return instance;
    }

    public static Searcher getSearcher() {
        return searcher;
    }
}
