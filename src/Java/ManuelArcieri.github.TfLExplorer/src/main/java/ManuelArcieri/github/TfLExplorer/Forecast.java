/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package ManuelArcieri.github.TfLExplorer;

import com.google.gson.JsonObject;

/**
 A collector of all air quality properties
 */
public class Forecast
{
    public final String id;
    public final String type;
    public final String summary;
    public final String text;
    public final String band;
    public final String nO2Band;
    public final String o3Band;
    public final String pM10Band;
    public final String pM25Band;
    public final String sO2Band;


    /**
     Create an empty Forecast object
     */
    public Forecast()
    {
        id = "";
        type = "";
        band = "";
        summary = "";
        text = "";
        nO2Band = "";
        o3Band = "";
        pM10Band = "";
        pM25Band = "";
        sO2Band = "";
    }

    /**
     Create a Forecast object from the given JSON object

     @param jsonObject A JsonObject containing all required information
     */
    public Forecast(JsonObject jsonObject)
    {
        id = jsonObject.has("forecastID") ? jsonObject.get("forecastID").getAsString() : "";
        type = jsonObject.has("forecastType") ? jsonObject.get("forecastType").getAsString() : "";
        summary = jsonObject.has("forecastSummary") ? jsonObject.get("forecastSummary").getAsString() : "";
        text = jsonObject.has("forecastText") ? getStringWithoutSymbols(jsonObject.get("forecastText").getAsString()) : "";
        band = jsonObject.has("forecastBand") ? jsonObject.get("forecastBand").getAsString() : "";
        nO2Band = jsonObject.has("nO2Band") ? jsonObject.get("nO2Band").getAsString() : "";
        o3Band = jsonObject.has("o3Band") ? jsonObject.get("o3Band").getAsString() : "";
        pM10Band = jsonObject.has("pM10Band") ? jsonObject.get("pM10Band").getAsString() : "";
        pM25Band = jsonObject.has("pM25Band") ? jsonObject.get("pM25Band").getAsString() : "";
        sO2Band = jsonObject.has("sO2Band") ? jsonObject.get("sO2Band").getAsString() : "";
    }

    private String getStringWithoutSymbols(String text)
    {
        return text.replaceAll("&lt;br/&gt;", "\n")
                .replaceAll("&#39;", "'");
    }
}
