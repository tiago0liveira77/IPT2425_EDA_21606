/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.ipt.eda21606.main;

import com.ipt.eda21606.algorithm.DijkstraAlgorithm;
import com.ipt.eda21606.model.CityBean;
import com.ipt.eda21606.model.GraphBean;
import com.ipt.eda21606.service.RouteService;
import com.ipt.eda21606.util.CSVUtils;
import static com.ipt.eda21606.util.Constants.*;
import static com.ipt.eda21606.util.DistanceUtils.*;
import com.ipt.eda21606.util.FileInOutUtils;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    
    private static final Logger logger = LogManager.getLogger(Main.class);
    
    static Map<CityBean, Double> distances;
    static GraphBean graph;
    
    public static void main(String[] args) {
        //test_ler_guardar();
        test_ler_grah_guardado();
        test_dist();
        //Ler ficheiro
        //List<CityBean> cities = FileInOutUtils.readRawFile(INPUT_RAW_FILE_PATH);
        //Criar grafo
        //GraphBean graph = RouteService.buildGraph(cities);
        //FileInOutUtils.saveGraphFile(graph, INPUT_GRAPH_FILE_PATH);
        //GraphBean graph = FileInOutUtils.readGraphFile(INPUT_GRAPH_FILE_PATH);
        //graph.displayGraph();
        //Usar algoritmo dijkstra para calcular caminho mais curto entre todas as cidades
        //DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        //Calcular caminho mais curto entre 2 cidades
        //distances = dijkstra.findShortestPath(cities.get(1), cities.get(4));
        //TESTS - mostrar todas as distancias da cidade origem para todas as outras existentes
        //printAllDistances(distances);
        
    }
    
    public static void test_dist(){
        Set<CityBean> cities = graph.getCities();
        CityBean targetCity = cities.stream()
        .filter(city -> "Resende".equalsIgnoreCase(city.getName()))
                .findFirst()
                .orElse(null);
        CityBean targetCity2 = cities.stream()
        .filter(city -> "Almada".equalsIgnoreCase(city.getName()))
                .findFirst()
                .orElse(null);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);  
        distances = dijkstra.findShortestPath(targetCity, targetCity2);
    }
    
    public static void test_ler_guardar(){
        List<CityBean> cities = FileInOutUtils.readRawFile(INPUT_RAW_FILE_PATH);
        GraphBean graph = RouteService.buildGraph(cities);
        FileInOutUtils.saveGraphFile(graph, INPUT_GRAPH_FILE_PATH);
    }
    
    public static void test_ler_grah_guardado(){
        graph = FileInOutUtils.readGraphFile(INPUT_GRAPH_FILE_PATH);
        graph.displayGraph();
    }
    
}
