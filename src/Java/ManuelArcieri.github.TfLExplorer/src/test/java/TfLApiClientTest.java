/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.logging.Logger;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class TfLApiClientTest
{
    protected final String API_BASE_URL = "api.tfl.gov.uk";
    protected TfLApiClient clientWithFakeCredentials = null;
    protected TfLApiClient clientWithRealCredentials = null;
    protected static String appId = null;
    protected static String appKey = null;

    @BeforeAll
    protected static void readPrivateApiKeysIfPresent()
    {
        Logger logger = Logger.getGlobal();
        String fileName = "<Undefined>";
        try
        {
            File secretFile = Paths.get("secret.txt").toFile();
            if (!secretFile.exists())
            {
                logger.warning(String.format("File not found: '%s'.\nAll tests which need the appId and appKey will be skipped.",
                                             secretFile.getAbsolutePath()));
                return;
            }

            fileName = secretFile.getAbsolutePath();
            List<String> lines = Files.readAllLines(secretFile.toPath());
            appId = lines.get(0);
            appKey = lines.get(1);
            logger.info("appId and appKey found in " + secretFile.getAbsolutePath());
        }
        catch (Exception ex)
        {
            logger.warning(String.format("It couldn't be possible to read appId and appKey from '%s'.\n" +
                                                 "All tests which need the appId and appKey will be skipped.\n" +
                                                 "(Cause --> %s)", fileName, ex.toString()));
        }
    }

    @BeforeEach
    protected void setUpClientWithFakeCredentials()
    {
        clientWithFakeCredentials = new TfLApiClient("FakeId", "FakeKey");
        if (appIdAndAppKeyArePresent())
            clientWithRealCredentials = new TfLApiClient(appId, appKey);
    }


    @Test
    void throwOnEmptyApplicationId()
    {
        assertThrows(IllegalArgumentException.class, () ->
                             new TfLApiClient("", "Not empty"),
                     "Constructor didn't thrown on empty 'applicationId'");
    }

    @Test
    void throwOnEmptyApplicationKey()
    {
        assertThrows(IllegalArgumentException.class, () ->
                             new TfLApiClient("Not empty", ""),
                     "Constructor didn't thrown on empty 'applicationKey'");
    }

    @Test
    void throwOnEmptyApplicationIdAndEmptyApplicationKey()
    {
        assertThrows(IllegalArgumentException.class, () ->
                             new TfLApiClient("", ""),
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

    @Test
    void setUpClientWithRealCredentials()
    {
        assumeTrue(appId != null, "'appId' is not present");
        assumeTrue(appKey != null, "'appKey' is not present");

        assertDoesNotThrow(() -> new TfLApiClient(appId, appKey),
                           "It must not throw with real credentials");
    }

    @Test
    void sendingRawRequestToFakeApiPathShouldReturn404ErrorCode()
    {
        assumeAppIdAndAppKeyPresence();

        TfLApiClient client = clientWithRealCredentials;
        Response response = null;
        try
        {
            response = client.sendRawRequest("fake", "path");
        }
        catch (Exception ex)
        {
            fail("The client should not throw while sending a raw request", ex);
        }

        assertEquals(404, response.responseCode, "Sending a request to a fake API path should return a 404 error code");
    }

    @Test
    void throwOnRequestToFakeWebsite()
    {
        TfLApiClient client = clientWithFakeCredentials;
        client.setApiBaseUrl("fakeWebsite.mock");

        assertThrows(UnknownHostException.class, () -> client.sendRawRequest("fakePath"),
                     "Sending a request to a non-existing website should throw");
    }

    @Test
    void throwOnRequestToMalformedUrl()
    {
        TfLApiClient client = clientWithFakeCredentials;
        client.setApiBaseUrl("Malformed URI");  // Whitespace is an invalid character

        assertThrows(UnknownHostException.class, () -> client.sendRawRequest("fakePath"),
                     "Sending a request to a malformed URL should throw");
    }

    protected void assumeAppIdAndAppKeyPresence()
    {
        assumeTrue(appId != null, "'appId' is not present");
        assumeTrue(appKey != null, "'appKey' is not present");
    }

    protected boolean appIdAndAppKeyArePresent()
    {
        return appId != null && appKey != null;
    }
}
