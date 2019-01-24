# TfL Explorer
**TfL Explorer** is a Java library which offers you a **user-friendly interface** to communicate with the **Transport for London Unified API** in an **object-oriented fashion**.

This project is still in its early stage. Several features are missing, while many others are currently being implemented.

## How does it work?

TfL Explorer is mainly composed of a **set of web clients**, each wrapped around its specific API implementation.
You can refer to the official [TfL Unified API documentation](https://api.tfl.gov.uk) to get a general idea of how they're structured.

Here's a list of all API clients (click on the name for details):
- **Completed**
    - [AccidentStatsClient](https://github.com/ManuelArcieri/TfLExplorer/wiki/AccidentStats-API)  
- **Under development**
    - AirQualityClient
- **Planned**
    - BikePointClient
    - JourneyClient
    - LineClient
    - ModeClient
    - OccupancyClient
    - PlaceClient
    - RoadClient
    - SearchClient
    - StopPointClient
    - VehicleClient

## How can I use it?
All API clients expose several public methods which can be used to call a specific API request.
Here's an example:

```java
AccidentStatsClient client = new AccidentStatsClient("<appId>", "<appKey>");
AccidentStatsResponse response = client.getAccidentsPerYear(2017);

if (response.responseCode == HttpURLConnection.HTTP_OK)
{
    List<Accident> accidents = response.accidents;
    int numberOfAccidents = accidents.size();  // = 54178

    Accident particularAccident = accidents.get(42);
    String location = particularAccident.location;  // = "Bloomsbury Way junction with Bury Place Wc1"
    String borough = particularAccident.borough;  // = "Camden"
    Calendar date = particularAccident.date; // = 15 March 2017, 09:49
    String severity = particularAccident.severity; // = "Serious"

    Casualty casualty = particularAccident.casualties.get(0);
    int age = casualty.age;  // = 36
    String casualtyClass = casualty.casualtyClass;  // = "Pedestrian";

    Vehicle vehicle = particularAccident.vehicles.get(0);
    String type = vehicle.type;  // = "Motorcycle_125_500cc"
}
```

Check the [wiki](https://github.com/ManuelArcieri/TfLExplorer/wiki) for **further information**.

## Where can I get my Application ID and Key?

You can get your own authentication credentials from the [API portal of Transport for London](https://api-portal.tfl.gov.uk/login).
