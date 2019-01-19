/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package ManuelArcieri.github.TfLExplorer;

import java.util.*;

import com.google.gson.*;


/**
 An object encapsulating all the properties of an AccidentStats accident
 */
public class Accident
{
    public final long id;
    public final double latitude;
    public final double longitude;
    public final String location;
    public final Calendar date;
    public final String severity;
    public final String borough;
    public final List<Casualty> casualties;
    public final List<Vehicle> vehicles;

    /**
     Create an object encapsulating all the properties of an AccidentStats accident given its JSON representation

     @param rootElement JSON element containing all the properties of the accident
     */
    public Accident(JsonObject rootElement)
    {
        id = rootElement.get("id").getAsLong();
        latitude = rootElement.get("lat").getAsDouble();
        longitude = rootElement.get("lon").getAsDouble();
        location = rootElement.get("location").getAsString();
        String dateString = rootElement.get("date").getAsString();
        date = JsonResponse.convertIso8601StringToCalendarObject(dateString);
        severity = rootElement.get("severity").getAsString();
        borough = rootElement.get("borough").getAsString();

        ArrayList<Casualty> casualtyList = new ArrayList<>();
        for (JsonElement casualty : rootElement.get("casualties").getAsJsonArray())
            casualtyList.add(new Casualty(casualty.getAsJsonObject()));
        casualties = casualtyList;

        ArrayList<Vehicle> vehicleList = new ArrayList<>();
        for (JsonElement vehicle : rootElement.get("vehicles").getAsJsonArray())
            vehicleList.add(new Vehicle(vehicle.getAsJsonObject()));
        vehicles = vehicleList;
    }
}
