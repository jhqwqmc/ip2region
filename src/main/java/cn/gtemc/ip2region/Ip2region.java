package cn.gtemc.ip2region;

import cn.gtemc.ip2region.api.Ip2RegionGeoLocator;
import cn.gtemc.ip2region.api.IpCZApiGeoLocator;
import cn.gtemc.ip2region.command.ReloadCommand;
import cn.gtemc.ip2region.papi.Placeholder;
import cn.gtemc.ip2region.util.ConfigUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Ip2region extends JavaPlugin implements Listener {
    public static Ip2region instance;
    private static final Placeholder placeholder = new Placeholder();

    @Override
    public void onEnable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            getLogger().severe("未安装PlaceholderAPI无法启动");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        instance = this;
        Objects.requireNonNull(getCommand("reload")).setExecutor(new ReloadCommand());
        init();
        placeholder.register();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("插件已启用");
    }

    @Override
    public void onDisable() {
        placeholder.unregister();
        getLogger().info("插件已禁用");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String parseString = "ip: %ip2region_ip% | 国家: %ip2region_country% | 省份: %ip2region_province% | 城市: %ip2region_city% | 区县: %ip2region_district% | 运营商: %ip2region_isp% | 纬度: %ip2region_latitude% | 经度: %ip2region_longitude%";
        String result = PlaceholderAPI.setPlaceholders(event.getPlayer(), parseString);
        player.sendMessage("你的位置是：\n" + result);
        getLogger().info("玩家 " + player.getName() + " 的位置是：\n" + result);
    }

    public static Ip2region getInstance() {
        return instance;
    }

    public void init() {
        ConfigUtil.createDefaultFile("config.yml");
        ConfigUtil.createDefaultFile("ip2region.xdb");
        Ip2RegionGeoLocator.init();
        IpCZApiGeoLocator.init();
        Placeholder.init();
    }

}
