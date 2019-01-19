/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package ManuelArcieri.github.TfLExplorer;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 A wrapper around an HTTP response which returned a JSON object
 */
public abstract class JsonResponse extends Response
{
    protected Optional<JsonElement> jsonRootElement;

    /**
     Create a wrapper around an HTTP response which returned a JSON object

     @param connection The HTTP connection which should be analyzed

     @throws IOException If there's been a connection problem
     */
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


    /**
     Convert an ISO 8601 date string to a Calendar object

     @param date Date string in ISO 8601 format

     @return A Calendar object representing the given date string
     */
    public static Calendar convertIso8601StringToCalendarObject(String date)
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

    /**
     Convert a Date object to a Calendar

     @param date Date object which should be converted

     @return A Calendar object representing the given Date
     */
    public static Calendar toCalendar(Date date)
    {
        Calendar calendar = GregorianCalendar.getInstance(Locale.UK);
        calendar.setTime(date);
        return calendar;
    }
}
