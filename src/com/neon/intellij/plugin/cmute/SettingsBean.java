package com.neon.intellij.plugin.cmute;

/**
 * User: laught
 * Date: 09-10-2011 Time: 23:32
 */
public class SettingsBean
{

    private String host;
    private int port;
    private String service;
    private String username;
    private String password;

    public SettingsBean()
    {

    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
