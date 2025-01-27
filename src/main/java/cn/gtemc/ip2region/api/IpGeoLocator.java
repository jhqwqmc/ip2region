package cn.gtemc.ip2region.api;

import cn.gtemc.ip2region.model.IpGeoInfo;

public interface IpGeoLocator {
    /**
     * 获取IP地址对应的地理位置信息
     *
     * @param ip IP地址
     * @return IP地理位置信息
     */
    IpGeoInfo getIpGeoInfo(String ip);
}
