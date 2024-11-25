/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.ipt.eda21606.algorithm;

import com.ipt.eda21606.model.CityBean;
import com.ipt.eda21606.model.GraphBean;
import com.ipt.eda21606.util.DistanceUtils;
import java.util.Map;
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
public class DijkstraAlgorithmTest2 {

    private GraphBean graph;
    private CityBean cityA, cityB, cityC, cityD;

    public DijkstraAlgorithmTest2() {
    }

    @BeforeEach
    void setUp() {
        // Inicializando o grafo e cidades
        graph = new GraphBean();

        cityA = CityBean.builder().name("CityA").latitude(0.0).longitude(0.0).build();
        cityB = CityBean.builder().name("CityB").latitude(1.0).longitude(1.0).build(); // Dentro da autonomia
        cityC = CityBean.builder().name("CityC").latitude(5.0).longitude(5.0).build(); // Fora da autonomia
        cityD = CityBean.builder().name("CityD").latitude(2.0).longitude(2.0).build(); // Dentro da autonomia

        // Adicionando cidades ao grafo
        graph.addCity(cityA);
        graph.addCity(cityB);
        graph.addCity(cityC);
        graph.addCity(cityD);

        // Adicionando arestas (conexões)
        graph.addEdge(cityA, cityB); // CidadeA -> CidadeB (dentro da autonomia)
        graph.addEdge(cityA, cityC); // CidadeA -> CidadeC (fora da autonomia, deve ser ignorada)
        graph.addEdge(cityB, cityD); // CidadeB -> CidadeD (dentro da autonomia)
    }

    @Test
    void testAutonomyExceedance() {
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

        // Calculando o menor caminho de CityA a CityD
        Map<CityBean, Double> distances = dijkstra.findShortestPath(cityA, cityD);

        // A distância esperada: CityA -> CityB -> CityD
        double distanceAB = DistanceUtils.calculateDistance(cityA.getLatitude(), cityA.getLongitude(),
                cityB.getLatitude(), cityB.getLongitude());
        double distanceBD = DistanceUtils.calculateDistance(cityB.getLatitude(), cityB.getLongitude(),
                cityD.getLatitude(), cityD.getLongitude());
        double expectedDistance = distanceAB + distanceBD;

        // Verifica se a distância calculada corresponde ao esperado
        assertEquals(expectedDistance, distances.get(cityD), "Distance should match the expected value.");

        // Verifica que CityC não foi processada (não deve ser conectada a CityA)
        assertEquals(Double.POSITIVE_INFINITY, distances.get(cityC), "CityC should not be reachable because its edge exceeds the autonomy.");

    }
}
