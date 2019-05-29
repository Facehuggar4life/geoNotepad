package com.example.geonotepad;

import android.location.Location;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataManipulator {
    final String fileName = "locationData/location_data.txt";

    public void writeLocationData(String locationName, Location pictureLocation) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(locationName+'\n');
        writer.write(pictureLocation.getLongitude()+":"+pictureLocation.getLatitude()+'\n');
        //Writes the name of the photo file and the Longitude and Latitude separated by a colon
    }

    public Coordinate readLocationData(String locationName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String currentLine = reader.readLine();
        while (currentLine != null) {
            if (currentLine == locationName) {
                currentLine = reader.readLine();
                Coordinate parsedCoordinate = parseCoordinates(currentLine);
                return parsedCoordinate;

            } else {
                currentLine = reader.readLine();
            }
        }
        return null;
    }
    public Coordinate parseCoordinates(String stringCoordinates){
        String Long = stringCoordinates.substring(0, (stringCoordinates.indexOf(':'))-1);
        String Lat = stringCoordinates.substring(stringCoordinates.indexOf(':')+1, stringCoordinates.length()-1);
        Coordinate coordinate = new Coordinate(Long, Lat);
        return coordinate;
    }
}
