/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.ipt.eda21606.main;

import com.ipt.eda21606.algorithm.DijkstraAlgorithm;
import com.ipt.eda21606.model.CityBean;
import com.ipt.eda21606.model.GraphBean;
import com.ipt.eda21606.service.RouteService;
import com.ipt.eda21606.util.CSVUtils;
import static com.ipt.eda21606.util.Constants.INPUT_FILE_PATH;
import static com.ipt.eda21606.util.DistanceUtils.*;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    
    private static final Logger logger = LogManager.getLogger(Main.class);
    
    static Map<CityBean, Double> distances;
    
    public static void main(String[] args) {
        List<CityBean> cities = CSVUtils.readCities(INPUT_FILE_PATH);
        GraphBean graph = RouteService.buildGraph(cities);
        // Criar o algoritmo de Dijkstra e calcular o caminho mais curto
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        distances = dijkstra.findShortestPath(cities.get(1), cities.get(4));
        printAllDistances(distances);
        getClosestCity(distances);
        
        
    }
}
