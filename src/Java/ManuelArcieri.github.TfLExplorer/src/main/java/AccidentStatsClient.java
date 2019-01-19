/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

import java.io.IOException;

public class AccidentStatsClient extends TfLApiClient
{
    public AccidentStatsClient(String applicationId, String applicationKey)
    {
        super(applicationId, applicationKey);
    }

    public AccidentStatsClient(String applicationId, String applicationKey, int timeoutInMilliseconds)
    {
        super(applicationId, applicationKey, timeoutInMilliseconds);
    }

    public AccidentStatsResponse getAccidentsPerYear(Integer year) throws IOException
    {
        Response baseResponse = sendRawRequest("AccidentStats", year.toString());
        return new AccidentStatsResponse(baseResponse.httpURLConnection);
    }
}
