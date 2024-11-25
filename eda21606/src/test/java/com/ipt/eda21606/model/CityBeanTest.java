/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.ipt.eda21606.model;

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
public class CityBeanTest {
    
    public CityBeanTest() {
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

    @Test
    void testEquals_SameObject() {
        CityBean city = new CityBean("Lisbon", 38.716, -9.139);
        assertTrue(city.equals(city), "A city should be equal to itself.");
    }

    @Test
    void testEquals_EqualObjects() {
        CityBean city1 = new CityBean("Lisbon", 38.716, -9.139);
        CityBean city2 = new CityBean("Lisbon", 38.716, -9.139);
        assertTrue(city1.equals(city2), "Cities with the same attributes should be equal.");
    }

    @Test
    void testEquals_DifferentName() {
        CityBean city1 = new CityBean("Lisbon", 38.716, -9.139);
        CityBean city2 = new CityBean("Porto", 38.716, -9.139);
        assertFalse(city1.equals(city2), "Cities with different names should not be equal.");
    }

    @Test
    void testEquals_DifferentCoordinates() {
        CityBean city1 = new CityBean("Lisbon", 38.716, -9.139);
        CityBean city2 = new CityBean("Lisbon", 40.0, -8.0);
        assertFalse(city1.equals(city2), "Cities with different coordinates should not be equal.");
    }

    @Test
    void testEquals_Null() {
        CityBean city = new CityBean("Lisbon", 38.716, -9.139);
        assertFalse(city.equals(null), "A city should not be equal to null.");
    }

    @Test
    void testEquals_DifferentType() {
        CityBean city = new CityBean("Lisbon", 38.716, -9.139);
        String notACity = "Not a city";
        assertFalse(city.equals(notACity), "A city should not be equal to an object of a different type.");
    }
}
