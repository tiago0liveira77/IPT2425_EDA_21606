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
import java.util.List;


public class Main {

    public static void main(String[] args) {
        System.out.println("--> 1 Read Cities");
        List<CityBean> cities = CSVUtils.readCities(INPUT_FILE_PATH);
        
        System.out.println("--> 2 Print test Cities");
        cities.stream().limit(10).forEach(System.out::println);
        
        // Build the graph
        System.out.println("--> 3 Building graph");
        GraphBean graph = RouteService.buildGraph(cities);

        // Display part of the graph to check the connections
        System.out.println("--> 4 Display grah");
        graph.displayGraph();
        
         // Escolher duas cidades (exemplo)
        System.out.println("--> 5 Test get closest");
        CityBean city1 = cities.get(0);  
        CityBean city2 = cities.get(8); 

        // Criar o algoritmo de Dijkstra e calcular o caminho mais curto
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.findShortestPath(city1, city2);
    }
}
