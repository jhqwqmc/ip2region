package cn.gtemc.ip2region.api;

import cn.gtemc.ip2region.Ip2region;
import cn.gtemc.ip2region.model.IpGeoInfo;
import org.lionsoul.ip2region.xdb.Searcher;

import java.io.IOException;

public class Ip2RegionGeoLocator implements IpGeoLocator {
    private static Searcher searcher;

    public static void init() {
        try {
            byte[] cBuff = Searcher.loadContentFromFile(
                    Ip2region.getInstance().getDataFolder().getAbsolutePath()
                            + "/ip2region.xdb");
            Ip2RegionGeoLocator.searcher = Searcher.newWithBuffer(cBuff);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public IpGeoInfo getIpGeoInfo(String ip) {
        IpGeoInfo ipGeoInfo = IpGeoInfo.getDefault();
        ipGeoInfo.setIp(ip);
        try {
            String stringResults = searcher.search(ip);
            String[] results = stringResults.split("\\|");
            ipGeoInfo.setCountry(results[0]);
            ipGeoInfo.setDistrict(results[1]);
            ipGeoInfo.setProvince(results[2]);
            ipGeoInfo.setCity(results[3]);
            ipGeoInfo.setIsp(results[4]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ipGeoInfo;
    }
}
