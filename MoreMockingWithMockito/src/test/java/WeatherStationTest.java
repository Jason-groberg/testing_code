import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import instruments.Anemometer;
import instruments.Barometer;
import instruments.Hygrometer;
import instruments.Thermometer;
import instruments.satellite.SatelliteUplink;
import org.junit.jupiter.api.Test;

class WeatherStationTest {

    @Test
    void stormWarningChecksTests() {
        WeatherStation station = new WeatherStation();
        Hygrometer hygrometer = mock(Hygrometer.class);
        Barometer barometer = mock(Barometer.class);
        Thermometer thermometer = mock(Thermometer.class);
        SatelliteUplink uplink = spy(new SatelliteUplink());
        station.setHygrometer(hygrometer);
        station.setBarometer(barometer);
        station.setThermometer(thermometer);
        station.setSatelliteUplink(uplink);


        when(hygrometer.getCurrentHumidity()).thenReturn(30.0, 50.0, 30.0);
        when(barometer.getAtmosphericPressure()).thenReturn(1000.0, 800.0, 799.0);
        when(thermometer.getCurrentTemperature()).thenReturn(60.0);

        boolean quiet = station.runStormWarningCheck();
        boolean localWarning = station.runStormWarningCheck();
        boolean nearbyWarning = station.runStormWarningCheck();

        assertAll("Storm readings and satellite delegation",
                () -> assertFalse(quiet),
                () -> assertTrue(localWarning),
                () -> assertTrue(nearbyWarning),
                () -> verify(hygrometer, times(3)).getCurrentHumidity(),
                () -> verify(barometer, times(3)).getAtmosphericPressure(),
                () -> verify(thermometer, times(3)).getCurrentTemperature(),
                () -> verify(uplink).runStormCheckForArea(50.0, 800.0, 60.0),
                () -> verify(uplink).runStormCheckForArea(30.0, 799.0, 60.0),
                () -> verify(uplink).checkNearbyAreaStorms());
    }

    @Test
    void tornadoWarningTests() {
        WeatherStation station = new WeatherStation();
        Hygrometer hygrometer = mock(Hygrometer.class);
        Barometer barometer = mock(Barometer.class);
        Anemometer anemometer = mock(Anemometer.class);
        SatelliteUplink uplink = spy(new SatelliteUplink());
        station.setHygrometer(hygrometer);
        station.setBarometer(barometer);
        station.setAnemometer(anemometer);
        station.setSatelliteUplink(uplink);

        when(hygrometer.getCurrentHumidity()).thenReturn(30.0);
        when(barometer.getAtmosphericPressure()).thenReturn(1000.0, 799.0, 1000.0);
        when(anemometer.getWindSpeed()).thenReturn(15.0, 20.0, 1000.0);

        boolean quiet = station.runTornadoWarningCheck();
        boolean localWarning = station.runTornadoWarningCheck();
        boolean nearbyWarning = station.runTornadoWarningCheck();

        assertAll("Tornado argument order and nearby-area fallback",
                () -> assertFalse(quiet),
                () -> assertTrue(localWarning),
                () -> assertTrue(nearbyWarning),
                () -> verify(hygrometer, times(3)).getCurrentHumidity(),
                () -> verify(barometer, times(3)).getAtmosphericPressure(),
                () -> verify(anemometer, times(3)).getWindSpeed(),
                // Expected order is humidity, pressure, wind speed.
                () -> verify(uplink).runTornadoCheckForArea(30.0, 799.0, 20.0),
                () -> verify(uplink).runTornadoCheckForArea(30.0, 1000.0, 1000.0),
                () -> verify(uplink).checkNearbyAreaTornadoes(),
                () -> verify(uplink, never()).checkNearbyAreaStorms());
    }

    @Test
    void anemometerCalibrationTests() {
        WeatherStation station = new WeatherStation();
        Anemometer anemometer = mock(Anemometer.class);
        station.setAnemometer(anemometer);
        when(anemometer.getWindSpeed()).thenReturn(10.0, 11.0);
        when(anemometer.getWindDirInDegrees()).thenReturn(90.0, 110.0);

        assertAll(
                () -> assertTrue(station.anemometerCalibrationCheck()),
                () -> verify(anemometer, times(2)).getWindSpeed(),
                () -> verify(anemometer, times(2)).getWindDirInDegrees());
    }

    @Test
    void anemometerCalibrationRejectsTests() {
        WeatherStation station = new WeatherStation();
        Anemometer anemometer = mock(Anemometer.class);
        station.setAnemometer(anemometer);
        when(anemometer.getWindSpeed()).thenReturn(10.0, 12.0, 10.0, 10.0);
        when(anemometer.getWindDirInDegrees()).thenReturn(90.0, 90.0, 90.0, 111.0);

        assertAll(
                () -> assertFalse(station.anemometerCalibrationCheck(), "Speed changed by more than 1"),
                () -> assertFalse(station.anemometerCalibrationCheck(), "Direction changed by more than 20"),
                () -> verify(anemometer, times(4)).getWindSpeed(),
                () -> verify(anemometer, times(4)).getWindDirInDegrees());
    }

    @Test
    void temperatureCalibrationAcceptTests() {
        WeatherStation station = new WeatherStation();
        Thermometer thermometer = mock(Thermometer.class);
        station.setThermometer(thermometer);
        when(thermometer.getCurrentTemperature()).thenReturn(70.0, 71.0);

        assertAll("Calibration must use the supplied thermometer",
                () -> assertTrue(station.temperatureCalibrationTest()),
                () -> verify(thermometer, times(2)).getCurrentTemperature());
    }

    @Test
    void temperatureCalibrationRejectsTests() {
        WeatherStation station = new WeatherStation();
        Thermometer thermometer = mock(Thermometer.class);
        station.setThermometer(thermometer);
        when(thermometer.getCurrentTemperature()).thenReturn(70.0, 72.0);

        assertAll("Calibration must reject a temperature change greater than 1",
                () -> assertFalse(station.temperatureCalibrationTest()),
                () -> verify(thermometer, times(2)).getCurrentTemperature());
    }
}
