/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

import java.util.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class AccidentStatsClientTest extends TfLApiClientTest
{
    protected AccidentStatsClient accidentStatsClient = null;

    @BeforeEach
    void setUpAccidentStatsClient()
    {
        if (appIdAndAppKeyArePresent())
            accidentStatsClient = new AccidentStatsClient(appId, appKey);
    }

    @Test
    @Tag("slow")
    void getAccidentsIn2015()
    {
        // This test will download ~36.5 MB of data
        assumeAppIdAndAppKeyPresence();
        assertDoesNotThrow(() -> {
            AccidentStatsResponse response = accidentStatsClient.getAccidentsPerYear(2015);
            assertEquals(200, response.responseCode, "A valid request should return a HTTP 200 response code");
            assertTrue(response.accidents.size() > 50000);  // There are at least 50.000 accidents. Others might be added in the future.
        }, "'getAccidentsPerYear' should not throw");
    }

    @Test
    @Tag("slow")
    void getAccidentsIn2016()
    {
        // This test will download ~36.5 MB of data
        assumeAppIdAndAppKeyPresence();
        assertDoesNotThrow(() -> {
            AccidentStatsResponse response = accidentStatsClient.getAccidentsPerYear(2016);
            assertEquals(200, response.responseCode, "A valid request should return a HTTP 200 response code");
            assertTrue(response.accidents.size() > 50000);  // There are at least 50.000 accidents. Others might be added in the future.
        }, "'getAccidentsPerYear' should not throw");
    }

    @Test
    @Tag("slow")
    void getAccidentsIn2017()
    {
        // This test will download ~39.2 MB of data
        assumeAppIdAndAppKeyPresence();
        assertDoesNotThrow(() -> {
            AccidentStatsResponse response = accidentStatsClient.getAccidentsPerYear(2017);
            assertEquals(200, response.responseCode, "A valid request should return a HTTP 200 response code");
            assertTrue(response.accidents.size() > 50000);  // There are at least 50.000 accidents. Others might be added in the future.
        }, "'getAccidentsPerYear' should not throw");
    }

    @Test
    void getAccidentsInInvalidYear()
    {
        assumeAppIdAndAppKeyPresence();
        assertDoesNotThrow(() -> {
            AccidentStatsResponse response = accidentStatsClient.getAccidentsPerYear(1500);
            assertEquals(400, response.responseCode, "An invalid request should return a HTTP 400 response code");
        }, "'getAccidentsPerYear' should not throw");
    }

    @Test
    void convertIsoDateStringInDateObject()
    {
        String dateString = "2016-06-04T20:00:00Z";
        Calendar calendar = JsonResponse.convertIso8601StringToCalendarObject(dateString);

        assertEquals(2016, calendar.get(Calendar.YEAR));
        assertEquals(Calendar.JUNE, calendar.get(Calendar.MONTH));
        assertEquals(4, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(0, calendar.get(Calendar.MINUTE));
    }

    @Test
    void convertingInvalidIsoDateStringShouldThrow()
    {
        String dateString = "201X-06-04T20:00:00Z";
        Calendar calendar = JsonResponse.convertIso8601StringToCalendarObject(dateString);
        assertNull(calendar, "'convertIso8601StringToCalendarObject' should return null if it gets invalid data");
    }
}
