/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package ManuelArcieri.github.TfLExplorer;

import com.google.gson.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.*;

/**
 A wrapper around an HTTP response which returned an AccidentStats object in JSON format
 */
public class AccidentStatsResponse extends JsonResponse
{
    public final List<Accident> accidents;

    /**
     Create a wrapper around an HTTP response which returned an AccidentStats object in JSON format

     @param connection The HTTP connection which should be analyzed

     @throws IOException If there's been a connection problem
     */
    public AccidentStatsResponse(HttpURLConnection connection) throws IOException
    {
        super(connection);

        List<Accident> accidentList = null;
        if (jsonRootElement.isPresent())
            accidentList = readAllAccidentsFromJson();
        accidents = accidentList;
    }

    private List<Accident> readAllAccidentsFromJson()
    {
        JsonElement root = jsonRootElement.get();
        if (!root.isJsonArray())
            return null;

        JsonArray array = root.getAsJsonArray();
        ArrayList<Accident> accidentsList = new ArrayList<>(array.size());
        for (JsonElement element : array)
            try
            {
                if (element.isJsonObject())
                {
                    Accident accident = new Accident(element.getAsJsonObject());
                    accidentsList.add(accident);
                }
            }
            catch (Exception ex) {}

        return accidentsList;
    }
}
