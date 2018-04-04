package com.fairytale.fortunetarot.entity;

/**
 * Created by lizhen on 2018/4/2.
 */

public class BaseConfigEntity extends BaseEntity {
    private String version;
    private String versioncode;
    private String versioninfo;
    private String ishaoping;
    private String indexres = "a";
    private String isusefont = "1";
    private String price;
    private String qqqun;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(String versioncode) {
        this.versioncode = versioncode;
    }

    public String getVersioninfo() {
        return versioninfo;
    }

    public void setVersioninfo(String versioninfo) {
        this.versioninfo = versioninfo;
    }

    public String getIshaoping() {
        return ishaoping;
    }

    public void setIshaoping(String ishaoping) {
        this.ishaoping = ishaoping;
    }

    public String getIndexres() {
        return indexres;
    }

    public void setIndexres(String indexres) {
        this.indexres = indexres;
    }

    public String getIsusefont() {
        return isusefont;
    }

    public void setIsusefont(String isusefont) {
        this.isusefont = isusefont;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQqqun() {
        return qqqun;
    }

    public void setQqqun(String qqqun) {
        this.qqqun = qqqun;
    }
}
