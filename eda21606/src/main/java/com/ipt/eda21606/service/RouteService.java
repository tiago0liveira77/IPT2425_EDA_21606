/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipt.eda21606.service;

import com.ipt.eda21606.model.CityBean;
import com.ipt.eda21606.model.GraphBean;
import com.ipt.eda21606.util.DistanceUtils;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RouteService {

    private static final Logger logger = LogManager.getLogger(RouteService.class);

    public static GraphBean buildGraph(List<CityBean> cities) {
        if (logger.isDebugEnabled()) {
            logger.debug("BUILDING GRAPH");
        }
        GraphBean graph = new GraphBean();

        // Filtrar cidades do país Portugal
        List<CityBean> portugueseCities = cities.parallelStream()
                .filter(city -> "Portugal".equalsIgnoreCase(city.getPais()))
                .toList();

        // Adicionar todas as cidades filtradas como vértices
        portugueseCities.parallelStream().forEach(graph::addCity);

        // Adicionar arestas entre cidades dentro do intervalo de autonomia
        portugueseCities.parallelStream().forEach(city1
                -> portugueseCities.parallelStream()
                        .filter(city2 -> !city1.equals(city2)) // Evitar comparar a cidade consigo mesma
                        .forEach(city2 -> {
                            double distance = DistanceUtils.calculateDistance(
                                    city1.getLatitude(), city1.getLongitude(),
                                    city2.getLatitude(), city2.getLongitude()
                            );

                            // if (distance <= VEHICLE_AUTONOMY_KM) {
                            graph.addEdge(city1, city2);
                            graph.addEdge(city2, city1); // Assumindo grafo não-direcionado
                            // }
                        })
        );

        return graph;
    }

    public static GraphBean buildGraphCountries(List<CityBean> cities, Set<String> countries) {
        if (logger.isDebugEnabled()) {
            logger.debug("BUILDING GRAPH FOR COUNTRIES: " + countries);
        }

        // Validar entrada
        if (cities == null || cities.isEmpty()) {
            logger.error("A lista de cidades está vazia ou nula.");
            return null;
        }

        if (countries == null || countries.isEmpty()) {
            logger.error("O conjunto de países está vazio ou nulo.");
            return null;
        }

        GraphBean graph = new GraphBean();

        // Filtrar cidades pelos países especificados
        List<CityBean> filteredCities = cities.parallelStream()
                .filter(city -> city != null && countries.contains(city.getPais()))
                .toList();

        if (filteredCities.isEmpty()) {
            logger.warn("Nenhuma cidade encontrada para os países especificados: " + countries);
            return null;
        }

        // Adicionar todas as cidades ao grafo
        filteredCities.parallelStream().forEach(graph::addCity);

        // Adicionar arestas entre cidades dentro do intervalo de autonomia
        filteredCities.parallelStream().forEach(city1
                -> filteredCities.parallelStream()
                        .filter(city2 -> !city1.equals(city2)) // Evitar comparar a cidade consigo mesma
                        .forEach(city2 -> {
                            double distance = DistanceUtils.calculateDistance(
                                    city1.getLatitude(), city1.getLongitude(),
                                    city2.getLatitude(), city2.getLongitude()
                            );

                            // if (distance <= VEHICLE_AUTONOMY_KM) { // Comentei aqui para contexto
                            graph.addEdge(city1, city2);
                            // }
                        })
        );

        return graph;
    }

}
