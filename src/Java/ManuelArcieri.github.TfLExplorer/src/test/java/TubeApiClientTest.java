/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class TubeApiClientTest
{
    private final String API_BASE_URL = "api.tfl.gov.uk";
    private TubeApiClient clientWithFakeCredentials;
    private static String appId;
    private static String appKey;


    @BeforeEach
    void setUpClientWithFakeCredentials()
    {
        clientWithFakeCredentials = new TubeApiClient("<<<Fake ID>>>", "<<<Fake Key>>>");
    }


    @Test
    void throwOnEmptyApplicationId()
    {
        assertThrows(IllegalArgumentException.class, () ->
                             new TubeApiClient("", "Not empty"),
                     "Constructor didn't thrown on empty 'applicationId'");
    }

    @Test
    void throwOnEmptyApplicationKey()
    {
        assertThrows(IllegalArgumentException.class, () ->
                             new TubeApiClient("Not empty", ""),
                     "Constructor didn't thrown on empty 'applicationKey'");
    }

    @Test
    void throwOnEmptyApplicationIdAndEmptyApplicationKey()
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
    void setNewBaseUrlWithoutProtocol()
    {
        String fakeBaseUrl = "a.b";
        clientWithFakeCredentials.setApiBaseUrl(fakeBaseUrl);
        String apiBaseUrl = clientWithFakeCredentials.getApiBaseUrl();
        assertEquals(fakeBaseUrl, apiBaseUrl, "The base URL hasn't been changed");
    }

    @Test
    void setNewBaseUrlWithHttpProtocol()
    {
        String fakeBaseUrl = "http://a.b";
        clientWithFakeCredentials.setApiBaseUrl(fakeBaseUrl);
        String apiBaseUrl = clientWithFakeCredentials.getApiBaseUrl();
        assertEquals("a.b", apiBaseUrl, "The base URL hasn't been changed or hasn't removed the 'http://' prefix");
    }

    @Test
    void setNewBaseUrlWithHttpsProtocol()
    {
        String fakeBaseUrl = "https://a.b";
        clientWithFakeCredentials.setApiBaseUrl(fakeBaseUrl);
        String apiBaseUrl = clientWithFakeCredentials.getApiBaseUrl();
        assertEquals("a.b", apiBaseUrl, "The base URL hasn't been changed or hasn't removed the 'https://' prefix");
    }

    @Test
    void throwOnEmptyNewBaseUrl()
    {
        assertThrows(IllegalArgumentException.class, () ->
                             clientWithFakeCredentials.setApiBaseUrl(""),
                     "Client didn't thrown on empty base URL");
    }

    @Test
    void generateUrlGivenItsParts()
    {
        assertDoesNotThrow(() -> {
            URL url = new URL("https", "sub.host.tld", "/a/b/x?p=1");
            String desiredUrl = "https://sub.host.tld/a/b/x?p=1";
            assertEquals(desiredUrl, url.toString(), "Generated URL is not the desired one");
        }, "URL constructor should not throw an exception");
    }

    static boolean readPrivateApiKeysIfPresent()
    {
        try
        {
            File secretFile = Paths.get("secret.txt").toFile();
            if (!secretFile.exists())
                return false;

            List<String> lines = Files.readAllLines(secretFile.toPath());
            appId = lines.get(0);
            appKey = lines.get(1);
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }
}
