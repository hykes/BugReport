package com.github.hykes;

/**
 * @author hehaiyangwork@gmail.com
 * @date 2019-05-06 17:06:00
 */
public class BugReport {

    private Reporter reporter;

    public BugReport(Reporter reporter) {
        this.reporter = reporter;
        BugReportUncaughtExceptionHandler.enabled(reporter);
    }

    private BugReport() {}

    public void report(Throwable e) {
        this.reporter.report(e);
    }

    public void notify(String title, String content) {
        this.reporter.notify(title, content);
    }

}
