/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

import java.io.*;
import java.net.HttpURLConnection;
import java.util.*;

import com.google.gson.*;

/**
 A wrapper around an HTTP response which returned an AccidentStats object in JSON format
 */
public class AccidentStatsResponse extends JsonResponse
{
    public final Set<Accident> accidents;

    /**
     Create a wrapper around an HTTP response which returned an AccidentStats object in JSON format

     @param connection The HTTP connection which should be analyzed

     @throws IOException If there's been a connection problem
     */
    public AccidentStatsResponse(HttpURLConnection connection) throws IOException
    {
        super(connection);

        Set<Accident> accidentSet = null;
        if (jsonRootElement.isPresent())
            accidentSet = readAllAccidentsFromJson();
        accidents = accidentSet;
    }

    private Set<Accident> readAllAccidentsFromJson()
    {
        JsonElement root = jsonRootElement.get();
        if (!root.isJsonArray())
            return null;

        JsonArray array = root.getAsJsonArray();
        HashSet<Accident> accidentsSet = new HashSet<>(array.size());
        for (JsonElement element : array)
            try
            {
                if (element.isJsonObject())
                {
                    Accident accident = new Accident(element.getAsJsonObject());
                    accidentsSet.add(accident);
                }
            }
            catch (Exception ex) {}

        return accidentsSet;
    }
}
