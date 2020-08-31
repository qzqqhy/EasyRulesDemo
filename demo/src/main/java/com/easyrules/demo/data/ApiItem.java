package com.easyrules.demo.data;

/**
 * @ClassName：ApiItem
 * @Description： <p> api item </p>
 * @Author： - liuxiu
 * @CreatTime：2020-08-31 - 23:27
 * @Modify By：
 * @ModifyTime： 2020-08-31
 * @Modify marker：
 * @version V1.0
*/
public class ApiItem {
    private String code;
    private String url;
    private String appid;
    private String appSecret;

    public ApiItem() {
    }

    public ApiItem(String code, String url, String appid, String appSecret) {
        this.code = code;
        this.url = url;
        this.appid = appid;
        this.appSecret = appSecret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
