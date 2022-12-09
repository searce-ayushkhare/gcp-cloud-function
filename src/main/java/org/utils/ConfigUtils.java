package org.utils;

import com.google.api.gax.paging.Page;
import com.google.cloud.logging.LogEntry;
import com.google.cloud.logging.Logging;

public class ConfigUtils {
    public String getExecutionId(Logging logs) {
        Page<LogEntry> entries = logs.listLogEntries();
        String id = null;
        for (LogEntry logName : entries.iterateAll()) {
            id = logName.getLabels().get("execution_id");
            if (id != null) {
                break;
            }
        }
        return id;
    }
}
