/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

public class TubeApiClient extends TfLApiClient
{
    public TubeApiClient(String applicationId, String applicationKey)
    {
        super(applicationId, applicationKey);
    }

    public TubeApiClient(String applicationId, String applicationKey, int timeoutInMilliseconds)
    {
        super(applicationId, applicationKey, timeoutInMilliseconds);
    }
}
