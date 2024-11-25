/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipt.eda21606.algorithm;

import com.ipt.eda21606.model.CityBean;
import com.ipt.eda21606.model.GraphBean;
import static com.ipt.eda21606.util.Constants.VEHICLE_AUTONOMY_KM;
import com.ipt.eda21606.util.DistanceUtils;
import java.util.*;

public class DijkstraAlgorithm {

    private GraphBean graph;

    public DijkstraAlgorithm(GraphBean graph) {
        this.graph = graph;
    }

    public Map<CityBean, Double> findShortestPath(CityBean start, CityBean end) {
        double autonomy = VEHICLE_AUTONOMY_KM;
        Map<CityBean, Double> distances = new HashMap<>(); // Mapa de distâncias mínimas
        Map<CityBean, CityBean> predecessors = new HashMap<>(); // Mapa de predecessores
        Set<CityBean> unvisitedCities = new HashSet<>(); // Conjunto de cidades não visitadas

        // Inicializa todas as distâncias para infinito e adiciona todas as cidades ao conjunto não visitado
        for (CityBean city : graph.getCities()) {
            distances.put(city, Double.POSITIVE_INFINITY);
            unvisitedCities.add(city);
        }
        distances.put(start, 0.0); // A distância da cidade inicial é 0

        while (!unvisitedCities.isEmpty()) {
            // Obtém a cidade com a menor distância
            CityBean currentCityBean = getCityBeanWithMinimumDistance(unvisitedCities, distances);

            if (currentCityBean == null) {
                // Não há mais cidades conectadas acessíveis
                System.err.println("No city found with minimum distance. Exiting loop.");
                break;
            }

            System.out.println("Processing city: " + currentCityBean.getName());
            System.out.println("Current distance: " + distances.get(currentCityBean));

            // Se a distância da cidade atual for infinita, significa que não há caminho
            if (distances.get(currentCityBean) == Double.POSITIVE_INFINITY) {
                System.out.println("All remaining cities are unreachable. Exiting loop.");
                break;
            }

            // Atualiza as distâncias para os vizinhos
            for (CityBean neighbor : graph.getAdjacencies(currentCityBean)) {
                System.out.println("Checking neighbor: " + neighbor.getName());

                // Calcula a distância entre a cidade atual e o vizinho
                double edgeDistance = DistanceUtils.calculateDistance(
                        currentCityBean.getLatitude(), currentCityBean.getLongitude(),
                        neighbor.getLatitude(), neighbor.getLongitude()
                );

                // Verifica se a distância da aresta excede a autonomia
                if (edgeDistance > autonomy) {
                    System.out.println("Edge from " + currentCityBean.getName() + " to " + neighbor.getName()
                            + " exceeds autonomy (" + edgeDistance + " > " + autonomy + "). Skipping.");
                    continue; // Ignora esta aresta
                }

                // Calcula a nova distância acumulada
                double distance = distances.get(currentCityBean) + edgeDistance;

                // Atualiza a distância mínima se o novo caminho for mais curto
                if (distance < distances.get(neighbor)) {
                    System.out.println("Updating distance for " + neighbor.getName() + " to " + distance);
                    distances.put(neighbor, distance);
                    predecessors.put(neighbor, currentCityBean);
                }
            }

            // Marca a cidade atual como visitada
            unvisitedCities.remove(currentCityBean);
            System.out.println("Remaining unvisited cities: " + unvisitedCities);
        }

        // Reconstrução do caminho, se necessário
        if (predecessors.containsKey(end)) {
            List<CityBean> path = new ArrayList<>();
            for (CityBean city = end; city != null; city = predecessors.get(city)) {
                path.add(city);
            }
            Collections.reverse(path);
            System.out.println("Shortest path: " + path.stream().map(CityBean::getName).toList());
        } else {
            System.out.println("No path exists from " + start.getName() + " to " + end.getName());
        }

        return distances; // Retorna o mapa de distâncias calculadas
    }

    private CityBean getCityBeanWithMinimumDistance(Set<CityBean> unvisitedCities, Map<CityBean, Double> distances) {
        CityBean minCityBean = null;
        double minDistance = Double.POSITIVE_INFINITY;

        for (CityBean city : unvisitedCities) {
            Double cityDistance = distances.get(city); // Obtém a distância da cidade
            if (cityDistance != null && cityDistance < minDistance) {
                minDistance = cityDistance;
                minCityBean = city;
            }
        }

        return minCityBean; // Retorna null se nenhuma cidade tiver distância válida
    }

}
