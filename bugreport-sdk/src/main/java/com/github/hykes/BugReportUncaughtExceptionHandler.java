package com.github.hykes;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * @author hehaiyangwork@gmail.com
 * @date 2019-05-06 23:30:00
 */
public class BugReportUncaughtExceptionHandler implements UncaughtExceptionHandler {

    private final UncaughtExceptionHandler oldHandler;

    private static Reporter reporter = null;

    BugReportUncaughtExceptionHandler(UncaughtExceptionHandler oldHandler) {
        this.oldHandler = oldHandler;
    }

    static void enabled(Reporter e) {
        UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new BugReportUncaughtExceptionHandler(handler));
        reporter = e;
    }

    static void disabled() {
        UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
        if (handler instanceof BugReportUncaughtExceptionHandler) {
            BugReportUncaughtExceptionHandler ehandler = (BugReportUncaughtExceptionHandler)handler;
            Thread.setDefaultUncaughtExceptionHandler(ehandler.oldHandler);
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        reporter.report(t, e);
        if (this.oldHandler != null) {
            this.oldHandler.uncaughtException(t, e);
        } else {
            System.err.printf("Exception in thread \"%s\" ", t.getName());
            e.printStackTrace(System.err);
        }
    }

}
