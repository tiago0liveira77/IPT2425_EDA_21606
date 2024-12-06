/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.ipt.eda21606.algorithm;

import com.ipt.eda21606.model.CityBean;
import com.ipt.eda21606.model.GraphBean;
import static com.ipt.eda21606.util.Constants.VEHICLE_AUTONOMY_KM_OLD;
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
public class DijkstraAlgorithmTest {

    private GraphBean graph;
    private CityBean lisbon;
    private CityBean porto;
    private CityBean faro;

    public DijkstraAlgorithmTest() {
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

        graph.addCity(lisbon);
        graph.addCity(porto);
        graph.addCity(faro);

        graph.addEdge(lisbon, porto);
        graph.addEdge(lisbon, faro);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void testSimpleGraph() {
        System.out.println("\n----- testSimpleGraph -----");
        GraphBean graph2 = new GraphBean();
        CityBean city1 = CityBean.builder()
                .name("City1")
                .latitude(0.0)
                .longitude(0.0)
                .build();
        CityBean city2 = CityBean.builder()
                .name("City2")
                .latitude(1.0)
                .longitude(1.0)
                .build();

        graph2.addCity(city1);
        graph2.addCity(city2);
        graph2.addEdge(city1, city2);

        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph2);

        Map<CityBean, Double> distances = dijkstra.findShortestPath(city1, city2, VEHICLE_AUTONOMY_KM_OLD);

        assertEquals(0.0, distances.get(city1), "Start city should have distance 0.");
        assertNotNull(distances.get(city2), "End city should have a distance.");
    }

    @Test
    void testShortestPath() {
        System.out.println("\n----- testShortestPath -----");
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

        graph.displayGraph();

        // Calcula o menor caminho entre Lisboa e Porto
        Map<CityBean, Double> distances = dijkstra.findShortestPath(lisbon, porto, VEHICLE_AUTONOMY_KM_OLD);

        // Distância esperada
        double expectedDistance = DistanceUtils.calculateDistance(
                lisbon.getLatitude(), lisbon.getLongitude(),
                porto.getLatitude(), porto.getLongitude()
        );

        // Verifica se a distância calculada está correta
        if(expectedDistance <= VEHICLE_AUTONOMY_KM_OLD){
            assertEquals(expectedDistance, distances.get(porto), "Distance should match the expected value.");
        }
        System.out.println("Distance is greater than autonomy");
        
    }

    @Test
    void testNoPathExistsWithNewGraph() {
        System.out.println("\n----- testNoPathExistsWithNewGraph -----");
        GraphBean newGraph = new GraphBean();
        newGraph.addCity(lisbon);
        newGraph.addCity(faro);
        // Não adiciona aresta entre Lisboa e Faro, simulando que não há caminho

        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(newGraph);

        Map<CityBean, Double> distances = dijkstra.findShortestPath(lisbon, faro, VEHICLE_AUTONOMY_KM_OLD);

        // Verifica se a cidade está no mapa e se a distância é infinita
        assertTrue(distances.containsKey(faro), "The city should exist in the distances map.");
        assertEquals(Double.POSITIVE_INFINITY, distances.get(faro), "Distance should be infinity if no path exists.");
    }

    @Test
    void testShortestPathWithIntermediateCities() {
        // Configurando o grafo com cidades
        GraphBean graph = new GraphBean();

        CityBean cityA = CityBean.builder().name("CityA").latitude(0.0).longitude(0.0).build();
        CityBean cityB = CityBean.builder().name("CityB").latitude(1.0).longitude(1.0).build(); // Distância <= 200 km
        CityBean cityC = CityBean.builder().name("CityC").latitude(5.0).longitude(5.0).build(); // Distância > 200 km
        CityBean cityD = CityBean.builder().name("CityD").latitude(1.5).longitude(1.5).build(); // Continuação de CityB para o destino final

        // Adicionando cidades ao grafo
        graph.addCity(cityA);
        graph.addCity(cityB);
        graph.addCity(cityC);
        graph.addCity(cityD);

        // Construindo conexões no grafo (apenas as dentro da autonomia)
        graph.addEdge(cityA, cityB); // CityA -> CityB (dentro da autonomia)
        graph.addEdge(cityB, cityD); // CityB -> CityD (dentro da autonomia)
        graph.addEdge(cityA, cityC);
        // Não adicionamos CityA -> CityC porque está fora da autonomia
        // Não adicionamos CityC -> CityD porque também está fora da autonomia

        // Algoritmo de Dijkstra
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

        // Calcula o menor caminho entre CityA e CityD
        Map<CityBean, Double> distances = dijkstra.findShortestPath(cityA, cityD, VEHICLE_AUTONOMY_KM_OLD);

        // Verifica o menor caminho esperado
        double distanceAB = DistanceUtils.calculateDistance(cityA.getLatitude(), cityA.getLongitude(),
                cityB.getLatitude(), cityB.getLongitude());
        double distanceBD = DistanceUtils.calculateDistance(cityB.getLatitude(), cityB.getLongitude(),
                cityD.getLatitude(), cityD.getLongitude());
        double expectedDistance = distanceAB + distanceBD;

        assertEquals(expectedDistance, distances.get(cityD), "Distance should match the expected value.");
    }

}
