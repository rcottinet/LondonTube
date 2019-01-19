/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package ManuelArcieri.github.TfLExplorer;

import java.io.IOException;


/**
 Client for getting all accident details for accidents occurred in the specified year
 */
public class AccidentStatsClient extends TfLApiClient
{

    /**
     Create an AccidentStats client with the given credentials

     @param applicationId  TfL Unified API application ID
     @param applicationKey TfL Unified API application key
     */
    public AccidentStatsClient(String applicationId, String applicationKey)
    {
        super(applicationId, applicationKey);
    }


    /**
     Create an AccidentStats client with the given credentials and timeout

     @param applicationId         TfL Unified API application ID
     @param applicationKey        TfL Unified API application key
     @param timeoutInMilliseconds Maximum waiting time expressed in milliseconds
     */
    public AccidentStatsClient(String applicationId, String applicationKey, int timeoutInMilliseconds)
    {
        super(applicationId, applicationKey, timeoutInMilliseconds);
    }

    /**
     Get all accident details for accidents occurred in the specified year

     @param year Year in which the accidents occurred

     @return An ManuelArcieri.github.TfLExplorer.AccidentStatsResponse containing a list of all occurred accidents

     @throws IOException If there's been a connection problem
     */
    public AccidentStatsResponse getAccidentsPerYear(Integer year) throws IOException
    {
        Response baseResponse = sendRawRequest("AccidentStats", year.toString());
        return new AccidentStatsResponse(baseResponse.httpURLConnection);
    }
}
