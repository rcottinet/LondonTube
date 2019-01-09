/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.time.Duration;

public class TfLApiClient
{
    private final String[] DEFAULT_HEADERS = { "User-Agent", "ManuelArcieri.github.TfLExplorer (Java)" };

    private String apiBaseUrl = "api.tfl.gov.uk";
    private String appId;
    private String appKey;
    private HttpClient client;

    public TfLApiClient(String applicationId, String applicationKey)
    {
        this(applicationId, applicationKey, 10L);
    }

    public TfLApiClient(String applicationId, String applicationKey, long timeoutInSeconds)
    {
        if (applicationId.isEmpty() || applicationKey.isEmpty())
            throw new IllegalArgumentException("'applicationId' or 'applicationKey' is empty");

        appId = applicationId;
        appKey = applicationKey;
        client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .connectTimeout(Duration.ofSeconds(timeoutInSeconds))
                .build();
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

    public HttpResponse<String> sendRawRequest(String... apiPath) throws URISyntaxException, IOException, InterruptedException
    {
        return sendGetRequest(apiPath);
    }

    private HttpResponse<String> sendGetRequest(String... apiPath) throws URISyntaxException, IOException, InterruptedException
    {
        URL url = getUrlWithCredentials(apiPath);
        HttpRequest request = HttpRequest.newBuilder(url.toURI())
                .headers(DEFAULT_HEADERS).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private URL getUrlWithCredentials(String... urlPath) throws MalformedURLException
    {
        String protocol = "https";
        String apiPath = "/" + String.join("/", urlPath);
        apiPath += getCredentialsAsUrlQueryString();
        URL url = new URL(protocol, apiBaseUrl, apiPath);
        return url;
    }

    private String getCredentialsAsUrlQueryString()
    {
        return String.format("?app_id=%s&app_key=%s", appId, appKey);
    }
}
