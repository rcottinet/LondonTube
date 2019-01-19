/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

import com.google.gson.JsonObject;

/**
 A casualty in an AccidentStats accident
 */
public class Casualty
{
    public final int age;
    public final String casualtyClass;
    public final String severity;
    public final String mode;
    public final String ageBand;

    /**
     Create a new Casualty reading all its properties from its JSON object

     @param casualtyElement A JSON element containing a Casualty object
     */
    public Casualty(JsonObject casualtyElement)
    {
        age = casualtyElement.has("age") ? casualtyElement.get("age").getAsInt() : -1;
        casualtyClass = casualtyElement.get("class").getAsString();
        severity = casualtyElement.get("severity").getAsString();
        mode = casualtyElement.get("mode").getAsString();
        ageBand = casualtyElement.get("ageBand").getAsString();
    }
}
