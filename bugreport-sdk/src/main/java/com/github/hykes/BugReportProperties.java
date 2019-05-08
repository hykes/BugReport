package com.github.hykes;

import java.io.Serializable;

/**
 * @author hehaiyangwork@gmail.com
 * @date 2019-05-06 17:59:00
 */
public class BugReportProperties implements Serializable {

    private static final long serialVersionUID = 702534274441057992L;

    private String appId;

    private String token;

    private String url;

    /**
     * 默认 default
     */
    private String environment = "default";

    /**
     * 默认 10
     */
    private Integer queueSize = 100;

    /**
     * 默认 true
     */
    private Boolean enabled = true;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public Integer getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Integer queueSize) {
        this.queueSize = queueSize;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
