/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package ManuelArcieri.github.TfLExplorer;

import com.google.gson.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Iterator;

/**
 A wrapper around an HTTP response which returned an AirQuality object in JSON format
 */
public class AirQualityResponse extends JsonResponse
{
    public final String updatePeriod;
    public final String forecastUrl;
    public final Forecast currentForecast;
    public final Forecast futureForecast;


    /**
     Create a wrapper around an HTTP response which returned an AirQuality object in JSON format

     @param connection The HTTP connection which should be analyzed

     @throws IOException If there's been a connection problem
     */
    public AirQualityResponse(HttpURLConnection connection) throws IOException
    {
        super(connection);

        JsonObject rootObject = jsonRootElement.isPresent()
                ? jsonRootElement.get().getAsJsonObject()
                : null;
        updatePeriod = getStringFromJson(rootObject, "updatePeriod");
        forecastUrl = getStringFromJson(rootObject, "forecastURL");

        JsonArray forecastArray = jsonRootElement.isPresent() && rootObject.has("currentForecast")
                ? rootObject.getAsJsonArray("currentForecast")
                : null;

        if (forecastArray != null)
        {
            Iterator<JsonElement> iterator = forecastArray.iterator();

            if (!iterator.hasNext())
            {
                currentForecast = new Forecast();
                futureForecast = new Forecast();
                return;
            }
            currentForecast = new Forecast(iterator.next().getAsJsonObject());

            if (!iterator.hasNext())
            {
                futureForecast = new Forecast();
                return;
            }
            futureForecast = new Forecast(iterator.next().getAsJsonObject());
        }
        else
        {
            currentForecast = new Forecast();
            futureForecast = new Forecast();
        }
    }

    private String getStringFromJson(JsonObject jsonObject, String propertyName)
    {
        return jsonObject != null && jsonObject.has(propertyName)
                ? jsonObject.get(propertyName).getAsString()
                : "";
    }
}
