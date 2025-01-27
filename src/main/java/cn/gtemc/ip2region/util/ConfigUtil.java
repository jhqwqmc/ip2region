package cn.gtemc.ip2region.util;

import cn.gtemc.ip2region.Ip2region;
import cn.gtemc.ip2region.model.ConfigInfo;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.*;

public class ConfigUtil {
    private static final Ip2region instance = Ip2region.getInstance();

    public static void createDefaultFile(String fileName) {
        if (!instance.getDataFolder().exists() && !instance.getDataFolder().mkdirs()) {
            instance.getLogger().warning("无法创建插件文件夹！");
            return;
        }
        File file = new File(instance.getDataFolder(), fileName);
        if (!file.exists()) {
            try (InputStream in = instance.getResource(fileName);
                 OutputStream out = new FileOutputStream(file)) {
                if (in == null) {
                    instance.getLogger().warning(fileName + " 文件未找到！");
                    return;
                }

                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            } catch (IOException e) {
                instance.getLogger().warning("无法复制 " + fileName + " 文件！");
            }
        }
    }

    public static ConfigInfo getConfigInfo() {
        FileConfiguration config = instance.getConfig();
        return new ConfigInfo(
                config.getString("query-source"),
                config.getString("czApi-appcode")
        );
    }
}
