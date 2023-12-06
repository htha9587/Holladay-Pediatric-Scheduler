package util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Util class that converts the scheduler time to UTC Form.
 *
 * @author Harrison Thacker
 */
public class UTCConverter {

    /**
     * Converts the scheduler time to UTC, and then finally out as LocalDateTime.
     * @param schedulerDateTime = String LocalDateTime formatter for the scheduler time.
     * @return schedulerDateTime
     */
    public static String UTCToLDTConverter(String schedulerDateTime)
    {
        Timestamp timestamp = Timestamp.valueOf(String.valueOf(schedulerDateTime));
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime schedulerUTC = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC")); //Converts to UTC.
        LocalDateTime ldtOutput = schedulerUTC.toLocalDateTime();

        ZonedDateTime zdtOutput = ldtOutput.atZone(ZoneId.of("UTC"));
        ZonedDateTime schedulerZDT = zdtOutput.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        LocalDateTime ldtEnd = schedulerZDT.toLocalDateTime(); //Finally output and converted to LocalDateTime.
        schedulerDateTime = ldtEnd.format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss"));
        return schedulerDateTime;
    }

}
