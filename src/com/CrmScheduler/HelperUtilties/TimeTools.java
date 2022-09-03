package com.CrmScheduler.HelperUtilties;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

/**
 * Collection of helper methods  to convert times between timezones
 */
public class TimeTools {


    /**
     * Converts a local time to UTC time
     * @param date the timestamp to convert to Utc
     * @return a timestamp with the value adjusted to the UTC zone
     */
    public static Timestamp ConvertDateToUTC(Timestamp date)
    {
        LocalDateTime localDateTime = date.toLocalDateTime();
        ZonedDateTime localZonedTime = localDateTime.atZone(ZoneId.systemDefault());
        TimeZone timeZone = TimeZone.getTimeZone(localZonedTime.getZone().getId());

        Date d = Date.from(localZonedTime.toInstant());
        if(timeZone.inDaylightTime(d))
            localZonedTime.minusHours(1);
        else
            localZonedTime.plusHours(1);


        ZonedDateTime utcTime = localZonedTime.withZoneSameInstant(ZoneId.of("UTC"));
        return Timestamp.valueOf(utcTime.toLocalDateTime());
    }


    /**
     * converts a timestamp at utc time to EST time
     * @param date the timestamp to convert to EST
     * @return a timestamp with its value adjusted from UTC to EST
     */
    public static Timestamp ConvertUtcToEst(Timestamp date) {
        LocalDateTime utcDateTime = date.toLocalDateTime();
        ZonedDateTime utcZonedTime = utcDateTime.atZone(ZoneId.of("UTC"));
        TimeZone timeZone = TimeZone.getTimeZone(utcZonedTime.getZone().getId());
        Date d = Date.from(utcZonedTime.toInstant());
        if (timeZone.inDaylightTime(d))
            utcZonedTime.minusHours(1);
        else
            utcZonedTime.plusHours(1);
        ZonedDateTime eastCoastTime = utcZonedTime.withZoneSameInstant(ZoneId.of("America/New_York"));
        LocalDateTime localDateTime = eastCoastTime.toLocalDateTime();

        Timestamp convertedTime = Timestamp.valueOf(localDateTime);
        return convertedTime;
    }

    /**
     * Converts a UTC timestamp to system time
     * @param date the timestamp to convert from UTC to local time
     * @return a timestamp with its value adjusted from UTC to EST
     */
    public static Timestamp ConvertUtcToSystemTime(Timestamp date)
    {
        LocalDateTime utcDateTime = date.toLocalDateTime();
        ZonedDateTime utcZonedTime = utcDateTime.atZone(ZoneId.of("UTC"));
        TimeZone timeZone = TimeZone.getTimeZone(utcZonedTime.getZone().getId());
        Date d = Date.from(utcZonedTime.toInstant());
        if(timeZone.inDaylightTime(d))
            utcZonedTime.minusHours(1);
        else
            utcZonedTime.plusHours(1);
        return Timestamp.from(utcZonedTime.withZoneSameInstant(ZoneId.systemDefault()).toInstant());
    }
}

