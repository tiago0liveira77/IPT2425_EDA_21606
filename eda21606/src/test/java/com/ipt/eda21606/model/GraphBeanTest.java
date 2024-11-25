/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.ipt.eda21606.model;

import java.util.List;
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
public class GraphBeanTest {
    
    private GraphBean graph;
    private CityBean lisbon, porto, faro, leiria;
    
    public GraphBeanTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    void setUp() {
        graph = new GraphBean();

        lisbon = CityBean.builder()
                    .name("Lisbon")
                    .latitude(38.716)
                    .longitude(-9.139)
                    .build();
        porto = CityBean.builder()
                    .name("Porto")
                    .latitude(41.1579)
                    .longitude(-8.6291)
                    .build();
        
        faro = CityBean.builder()
                    .name("Faro")
                    .latitude(37.0194)
                    .longitude(-7.9308)
                    .build();
        
        leiria = CityBean.builder()
                    .name("Leiria")
                    .latitude(37.0194)
                    .longitude(-7.9308)
                    .build();

        graph.addCity(lisbon);
        graph.addCity(porto);
        graph.addCity(faro);
        graph.addCity(leiria);

        graph.addEdge(lisbon, porto);
        graph.addEdge(lisbon, faro);
    }
    
    @AfterEach
    public void tearDown() {
    }

     @Test
    void testGraphConnections() {
        List<CityBean> lisbonAdj = graph.getAdjacencies(lisbon);
        assertEquals(2, lisbonAdj.size(), "Lisbon should have 2 adjacent cities.");
        assertTrue(lisbonAdj.contains(porto), "Lisbon should be connected to Porto.");
        assertTrue(lisbonAdj.contains(faro), "Lisbon should be connected to Faro.");
    }

    @Test
    void testNoConnection() {
        List<CityBean> leiriaAdj = graph.getAdjacencies(leiria);
        assertTrue(leiriaAdj.isEmpty(), "Porto should not have any connections in this test setup.");
    }
    
}
