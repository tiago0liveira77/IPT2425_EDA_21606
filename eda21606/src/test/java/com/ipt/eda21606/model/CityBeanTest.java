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
        CityBean city = CityBean.builder()
                .name("Lisboa") // name
                .nameAscii("Lisboa") // nameAscii
                .latitude(22.2) // latitude
                .longitude(22.2) // longitude
                .pais("Portugal") // pais (country)
                .iso2("") // iso2
                .iso3("") // iso3
                .adminName("") // adminName
                .capital("") // capital
                .population("123") // population
                .id("123") // id
                .build();

        assertTrue(city.equals(city), "A city should be equal to itself.");
    }

    @Test
    void testEquals_EqualObjects() {
        CityBean city1 = CityBean.builder()
                .name("Lisboa") // name
                .nameAscii("Lisboa") // nameAscii
                .latitude(22.2) // latitude
                .longitude(22.2) // longitude
                .pais("Portugal") // pais (country)
                .iso2("") // iso2
                .iso3("") // iso3
                .adminName("") // adminName
                .capital("") // capital
                .population("123") // population
                .id("123") // id
                .build();
        CityBean city2 = CityBean.builder()
                .name("Lisboa") // name
                .nameAscii("Lisboa") // nameAscii
                .latitude(22.2) // latitude
                .longitude(22.2) // longitude
                .pais("Portugal") // pais (country)
                .iso2("") // iso2
                .iso3("") // iso3
                .adminName("") // adminName
                .capital("") // capital
                .population("123") // population
                .id("123") // id
                .build();
        assertTrue(city1.equals(city2), "Cities with the same attributes should be equal.");
    }

    @Test
    void testEquals_DifferentName() {
       CityBean city1 = CityBean.builder()
                .name("Lisboa") // name
                .nameAscii("Lisboa") // nameAscii
                .latitude(22.2) // latitude
                .longitude(22.2) // longitude
                .pais("Portugal") // pais (country)
                .iso2("") // iso2
                .iso3("") // iso3
                .adminName("") // adminName
                .capital("") // capital
                .population("123") // population
                .id("123") // id
                .build();
        CityBean city2 = CityBean.builder()
                .name("Lisboa2") // name
                .nameAscii("Lisboa") // nameAscii
                .latitude(33.2) // latitude
                .longitude(33.2) // longitude
                .pais("Portugal") // pais (country)
                .iso2("") // iso2
                .iso3("") // iso3
                .adminName("") // adminName
                .capital("") // capital
                .population("123") // population
                .id("123") // id
                .build();
        assertFalse(city1.equals(city2), "Cities with different names should not be equal.");
    }

    @Test
    void testEquals_DifferentCoordinates() {
        CityBean city1 = CityBean.builder()
                .name("Lisboa") // name
                .nameAscii("Lisboa") // nameAscii
                .latitude(22.2) // latitude
                .longitude(22.2) // longitude
                .pais("Portugal") // pais (country)
                .iso2("") // iso2
                .iso3("") // iso3
                .adminName("") // adminName
                .capital("") // capital
                .population("123") // population
                .id("123") // id
                .build();
        CityBean city2 = CityBean.builder()
                .name("Lisboa") // name
                .nameAscii("Lisboa") // nameAscii
                .latitude(33.2) // latitude
                .longitude(33.2) // longitude
                .pais("Portugal") // pais (country)
                .iso2("") // iso2
                .iso3("") // iso3
                .adminName("") // adminName
                .capital("") // capital
                .population("123") // population
                .id("123") // id
                .build();
        assertFalse(city1.equals(city2), "Cities with different coordinates should not be equal.");
    }

    @Test
    void testEquals_Null() {
        CityBean city = CityBean.builder()
                .name("Lisboa") // name
                .nameAscii("Lisboa") // nameAscii
                .latitude(33.2) // latitude
                .longitude(33.2) // longitude
                .pais("Portugal") // pais (country)
                .iso2("") // iso2
                .iso3("") // iso3
                .adminName("") // adminName
                .capital("") // capital
                .population("123") // population
                .id("123") // id
                .build();
        assertFalse(city.equals(null), "A city should not be equal to null.");
    }

    @Test
    void testEquals_DifferentType() {
         CityBean city = CityBean.builder()
                .name("Lisboa") // name
                .nameAscii("Lisboa") // nameAscii
                .latitude(33.2) // latitude
                .longitude(33.2) // longitude
                .pais("Portugal") // pais (country)
                .iso2("") // iso2
                .iso3("") // iso3
                .adminName("") // adminName
                .capital("") // capital
                .population("123") // population
                .id("123") // id
                .build();
        String notACity = "Not a city";
        assertFalse(city.equals(notACity), "A city should not be equal to an object of a different type.");
    }
}
