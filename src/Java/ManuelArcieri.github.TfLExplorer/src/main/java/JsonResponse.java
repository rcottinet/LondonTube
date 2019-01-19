/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

import java.io.*;
import java.net.HttpURLConnection;
import java.text.*;
import java.util.*;

import com.google.gson.*;

public class JsonResponse extends Response
{
    protected Optional<JsonElement> jsonRootElement;

    public JsonResponse(HttpURLConnection connection) throws IOException
    {
        super(connection);

        if (responseCode == HttpURLConnection.HTTP_OK)
            try
            {
                InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
                jsonRootElement = Optional.of(new JsonParser().parse(inputStream));
            }
            catch (Exception ex)
            {
                jsonRootElement = Optional.empty();
            }
        else
            jsonRootElement = Optional.empty();
    }

    public static Calendar convertIso8601StringToDateObject(String date)
    {
        TimeZone UTCtimeZone = TimeZone.getTimeZone("UTC");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormat.setTimeZone(UTCtimeZone);

        try
        {
            Date dateObject = dateFormat.parse(date);
            return toCalendar(dateObject);
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    public static Calendar toCalendar(Date date)
    {
        Calendar calendar = GregorianCalendar.getInstance(Locale.UK);
        calendar.setTime(date);
        return calendar;
    }
}
