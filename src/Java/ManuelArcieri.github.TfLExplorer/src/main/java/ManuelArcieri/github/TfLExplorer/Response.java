/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package ManuelArcieri.github.TfLExplorer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Date;


/**
 A basic wrapper around an HTTP response
 */
public class Response
{
    public final HttpURLConnection httpURLConnection;
    public final int responseCode;
    public final String responseMessage;
    public final String contentType;
    public final Date date;

    /**
     Generate a basic wrapper around an HTTP response

     @param connection The HTTP connection which should be analyzed

     @throws IOException If there's been a connection problem
     */
    public Response(HttpURLConnection connection) throws IOException
    {
        httpURLConnection = connection;
        responseCode = connection.getResponseCode();
        responseMessage = connection.getResponseMessage();
        contentType = connection.getContentType();
        date = new Date(connection.getDate());
    }
}
