/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

import com.google.gson.JsonObject;

public class Casualty
{
    public final int age;
    public final String casualtyClass;
    public final String severity;
    public final String mode;
    public final String ageBand;

    public Casualty(JsonObject casualtyElement)
    {
        age = casualtyElement.has("age") ? casualtyElement.get("age").getAsInt() : -1;
        casualtyClass = casualtyElement.get("class").getAsString();
        severity = casualtyElement.get("severity").getAsString();
        mode = casualtyElement.get("mode").getAsString();
        ageBand = casualtyElement.get("ageBand").getAsString();
    }
}
