/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package ManuelArcieri.github.TfLExplorer;

import java.io.IOException;

/**
 Client for getting the current and future air quality data feed.
 <br/><br/>
 DISCLAIMER: This forecast is intended to provide information on expected pollution levels in areas of significant public exposure. It may not apply in very specific locations close to unusually strong or short-lived local sources of pollution.
 */
public class AirQualityClient extends TfLApiClient
{
    /**
     Create an AirQuality client with the given credentials

     @param applicationId  TfL Unified API application ID
     @param applicationKey TfL Unified API application key
     */
    public AirQualityClient(String applicationId, String applicationKey)
    {
        super(applicationId, applicationKey);
    }

    /**
     Create an AirQuality client with the given credentials and timeout

     @param applicationId         TfL Unified API application ID
     @param applicationKey        TfL Unified API application key
     @param timeoutInMilliseconds Maximum waiting time expressed in milliseconds
     */
    public AirQualityClient(String applicationId, String applicationKey, int timeoutInMilliseconds)
    {
        super(applicationId, applicationKey, timeoutInMilliseconds);
    }

    /**
     Return the current and future air quality data feed

     @return An AirQualityResponse containing the air quality information

     @throws IOException If there's been a connection problem
     */
    public AirQualityResponse getAirQuality() throws IOException
    {
        Response response = sendRawRequest("AirQuality");
        return new AirQualityResponse(response.httpURLConnection);
    }
}
