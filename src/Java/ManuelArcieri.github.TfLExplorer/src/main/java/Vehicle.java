/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

import com.google.gson.JsonObject;

public class Vehicle
{
    public String type;

    public Vehicle(JsonObject vehicleElement)
    {
        type = vehicleElement.get("type").getAsString();
    }
}
