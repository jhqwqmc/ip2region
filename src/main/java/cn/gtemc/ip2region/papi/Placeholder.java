package cn.gtemc.ip2region.papi;

import cn.gtemc.ip2region.api.Ip2RegionGeoLocator;
import cn.gtemc.ip2region.api.IpCZApiGeoLocator;
import cn.gtemc.ip2region.util.ConfigUtil;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Placeholder extends PlaceholderExpansion {
    private static Ip2RegionGeoLocator ip2regionGeoLocator;
    private static IpCZApiGeoLocator ipCZApiGeoLocator;

    public static void init() {
        ip2regionGeoLocator = new Ip2RegionGeoLocator();
        ipCZApiGeoLocator = new IpCZApiGeoLocator();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "ip2region";
    }

    @Override
    public @NotNull String getAuthor() {
        return "jhqwqmc";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.0.1";
    }

    @Override
    public String onRequest(@NotNull OfflinePlayer player, @NotNull String params) {
        String ip = Objects.requireNonNull(Objects.requireNonNull(player.getPlayer()).getAddress()).getAddress().getHostAddress();
        if (params.equals("ip")) {
            return ip;
        }
        var ipGeoInfo = switch (ConfigUtil.getConfigInfo().querySource()) {
            case "ip2region" -> ip2regionGeoLocator;
            case "czApi" -> ipCZApiGeoLocator;
            default -> throw new IllegalStateException("Unexpected value: " + ConfigUtil.getConfigInfo().querySource());
        };
        return switch (params) {
            case "country" -> ipGeoInfo.getIpGeoInfo(ip).country;
            case "province" -> ipGeoInfo.getIpGeoInfo(ip).province;
            case "city" -> ipGeoInfo.getIpGeoInfo(ip).city;
            case "district" -> ipGeoInfo.getIpGeoInfo(ip).district;
            case "isp" -> ipGeoInfo.getIpGeoInfo(ip).isp;
            case "latitude" -> String.valueOf(ipGeoInfo.getIpGeoInfo(ip).latitude);
            case "longitude" -> String.valueOf(ipGeoInfo.getIpGeoInfo(ip).longitude);
            default -> null;
        };
    }

}
