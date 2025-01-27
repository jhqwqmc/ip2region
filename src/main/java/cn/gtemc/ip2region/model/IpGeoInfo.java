package cn.gtemc.ip2region.model;

public class IpGeoInfo {
    public String ip;
    public String country;
    public String province;
    public String city;
    public String district;
    public String isp;
    public Object latitude;
    public Object longitude;

    public IpGeoInfo(
            String ip,
            String country,
            String province,
            String city,
            String district,
            String isp,
            Object latitude,
            Object longitude
    ) {
        this.ip = ip;
        this.country = country;
        this.province = province;
        this.city = city;
        this.district = district;
        this.isp = isp;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return String.format("ip: %s, 国家: %s, 省份: %s, 城市: %s, 区县: %s, 运营商: %s, 纬度: %s, 经度: %s",
                ip, country, province, city, district, isp, latitude.toString(), longitude.toString());
    }

    public static IpGeoInfo getDefault() {
        return new IpGeoInfo(
                "0.0.0.0",
                "未知",
                "未知",
                "未知",
                "未知",
                "未知",
                "未知",
                "未知"
        );
    }

    public static IpGeoInfo getLocal(String ip) {
        return new IpGeoInfo(
                ip,
                "内网",
                "内网",
                "内网",
                "内网",
                "内网",
                "内网",
                "内网"
        );
    }

    public void setIp(String ip) {
        if (ip == null || ip.isEmpty() || ip.equals("0")) return;
        this.ip = ip;
    }

    public void setCountry(String country) {
        if (country == null || country.isEmpty() || country.equals("0")) return;
        this.country = country;
    }

    public void setProvince(String province) {
        if (province == null || province.isEmpty() || province.equals("0")) return;
        this.province = province;
    }

    public void setCity(String city) {
        if (city == null || city.isEmpty() || city.equals("0")) return;
        this.city = city;
    }

    public void setDistrict(String district) {
        if (district == null || district.isEmpty() || district.equals("0")) return;
        this.district = district;
    }

    public void setIsp(String isp) {
        if (isp == null || isp.isEmpty() || isp.equals("0")) return;
        this.isp = isp;
    }

    public void setLatitude(double latitude) {
        if (latitude > 90 || latitude < -90) return;
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        if (longitude > 180 || longitude < -180) return;
        this.longitude = longitude;
    }

}
