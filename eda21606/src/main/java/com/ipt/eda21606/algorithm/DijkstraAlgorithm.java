/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipt.eda21606.algorithm;

import com.ipt.eda21606.model.CityBean;
import com.ipt.eda21606.model.GraphBean;
import com.ipt.eda21606.util.DistanceUtils;
import java.util.*;

public class DijkstraAlgorithm {

    private GraphBean graph;

    public DijkstraAlgorithm(GraphBean graph) {
        this.graph = graph;
    }

    public void findShortestPath(CityBean start, CityBean end) {
        Map<CityBean, Double> distances = new HashMap<>();
        Map<CityBean, CityBean> predecessors = new HashMap<>();
        Set<CityBean> unvisitedCities = new HashSet<>();

        // Inicializa todas as distâncias para infinito e a cidade de origem com distância 0
        for (CityBean city : graph.getAdjacencies(start)) {
            distances.put(city, Double.POSITIVE_INFINITY);
            unvisitedCities.add(city);
        }
        distances.put(start, 0.0); // A distância da cidade de origem até ela mesma é 0
        unvisitedCities.add(start); // Adiciona a cidade de origem ao conjunto de cidades não visitadas

        // Algoritmo de Dijkstra
        while (!unvisitedCities.isEmpty()) {
            // Escolhe a cidade com a menor distância
            CityBean currentCityBean = getCityBeanWithMinimumDistance(unvisitedCities, distances);

            // Se a distância para a cidade de destino for infinita, significa que não há caminho
            if (distances.get(currentCityBean) == Double.POSITIVE_INFINITY) {
                break;
            }

            // Para cada cidade vizinha
            for (CityBean neighbor : graph.getAdjacencies(currentCityBean)) {
                // Garantir que o vizinho tenha uma entrada válida no mapa
                if (!distances.containsKey(neighbor)) {
                    distances.put(neighbor, Double.POSITIVE_INFINITY);
                }

                double distance = distances.get(currentCityBean) + DistanceUtils.calculateDistance(
                        currentCityBean.getLatitude(), currentCityBean.getLongitude(),
                        neighbor.getLatitude(), neighbor.getLongitude()
                );

                // Se o novo caminho for mais curto
                if (distance < distances.get(neighbor)) {
                    distances.put(neighbor, distance);
                    predecessors.put(neighbor, currentCityBean);
                }
            }

            // Marca a cidade atual como visitada
            unvisitedCities.remove(currentCityBean);
        }

        // Reconstrução do caminho
        List<CityBean> path = new ArrayList<>();
        for (CityBean city = end; city != null; city = predecessors.get(city)) {
            path.add(city);
        }
        Collections.reverse(path);

        // Exibe o caminho
        System.out.println("Shortest path from " + start.getName() + " to " + end.getName() + ":");
        for (CityBean city : path) {
            System.out.print(city.getName() + " -> ");
        }
        System.out.println("Total distance: " + distances.get(end) + " km");
    }

    private CityBean getCityBeanWithMinimumDistance(Set<CityBean> unvisitedCities, Map<CityBean, Double> distances) {
        CityBean minCityBean = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (CityBean city : unvisitedCities) {
            double cityDistance = distances.get(city);
            if (cityDistance < minDistance) {
                minDistance = cityDistance;
                minCityBean = city;
            }
        }
        return minCityBean;
    }
}