/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.ipt.eda21606.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author pcjoa
 */
public class DistanceUtilsTest {

    public DistanceUtilsTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of calculateDistance method, of class DistanceUtils.
     */
    @Test
    void testCalculateDistance() {
        double lat1 = 38.717;
        double lon1 = -9.139;
        double lat2 = 41.1579;
        double lon2 = -8.6291;

        // Distância aproximada entre Lisboa e Porto
        double expectedDistance = 274.0; // valor aproximado em km

        double calculatedDistance = DistanceUtils.calculateDistance(lat1, lon1, lat2, lon2);

        assertEquals(expectedDistance, calculatedDistance, 5.0, "Distance should be within a 5 km tolerance.");
    }

    @Test
    void testCalculateDistanceSamePoint() {
        double lat = 38.716;
        double lon = -9.139;

        double calculatedDistance = DistanceUtils.calculateDistance(lat, lon, lat, lon);

        assertEquals(0.0, calculatedDistance, "Distance between the same point should be zero.");
    }

}
