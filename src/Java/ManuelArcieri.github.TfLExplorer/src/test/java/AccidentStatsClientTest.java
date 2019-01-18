/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

import java.text.ParseException;
import java.util.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

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
    void getAccidentsIn2016()
    {
        assumeAppIdAndAppKeyPresence();
        assertDoesNotThrow(() -> {
            Response response = accidentStatsClient.getAccidentsPerYear(2016);
            assertTrue(response.responseCode >= 200 && response.responseCode <= 299);
        }, "'getAccidentsPerYear' should not throw");
    }

    @Test
    void convertIsoDateStringInDateObject()
    {
        String dateString = "2016-06-04T20:00:00Z";
        Calendar calendar = JsonResponse.convertIso8601StringToDateObject(dateString);

        assertEquals(2016, calendar.get(Calendar.YEAR));
        assertEquals(Calendar.JUNE, calendar.get(Calendar.MONTH));
        assertEquals(4, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(0, calendar.get(Calendar.MINUTE));
    }

    @Test
    void convertingInvalidIsoDateStringShouldThrow()
    {
        String dateString = "201X-06-04T20:00:00Z";
        Calendar calendar = JsonResponse.convertIso8601StringToDateObject(dateString);
        assertNull(calendar, "'convertIso8601StringToDateObject' should return null if it gets invalid data");
    }
}
