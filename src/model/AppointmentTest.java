package model;

import Utilities.TimeTools;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppointmentTest {

    /**
     * Sets the start time for the appointment to 9:30:00 AM, on july 4th, and the end time to 9:45:00 on the same day.
     * This appointment should be within business hours.
     */
    @Test
    void isWithinBusinessHours() {
        Appointment testAppointment = new Appointment();
        Timestamp startDate = Timestamp.from(Instant.ofEpochSecond(1656952200));
        Timestamp endDate = Timestamp.from(Instant.ofEpochSecond(1656953100));

        startDate = TimeTools.ConvertDateToUTC(startDate);
        testAppointment.setStart(Timestamp.from(startDate.toInstant()));

        endDate = TimeTools.ConvertDateToUTC(endDate);
        testAppointment.setEnd(Timestamp.from(endDate.toInstant()));

        assertTrue(testAppointment.isWithinBusinessHours(testAppointment));
    }

    /**
     * Sets the start date to july 5th, 12:00:00 AM. The start date is set to 12:15:00 AM on the same day.
     * This appointment is not within business hours.
     */
    @Test
    void isNotWithinBusinessHours()
    {
        Appointment testAppointment = new Appointment();
        Timestamp startDate = Timestamp.from(Instant.ofEpochSecond(1657004400));
        Timestamp endDate = Timestamp.from(Instant.ofEpochSecond(1657005300));

        startDate = TimeTools.ConvertDateToUTC(startDate);
        testAppointment.setStart(Timestamp.from(startDate.toInstant()));

        endDate = TimeTools.ConvertDateToUTC(endDate);
        testAppointment.setEnd(Timestamp.from(endDate.toInstant()));

        assertFalse(testAppointment.isWithinBusinessHours(testAppointment));
    }

    /**
     * Sets the start time for the appointment to 9:30:00 AM, on july 4th, then compares it to one epoch second behind the start time.
     * a successful validation will return true in this capse
     *
     */
    @Test
    void appointmentIsAfter()
    {
        Appointment testAppointment = new Appointment();
        Timestamp startDate = Timestamp.from(Instant.ofEpochSecond(1656952200));

        startDate = TimeTools.ConvertDateToUTC(startDate);
        testAppointment.setStart(Timestamp.from(startDate.toInstant()));

        long appointmentStartInEpoch = testAppointment.getStart().toInstant().toEpochMilli();
        boolean result = testAppointment.isAfter.test(Timestamp.from(Instant.ofEpochSecond(appointmentStartInEpoch)), Timestamp.from(Instant.ofEpochSecond(appointmentStartInEpoch - 1)));

        assertTrue(result);
    }

    /**
     * Sets the start time for the appointment to 9:30:00 AM, on july 4th. Then compares it with a time that is one epoch second ahead.
     * The test makes sure that isAfter returns false when the appointment is not after the specified time.
     */
    @Test
    void appointmentIsNotAfter()
    {
        Appointment testAppointment = new Appointment();
        Timestamp startDate = Timestamp.from(Instant.ofEpochSecond(1656952200));

        startDate = TimeTools.ConvertDateToUTC(startDate);
        testAppointment.setStart(Timestamp.from(startDate.toInstant()));

        long appointmentStartInEpoch = testAppointment.getStart().toInstant().toEpochMilli();
        boolean result = testAppointment.isAfter.test(Timestamp.from(Instant.ofEpochSecond(appointmentStartInEpoch)), Timestamp.from(Instant.ofEpochSecond(appointmentStartInEpoch + 1)));
        assertFalse(result);
    }

    /**
     * Sets the start time for the appointment to 9:30:00 AM, on july 4th. Then compares it with one epoch second ahead.
     * This test will ensure that isBefore will return true when the appointment does occur before the compared appointment time.
     */
    @Test
    void appointmentIsBefore()
    {
        Appointment testAppointment = new Appointment();
        Timestamp startDate = Timestamp.from(Instant.ofEpochSecond(1656952200));

        startDate = TimeTools.ConvertDateToUTC(startDate);
        testAppointment.setStart(Timestamp.from(startDate.toInstant()));

        long appointmentStartInEpoch = testAppointment.getStart().toInstant().toEpochMilli();
        boolean result = testAppointment.isBefore.test(Timestamp.from(Instant.ofEpochSecond(appointmentStartInEpoch)), Timestamp.from(Instant.ofEpochSecond(appointmentStartInEpoch + 1)));
        assertTrue(result);
    }

    /**
     * Sets the start time for the appointment to 9:30:00 AM, on july 4th. Then compares it with one epoch second ahead.
     * This test will ensure that isBefore will return false when the appointment does not occur before the compared appointment time.
     */
    @Test
    void appointmentIsNotBefore()
    {
        Appointment testAppointment = new Appointment();
        Timestamp startDate = Timestamp.from(Instant.ofEpochSecond(1656952200));

        startDate = TimeTools.ConvertDateToUTC(startDate);
        testAppointment.setStart(Timestamp.from(startDate.toInstant()));

        long appointmentStartInEpoch = testAppointment.getStart().toInstant().toEpochMilli();
        // boolean result = testAppointment.isAfter.test(Timestamp.from(Instant.ofEpochSecond(1657038540)), Timestamp.from(Instant.ofEpochSecond(1656953099)));
        boolean result = testAppointment.isBefore.test(Timestamp.from(Instant.ofEpochSecond(appointmentStartInEpoch)), Timestamp.from(Instant.ofEpochSecond(appointmentStartInEpoch - 1)));
        assertFalse(result);
    }

    /**
     * Sets the start time for the appointment to 9:30:00 AM and then increases the time by one epoch second. The end date is set to 9:30:00 AM (before the start occurs)
     * this predicate will return true if the appointment is invalid because the start date occurs at a time later than the end date.
     */
    @Test
    void appointmentEndsAfterStart()
    {
        Appointment testAppointment = new Appointment();
        Timestamp startDate = Timestamp.from(Instant.ofEpochSecond(1657004401));
        Timestamp endDate = Timestamp.from(Instant.ofEpochSecond(1657004400));
        testAppointment.setStart(startDate);
        testAppointment.setEnd(endDate);

        boolean result = testAppointment.endsAfterStart.test(testAppointment);
        assertTrue(result);
    }

    /**
     * Sets the start time for the appointment to 9:30:00 AM. The end date is set to 9:30:00 AM and increased by one epoch second (after the start occurs).
     * this predicate will return false if the appointment is correctly timed because the start date occurs before the end date.
     */
    @Test
    void appointmentDoesNotEndsAfterStart()
    {
        Appointment testAppointment = new Appointment();
        Timestamp startDate = Timestamp.from(Instant.ofEpochSecond(1657004400));
        Timestamp endDate = Timestamp.from(Instant.ofEpochSecond(1657004401));
        testAppointment.setStart(startDate);
        testAppointment.setEnd(endDate);

        boolean result = testAppointment.endsAfterStart.test(testAppointment);
        assertFalse(result);
    }

    /**
     * Sets the start time for the appointment to 9:30:00 AM. The end date is set to 9:30:00 AM so that the start and end time are exactly the same.
     * this predicate should return true if the times are the same.
     */
    @Test
    void appointmentStartAndEndSame()
    {
        Appointment testAppointment = new Appointment();
        Timestamp startDate = Timestamp.from(Instant.ofEpochSecond(1657004400));
        Timestamp endDate = Timestamp.from(Instant.ofEpochSecond(1657004400));
        testAppointment.setStart(startDate);
        testAppointment.setEnd(endDate);

        boolean result = testAppointment.startAndEndSame.test(testAppointment);

        assertTrue(result);
    }

    /**
     * Sets the start time for the appointment to 9:30:00 AM. The end date is set to 9:30:00 AM plus one epoch second so that the start and end time are not the same.
     * this predicate should return false if the times are not the same.
     */
    @Test
    void appointmentStartAndEndAreNotSame()
    {
        Appointment testAppointment = new Appointment();
        Timestamp startDate = Timestamp.from(Instant.ofEpochSecond(1657004400));
        Timestamp endDate = Timestamp.from(Instant.ofEpochSecond(1657004401));
        testAppointment.setStart(startDate);
        testAppointment.setEnd(endDate);

        boolean result = testAppointment.startAndEndSame.test(testAppointment);

        assertFalse(result);
    }

    /**
     * creates two appointments with the same customer registered to them and checks if they are conflicting.
     * the first appointment starts at 9:30:00 AM, and ends at 9:45:00 AM. The second appointment begins at 9:35:00 AM, and ends at 9:50:00 AM.
     * if the predicate functions correctly, a false value will be returned.
     */
    @Test
    void isConflictingWithCustomer()
    {
        Appointment testAppointment = new Appointment();
        Timestamp startDate = Timestamp.from(Instant.ofEpochSecond(1656952200));
        Timestamp endDate = Timestamp.from(Instant.ofEpochSecond(1656953100));
        testAppointment.setStart(startDate);
        testAppointment.setEnd(endDate);
        testAppointment.setCustomerId(1);
        testAppointment.setAppointmentId(1);

        Appointment conflictingTestAppointment = new Appointment();
        Timestamp conflictingStartDate = Timestamp.from(Instant.ofEpochSecond(1657082100));
        Timestamp conflictingEndDate = Timestamp.from(Instant.ofEpochSecond(1657083000));
        conflictingTestAppointment.setStart(conflictingStartDate);
        conflictingTestAppointment.setEnd(conflictingEndDate);
        conflictingTestAppointment.setCustomerId(1);
        conflictingTestAppointment.setAppointmentId(2);

        boolean result = testAppointment.isConflictingWithCustomer.test(testAppointment,conflictingTestAppointment);
        assertFalse(result);
    }

    /**
     * creates two appointments with the same customer registered to them and checks if they are conflicting.
     * the first appointment starts at 9:30:00 AM, and ends at 9:45:00 AM. The second appointment begins at 9:35:00 AM, and ends at 9:50:00 AM.
     * if the predicate functions correctly, a false value will be returned.
     */
    @Test
    void isNotConflictingWithCustomer()
    {
        Appointment testAppointment = new Appointment();
        Timestamp startDate = Timestamp.from(Instant.ofEpochSecond(1656952200));
        Timestamp endDate = Timestamp.from(Instant.ofEpochSecond(1656953100));
        testAppointment.setStart(startDate);
        testAppointment.setEnd(endDate);
        testAppointment.setCustomerId(1);
        testAppointment.setAppointmentId(1);

        Appointment conflictingTestAppointment = new Appointment();
        Timestamp conflictingStartDate = Timestamp.from(Instant.ofEpochSecond(1657087200));
        Timestamp conflictingEndDate = Timestamp.from(Instant.ofEpochSecond(1657089000));
        conflictingTestAppointment.setStart(conflictingStartDate);
        conflictingTestAppointment.setEnd(conflictingEndDate);
        conflictingTestAppointment.setCustomerId(1);
        conflictingTestAppointment.setAppointmentId(2);

        boolean result = testAppointment.isConflictingWithCustomer.test(testAppointment,conflictingTestAppointment);
        assertFalse(result);
    }
}