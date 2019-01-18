/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

import java.io.*;
import java.net.HttpURLConnection;
import java.util.HashSet;
import java.util.Optional;

import com.google.gson.*;

public class AccidentStatsResponse
{
    public final JsonResponse jsonResponse;
    protected HashSet<Accident> accidents;

    public AccidentStatsResponse(JsonResponse response)
    {
        jsonResponse = response;
        if (jsonResponse.jsonRootElement.isPresent())
            readAllAccidentsFromJson();
        else
            throwOnInvalidData("The server didn't provide a valid JSON response");
    }

    private void throwOnInvalidData(String message)
    {
        throw new IllegalArgumentException(message);
    }

    private void readAllAccidentsFromJson()
    {
        JsonElement root = jsonResponse.jsonRootElement.get();
        if (!root.isJsonArray())
            throwOnInvalidData("JSON root element is not an array");

        JsonArray array = root.getAsJsonArray();
        accidents = new HashSet<>(array.size());
        for (JsonElement element : array)
            if (element.isJsonObject())
                readSingleAccident(element.getAsJsonObject());
    }

    private void readSingleAccident(JsonObject object)
    {

    }
}
