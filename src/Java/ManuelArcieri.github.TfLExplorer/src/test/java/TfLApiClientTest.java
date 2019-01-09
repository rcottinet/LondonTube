/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

import java.io.*;
import java.net.*;
import java.net.http.*;
import java.nio.file.*;
import java.util.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class TfLApiClientTest
{
    private final String API_BASE_URL = "api.tfl.gov.uk";
    private TfLApiClient clientWithFakeCredentials = null;
    private TfLApiClient clientWithRealCredentials = null;
    private static String appId = null;
    private static String appKey = null;

    @BeforeAll
    static void readPrivateApiKeysIfPresent()
    {
        try
        {
            File secretFile = Paths.get("secret.txt").toFile();
            if (!secretFile.exists())
                return;

            List<String> lines = Files.readAllLines(secretFile.toPath());
            appId = lines.get(0);
            appKey = lines.get(1);
        }
        catch (Exception ex)
        {
        }
    }

    @BeforeEach
    void setUpClientWithFakeCredentials()
    {
        clientWithFakeCredentials = new TfLApiClient("FakeId", "FakeKey");
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
        assumeTrue(appId != null, "'appId' is not present");
        assumeTrue(appKey != null, "'appKey' is not present");

        TfLApiClient client = clientWithRealCredentials;
        HttpResponse<String> response = null;
        try
        {
            response = client.sendRawRequest("fake", "path");
        }
        catch (Exception ex)
        {
            fail("The client should not throw while sending a raw request", ex);
        }

        assertEquals(404, response.statusCode(), "Sending a request to a fake API path should return a 404 error code");
    }

    @Test
    void throwOnRequestToFakeWebsite()
    {
        TfLApiClient client = clientWithFakeCredentials;
        client.setApiBaseUrl("fakeWebsite.mock");

        assertThrows(ConnectException.class, () -> client.sendRawRequest("fakePath"),
                     "Sending a request to a non-existing website should throw");
    }

    @Test
    void throwOnRequestToMalformedUrl()
    {
        TfLApiClient client = clientWithFakeCredentials;
        client.setApiBaseUrl("Malformed URI");  // Whitespace is an invalid character

        assertThrows(URISyntaxException.class, () -> client.sendRawRequest("fakePath"),
                     "Sending a request to a malformed URL should throw");
    }
}
