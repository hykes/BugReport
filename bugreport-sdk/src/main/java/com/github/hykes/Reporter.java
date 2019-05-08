package com.github.hykes;

/**
 * @author hehaiyangwork@gmail.com
 * @date 2019-05-08 03:07:00
 */

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Reporter {

    private BugReportProperties bugReportProperties;

    private Reporter() {}

    public Reporter(BugReportProperties bugReportProperties) {
        this.bugReportProperties = bugReportProperties;
        this.checkProperties();
        this.initialize();
    }

    /**
     * basic setting
     */
    private Map<String, Object> extraMap;
    private String type;

    /**
     * Device information
     */
    private String hostname;
    private String osName;
    private String osVersion;
    private String osArch;
    private String runtimeName;
    private String runtimeVersion;
    private String locale;

    /**
     * error information
     */
    private String threadName;
    private String stackTrace;
    private String title;
    private String context;

    private void initialize(){
        this.hostname = getHostname();
        this.osName = System.getProperty("os.name");
        this.osVersion = System.getProperty("os.version");
        this.osArch = System.getProperty("os.arch");
        this.runtimeName = System.getProperty("java.runtime.name");
        this.runtimeVersion = System.getProperty("java.runtime.version");
        this.locale = Locale.getDefault().toString();
    }

    private void checkProperties() {

    }

    private String getHostname() {
        // windows
        if (System.getProperty("os.name").startsWith("Windows")) {
            return System.getenv("COMPUTERNAME");
        }

        // Linux system
        String hostname = System.getenv("HOSTNAME");
        if (hostname != null) {
            return hostname;
        }

        // Resort to dns hostname lookup
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            // Give up
            ex.printStackTrace();
        }
        return "Unknown";
    }


    public void report(Throwable e)  {
        this.setStackTrace(e);
        try {
            this.send();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void report(Thread t, Throwable e) {
        this.type = "Exception";
        this.setStackTrace(e);
        this.setThreadName(t);
        try {
            this.send();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void notify(String title, String context) {
        this.title = title;
        this.context = context;
        this.type = "Notification";
        try {
            this.send();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void setThreadName(Thread t){
        this.threadName = t.getName();
    }

    public void setStackTrace(Throwable e){
        this.stackTrace = this.stackTraceToString(e);
    }

    public void setExtraMap(Map<String, Object> extraMap) {
        this.extraMap = extraMap;
    }

    private String stackTraceToString(Throwable e){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public void send() throws IOException{
        if (Objects.equals(false, bugReportProperties.getEnabled())) {
            return;
        }
        this.post(this);
    }

    private void post(Reporter reporter) throws IOException {
        URL url = new URL(bugReportProperties.getUrl());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept-Charset", "utf-8");
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setRequestProperty("Accept", "application/json");

        OutputStream out = connection.getOutputStream();

        out.write(buildObj().toString().getBytes("utf-8"));
        out.close();
        int statusCode = connection.getResponseCode();
        if (statusCode == 200) {
            System.out.println("Send Error to Fundebug success!");
        }
    }

    private JSONObject buildObj () {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appId", bugReportProperties.getAppId());
        jsonObject.put("token", bugReportProperties.getToken());
        jsonObject.put("url", bugReportProperties.getUrl());
        jsonObject.put("environment", bugReportProperties.getEnvironment());
        jsonObject.put("hostname", this.hostname);
        jsonObject.put("osName", this.osName);
        jsonObject.put("osVersion", this.osVersion);
        jsonObject.put("osArch", this.osArch);
        jsonObject.put("runtimeName", this.runtimeName);
        jsonObject.put("runtimeVersion", this.runtimeVersion);
        jsonObject.put("locale", this.locale);
        jsonObject.put("threadName", this.threadName);
        jsonObject.put("stackTrace", this.stackTrace);
        return jsonObject;
    }

}