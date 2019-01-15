/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

import java.io.*;
import java.net.*;

public class TfLApiClient
{
    private String apiBaseUrl = "api.tfl.gov.uk";
    private String appId;
    private String appKey;
    private int maxTimeoutInMilliseconds;

    public TfLApiClient(String applicationId, String applicationKey)
    {
        this(applicationId, applicationKey, 5000);
    }

    public TfLApiClient(String applicationId, String applicationKey, int timeoutInMilliseconds)
    {
        if (applicationId.isEmpty() || applicationKey.isEmpty())
            throw new IllegalArgumentException("'applicationId' or 'applicationKey' is empty");

        appId = applicationId;
        appKey = applicationKey;
        maxTimeoutInMilliseconds = timeoutInMilliseconds;
    }

    public String getApiBaseUrl()
    {
        return apiBaseUrl;
    }

    public void setApiBaseUrl(String newBaseUrl)
    {
        if (newBaseUrl.isEmpty())
            throw new IllegalArgumentException("'newBaseUrl' is empty");


        if (newBaseUrl.startsWith("http://"))
            newBaseUrl = newBaseUrl.substring(7);
        else if (newBaseUrl.startsWith("https://"))
            newBaseUrl = newBaseUrl.substring(8);

        apiBaseUrl = newBaseUrl;
    }

    public Response sendRawRequest(String... apiPath) throws IOException
    {
        return sendGetRequest(apiPath);
    }

    private final String[] DEFAULT_HEADERS = {
            "User-Agent", "ManuelArcieri.github.TfLExplorer (Java)",
            "Content-Type", "application/json; charset=utf-8"
    };

    private Response sendGetRequest(String... apiPath) throws IOException
    {
        URL url = getUrlWithCredentials(apiPath);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setInstanceFollowRedirects(true);
        connection.setConnectTimeout(maxTimeoutInMilliseconds);
        for (int i = 0; i < DEFAULT_HEADERS.length; i += 2)
            connection.setRequestProperty(DEFAULT_HEADERS[i], DEFAULT_HEADERS[i + 1]);

        Response response = new Response(connection);

        connection.disconnect();
        return response;
    }

    private URL getUrlWithCredentials(String... urlPath) throws MalformedURLException
    {
        String protocol = "https";
        String apiPath = "/" + String.join("/", urlPath);
        apiPath += getCredentialsAsUrlQueryString();
        return new URL(protocol, apiBaseUrl, apiPath);
    }

    private String getCredentialsAsUrlQueryString()
    {
        return String.format("?app_id=%s&app_key=%s", appId, appKey);
    }
}
