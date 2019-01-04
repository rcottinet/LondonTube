/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

import java.net.http.*;

public class TubeApiClient
{
    private String apiBaseUrl = "https://api.tfl.gov.uk";
    private String appId;
    private String appKey;
    private HttpClient client;

    public TubeApiClient(String applicationId, String applicationKey)
    {
        if (applicationId.isEmpty() || applicationKey.isEmpty())
            throw new IllegalArgumentException("'applicationId' or 'applicationKey' is empty");

        appId = applicationId;
        appKey = applicationKey;

        client = HttpClient.newHttpClient();
    }

    public String getApiBaseUrl()
    {
        return apiBaseUrl;
    }

    public void setApiBaseUrl(String newBaseUrl)
    {
        if (newBaseUrl.isEmpty())
            throw new IllegalArgumentException("'newBaseUrl' is empty");
        apiBaseUrl = newBaseUrl;
    }
}
