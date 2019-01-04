/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TubeApiClientTest
{
    final String API_BASE_URL = "https://api.tfl.gov.uk";
    TubeApiClient clientWithFakeCredentials;

    @BeforeEach
    void setUpClientWithFakeCredentials()
    {
        clientWithFakeCredentials = new TubeApiClient("<<<Fake ID>>>", "<<<Fake Key>>>");
    }


    @Test
    void throwsOnEmptyApplicationId()
    {
        assertThrows(IllegalArgumentException.class, () ->
                             new TubeApiClient("", "Not empty"),
                     "Constructor didn't thrown on empty 'applicationId'");
    }

    @Test
    void throwsOnEmptyApplicationKey()
    {
        assertThrows(IllegalArgumentException.class, () ->
                             new TubeApiClient("Not empty", ""),
                     "Constructor didn't thrown on empty 'applicationKey'");
    }

    @Test
    void throwsOnEmptyApplicationIdAndEmptyApplicationKey()
    {
        assertThrows(IllegalArgumentException.class, () ->
                             new TubeApiClient("", ""),
                     "Constructor didn't thrown on empty 'applicationId' and 'applicationKey'");
    }

    @Test
    void getDefaultBaseUrl()
    {
        String apiBaseUrl = clientWithFakeCredentials.getApiBaseUrl();
        assertEquals(API_BASE_URL, apiBaseUrl, "The default base URL is incorrect");
    }

    @Test
    void setNewBaseUrl()
    {
        String fakeBaseUrl = "http://a.b";
        clientWithFakeCredentials.setApiBaseUrl(fakeBaseUrl);
        String apiBaseUrl = clientWithFakeCredentials.getApiBaseUrl();
        assertEquals(fakeBaseUrl, apiBaseUrl, "The base URL hasn't been changed");
    }
}
