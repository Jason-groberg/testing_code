# Lab: More Mocking with Mockito

## Spies, Consecutive Calls, and Practice

# Lab

In this lab, you will be testing the provided WeatherStation class. This is a simple program that takes measurement data from instruments in the local area and uses that data to determine if there is a possibility of a catastrophic weather event. None of the instruments inside the ‘instruments’ package are currently implemented so you will be using mocks to provide data to the station to make sure the warning systems are working as intended.

One tool that the Weather Station uses that has already been written is the SatelliteUplink class. This is a tool used to provide secondary data and info on nearby stations to make suggestions on whether the station should or should not issue a warning. Since the SatelliteUplink class is already written, you will be using the Mockito Spy feature to verify that the Uplink is working as intended. The Uplink has already been provided with dummy data so you do not need to mock it to provide data for the SatelliteUplink class.

You will be responsible for writing tests for each of the public methods in the WeatherStation class (not including setters) according to the following requirements:

* **Your tests should achieve 100% line coverage for WeatherStation and SatelliteUplink.**
    * **Note:** You should **not** create a SatellitleUplinkTest.java file. Line coverage for this class will be achieved by the fact that it is called by the WeatherStation class, so its methods will be called when your tests call methods on the WeatherStation class.
* You must use ‘verify’ to confirm that each of the following is called in at least one test:
    * checkNearbyAreaStorms is called in the runStormCheckForArea method.
    * checkNearbyAreaTornados is called in the runTornadoCheckForArea method.
    * For each mock you create for an instrument, verify that the methods on that instrument are called.
    * **Note:** For all verifies on the SatelliteUplink class, you will need to use spies as discussed in the reading.
* For the calibration test methods (anemometerCalibrationCheck() and temperatureCalibrationCheck()) you will need to write two test methods each.
    * one positive case (true returned from the method)
    * one negative case (false returned from the method)
    * **Hint:** This can only be done with consecutive calls as described in the reading.

It is important to remember that using mocks to test implies that you are doing white box testing, meaning that you should know what is going on in the code to write your tests. To help you with your tests, we provided specifications of the SatelliteUplink class and how each method should work by annotating the class with Javadocs. To see a formatted version of Javadocs in IntelliJ, click on the button shown below in the red box next to the Javadoc:


# WARNING

There are 3 bugs in the project. You can look for them if you like, but they are specifically designed so that if you are using verify and proper mocking techniques you will be able to easily find and fix them. Part of the assignment is to fix the bugs.