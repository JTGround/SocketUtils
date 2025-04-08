package runner;

import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class DefaultLogFormatter extends SimpleFormatter {
    @Override
    public String format(LogRecord record) {
        String format = "[%1$tF %1$tT.%1$tL] %2$s: %3$s %n";
        return String.format(format, new Date(record.getMillis()), record.getLevel().getLocalizedName(), record.getMessage());
    }
}
