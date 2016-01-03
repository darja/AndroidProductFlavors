package com.demos.productflavors.util;

import java.util.HashSet;

@SuppressWarnings("all")
public class DPLog {
    private static String tag = "ProductFlavors";
    private static String checkpointTag;
    private static boolean enabled = true;
    private static boolean locationEnabled = true;

    private static HashSet<String> ignoredTags = new HashSet<String>();

    public static String createTag(String tag) {
        return DPLog.tag + "." + tag;
    }

    public static void ignoreTag(String tag) {
        ignoredTags.add(tag);
    }

    private static boolean isEnabled(String tag) {
        return enabled && !ignoredTags.contains(tag);
    }

    /**
     * Set default tag, used in {@link #v}, {@link #i}, {@link #d}, {@link #w}, {@link #e}, {@link #wtf} and trace functions
     *
     * @param tag Default tag
     */
    public static void setDefaultTag(String tag) {
        DPLog.tag = tag;
        DPLog.checkpointTag = createTag("checkpoint");
    }

    /**
     * Enables or disables logging
     */
    public static void setEnabled(boolean enabled) {
        DPLog.enabled = enabled;
    }

    /**
     * Enables or disables log source display
     */
    public static void setLocationEnabled(boolean locationEnabled) {
        DPLog.locationEnabled = locationEnabled;
    }

    /**
     * Send VERBOSE formatted message with custom tag
     */
    public static void vt(String tag, String format, Object... args) {
        if (isEnabled(tag)) android.util.Log.v(tag, String.format(format, args) + getLocation());
    }

    /**
     * Send DEBUG formatted message with custom tag
     */
    public static void dt(String tag, String format, Object... args) {
        if (isEnabled(tag)) android.util.Log.d(tag, String.format(format, args) + getLocation());
    }

    /**
     * Send INFO formatted message with custom tag
     */
    public static void it(String tag, String format, Object... args) {
        if (isEnabled(tag)) android.util.Log.i(tag, String.format(format, args) + getLocation());
    }

    /**
     * Send WARN formatted message with custom tag
     */
    public static void wt(String tag, String format, Object... args) {
        if (isEnabled(tag)) android.util.Log.w(tag, String.format(format, args) + getLocation());
    }

    /**
     * Send WARN formatted message with custom tag and throwable
     */
    public static void wt(String tag, String message, Throwable e) {
        if (isEnabled(tag)) android.util.Log.w(tag, message + getLocation(), e);
    }

    /**
     * Send WARN throwable with custom tag
     */
    public static void wt(String tag, Throwable e) {
        if (isEnabled(tag)) android.util.Log.w(tag, e);
    }

    /**
     * Send ERROR throwable with custom tag
     */
    public static void et(String tag, Throwable e) {
        if (isEnabled(tag)) android.util.Log.e(tag, e.getMessage() + getLocation(), e);
    }

    /**
     * Send ERROR formatted message with custom tag
     */
    public static void et(String tag, String format, Object... args) {
        if (isEnabled(tag)) android.util.Log.e(tag, String.format(format, args) + getLocation());
    }

    /**
     * Send ASSERT formatted message with custom tag
     */
    public static void wtf_t(String tag, String format, Object... args) {
        if (isEnabled(tag)) android.util.Log.wtf(tag, String.format(format, args) + getLocation());
    }

    /**
     * Send VERBOSE formatted message with default tag
     */
    public static void v(String format, Object... args) {
        vt(tag, format, args);
    }

    /**
     * Send DEBUG formatted message with default tag
     */
    public static void d(String format, Object... args) {
        dt(tag, format, args);
    }

    /**
     * Send INFO formatted message with default tag
     */
    public static void i(String format, Object... args) {
        it(tag, format, args);
    }

    /**
     * Send WARN formatted message with default tag
     */
    public static void w(String format, Object... args) {
        wt(tag, format, args);
    }

    /**
     * Send VERBOSE message and Throwable with default tag
     */
    public static void w(String message, Throwable e) {
        wt(tag, message, e);
    }

    /**
     * Send WARN throwable with default tag
     */
    public static void w(Throwable e) {
        wt(tag, e);
    }

    /**
     * Send ERROR formatted message with default tag
     */
    public static void e(String format, Object... args) {
        et(tag, format, args);
    }

    /**
     * Send ERROR throwable with default tag
     */
    public static void e(Throwable e) {
        et(tag, e);
    }

    /**
     * Send ASSERT formatted message with default tag
     */
    public static void wtf(String format, Object... args) {
        wtf_t(tag, format, args);
    }

    /**
     * Send VERBOSE formatted message with default tag and trace
     */
    public static void vtrace(int traceLength, String format, Object... args) {
        vtrace(tag, traceLength, format, args);
    }

    /**
     * Send DEBUG formatted message with default tag and trace
     */
    public static void dtrace(int traceLength, String format, Object... args) {
        dtrace(tag, traceLength, format, args);
    }

    /**
     * Send INFO formatted message with default tag and trace
     */
    public static void itrace(int traceLength, String format, Object... args) {
        itrace(tag, traceLength, format, args);
    }

    /**
     * Send WARN formatted message with default tag and trace
     */
    public static void wtrace(int traceLength, String format, Object... args) {
        wtrace(tag, traceLength, format, args);
    }

    /**
     * Send ERROR formatted message with default tag and trace
     */
    public static void etrace(int traceLength, String format, Object... args) {
        etrace(tag, traceLength, format, args);
    }

    /**
     * Send VERBOSE formatted message with custom tag and trace
     */
    public static void vtrace(String tag, int traceLength, String format, Object... args) {
        if (isEnabled(tag)) android.util.Log.v(tag, String.format(format, args) + getTrace(traceLength));
    }

    /**
     * Send DEBUG formatted message with custom tag and trace
     */
    public static void dtrace(String tag, int traceLength, String format, Object... args) {
        if (isEnabled(tag)) android.util.Log.d(tag, String.format(format, args) + getTrace(traceLength));
    }

    /**
     * Send INFO formatted message with custom tag and trace
     */
    public static void itrace(String tag, int traceLength, String format, Object... args) {
        if (isEnabled(tag)) android.util.Log.i(tag, String.format(format, args) + getTrace(traceLength));
    }

    /**
     * Send WARN formatted message with custom tag and trace
     */
    public static void wtrace(String tag, int traceLength, String format, Object... args) {
        if (isEnabled(tag)) android.util.Log.w(tag, String.format(format, args) + getTrace(traceLength));
    }

    /**
     * Send ERROR formatted message with custom tag and trace
     */
    public static void etrace(String tag, int traceLength, String format, Object... args) {
        if (isEnabled(tag)) android.util.Log.e(tag, String.format(format, args) + getTrace(traceLength));
    }

    /**
     * {@link #checkpoint(String)} with default tag
     */
    public static void checkpoint() {
        checkpoint(checkpointTag);
    }

    /**
     * Send VERBOSE checkpoint with custom tag
     * <p>Checkpoint format: <code>ClassName.method()</code></p>
     */
    public static void checkpoint(String tag) {
        if (!isEnabled(tag)) {
            return;
        }

        final String logClassName = DPLog.class.getName();
        final StackTraceElement[] traces = Thread.currentThread().getStackTrace();
        int foundIndex = -1;

        for (int i = 0; i < traces.length; i++) {
            StackTraceElement trace = traces[i];

            if (trace.getClassName().startsWith(logClassName)) {
                foundIndex = i;
            } else {
                if (foundIndex > 0)
                    break;
            }
        }

        StackTraceElement trace = traces[foundIndex + 1];
        String message = String.format("%s.%s (%s:%s)", trace.getClassName(), trace.getMethodName(),
            trace.getFileName(), trace.getLineNumber());

        android.util.Log.v(tag, message);
    }

    private static String getTrace(int length) {
        if (!locationEnabled)
            return "";

        final String logClassName = DPLog.class.getName();
        final StackTraceElement[] traces = Thread.currentThread().getStackTrace();
        int foundIndex = -1;

        for (int i = 0; i < traces.length; i++) {
            StackTraceElement trace = traces[i];

            if (trace.getClassName().startsWith(logClassName)) {
                foundIndex = i;
            } else {
                if (foundIndex > 0)
                    break;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int i = foundIndex + 1; i < foundIndex + length + 1; ++i) {
            if (i > traces.length)
                break;

            StackTraceElement trace = traces[i];
            sb.append(String.format("    at %s.%s (%s:%s)\n",
                trace.getClassName(), trace.getMethodName(),
                trace.getFileName(), trace.getLineNumber()));
        }
        sb.delete(sb.length() - 1, sb.length());
        return "\n" + sb.toString();
    }

    private static String getLocation() {
        if (!locationEnabled)
            return "";

        final String logClassName = DPLog.class.getName();
        final StackTraceElement[] traces = Thread.currentThread().getStackTrace();
        boolean found = false;

        for (int i = 0; i < traces.length; i++) {
            StackTraceElement trace = traces[i];

            if (found) {
                if (!trace.getClassName().startsWith(logClassName)) {
                    return String.format(" (%s:%d)", trace.getFileName(), trace.getLineNumber());
                }
            } else if (trace.getClassName().startsWith(logClassName)) {
                found = true;
            }
        }

        return " (?)";
    }
}
