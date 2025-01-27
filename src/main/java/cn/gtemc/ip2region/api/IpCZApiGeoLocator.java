package cn.gtemc.ip2region.api;

import cn.gtemc.ip2region.model.IpGeoInfo;
import cn.gtemc.ip2region.util.ConfigUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class IpCZApiGeoLocator implements IpGeoLocator {
    private static final String host = "https://cz88geoaliyun.cz88.net/search/ip/geo";
    private static String appcode;

    public static void init() {
        appcode = ConfigUtil.getConfigInfo().czApiAppCode();
    }

    @Override
    public IpGeoInfo getIpGeoInfo(String ip) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(host + "?ip=" + ip))
                .header("Authorization", "APPCODE " + appcode)
                .GET()
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (response.statusCode() != 200) {
            throw new RuntimeException("请求失败，状态码：" + response.statusCode());
        }
        String jsonResponse = response.body();
        JsonObject rootNode = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonObject dataNode = rootNode.getAsJsonObject("data");
        if (Objects.equals(dataNode.get("country").getAsString(), "IANA未分配")
                || Objects.equals(ip, "255.255.255.255")) {
            return IpGeoInfo.getLocal(ip);
        }
        IpGeoInfo ipGeoInfo = IpGeoInfo.getDefault();
        ipGeoInfo.setIp(dataNode.get("ip").getAsString());
        ipGeoInfo.setCountry(dataNode.get("country").getAsString());
        ipGeoInfo.setProvince(dataNode.get("province").getAsString());
        ipGeoInfo.setCity(dataNode.get("city").getAsString());
        ipGeoInfo.setDistrict(dataNode.get("districts").getAsString());
        ipGeoInfo.setIsp(dataNode.get("isp").getAsString());
        ipGeoInfo.setLatitude(dataNode.getAsJsonObject("geoCenter").get("latitude").getAsDouble());
        ipGeoInfo.setLongitude(dataNode.getAsJsonObject("geoCenter").get("longitude").getAsDouble());
        return ipGeoInfo;
    }
}
