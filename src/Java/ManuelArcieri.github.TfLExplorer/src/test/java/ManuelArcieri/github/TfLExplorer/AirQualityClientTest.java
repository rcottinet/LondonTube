/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package ManuelArcieri.github.TfLExplorer;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class AirQualityClientTest extends TfLApiClientTest
{
    protected AirQualityClient airQualityClient = null;

    @BeforeEach
    void setUpAirQualityClient()
    {
        if (appIdAndAppKeyArePresent())
            airQualityClient = new AirQualityClient(appId, appKey);
    }

    @Test
    void getAirQuality()
    {
        assumeAppIdAndAppKeyPresence();
        assertDoesNotThrow(() -> {
            airQualityClient.getAirQuality();
        }, "'getAirQuality' should not throw");
    }

    @Test
    void checkCurrentAndFutureForecastOrder()
    {
        assumeAppIdAndAppKeyPresence();
        assertDoesNotThrow(() -> {
            AirQualityResponse response = airQualityClient.getAirQuality();
            String type = response.currentForecast.type;
            assertEquals("Current", type, "The first forecast should refer to the current air quality");

            type = response.futureForecast.type;
            assertEquals("Future", type, "The second forecast should refer to the future air quality");
        }, "'getAirQuality' should not throw");
    }

    @Test
    void checkThatPropertiesAreNotEmpty()
    {
        assumeAppIdAndAppKeyPresence();
        assertDoesNotThrow(() -> {
            AirQualityResponse response = airQualityClient.getAirQuality();
            assertNotEquals("", response.forecastUrl, "The URL should not be blank");
            assertNotEquals("", response.updatePeriod, "The update period should not be blank");
            assertNotNull(response.currentForecast, "The current forecast must not be null");
            assertNotNull(response.futureForecast, "The future forecast must not be null");
            assertNotEquals("", response.currentForecast.type, "The forecast type should not be blank");
            assertNotEquals("", response.futureForecast.type, "The forecast type should not be blank");
            assertNotEquals("", response.currentForecast.text, "The forecast text should not be blank");
            assertNotEquals("", response.futureForecast.text, "The forecast text should not be blank");
            assertNotEquals("", response.currentForecast.summary, "The forecast summary should not be blank");
            assertNotEquals("", response.futureForecast.summary, "The forecast summary should not be blank");
            assertNotEquals("", response.currentForecast.band, "The forecast band should not be blank");
            assertNotEquals("", response.futureForecast.band, "The forecast band should not be blank");
        }, "'getAirQuality' should not throw");
    }
}
