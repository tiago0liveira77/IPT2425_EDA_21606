/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipt.eda21606.algorithm;

import com.ipt.eda21606.main.MainGUI;
import com.ipt.eda21606.model.CityBean;
import com.ipt.eda21606.model.GraphBean;
import static com.ipt.eda21606.util.Constants.VEHICLE_AUTONOMY_KM_OLD;
import com.ipt.eda21606.util.DistanceUtils;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DijkstraAlgorithm {

    private GraphBean graph;

    private static final Logger logger = LogManager.getLogger(DijkstraAlgorithm.class);

    public DijkstraAlgorithm(GraphBean graph) {
        this.graph = graph;
    }

    public Map<CityBean, Double> findShortestPath(CityBean start, CityBean end, double autonomy) {
        if (start == null || end == null) {
            if (logger.isErrorEnabled()){
                logger.error("UMA DAS CIDADES NÃO EXISTE");     
            }
            return null;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("|-> |FindShortestPath| From: " + start.getName() + " To: " + end.getName());
        }
        //double autonomy = VEHICLE_AUTONOMY_KM_OLD;
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
                if (logger.isDebugEnabled()) {
                    logger.debug("|---> No city found with minimum distance. Exiting loop.");
                    logger.debug("|--------------------------------------------------------|");
                }
                break;
            }

            if (logger.isDebugEnabled()) {
                logger.debug("|--> Processing city: " + currentCityBean.getName());
                logger.debug("|--> Current distance: " + distances.get(currentCityBean));
            }

            // Se a distância da cidade atual for infinita, significa que não há caminho
            if (distances.get(currentCityBean) == Double.POSITIVE_INFINITY) {
                if (logger.isDebugEnabled()) {
                    logger.debug("|---> All remaining cities are unreachable. Exiting loop.");
                    logger.debug("|--------------------------------------------------------|");
                }
                break;
            }

            // Atualiza as distâncias para os vizinhos
            for (CityBean neighbor : graph.getAdjacencies(currentCityBean)) {
                if (neighbor == null){
                    if (logger.isDebugEnabled()) {
                    logger.debug("|---> neighbor null");
                }
                    continue;
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("|---> Checking neighbor: " + neighbor.getName());
                }

                // Calcula a distância entre a cidade atual e o vizinho
                double edgeDistance = DistanceUtils.calculateDistance(
                        currentCityBean.getLatitude(), currentCityBean.getLongitude(),
                        neighbor.getLatitude(), neighbor.getLongitude()
                );

                // Verifica se a distância da aresta excede a autonomia
                if (edgeDistance > autonomy) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("|---> Edge from " + currentCityBean.getName() + " to " + neighbor.getName()
                                + " exceeds autonomy (" + edgeDistance + " > " + autonomy + "). Skipping.");
                    }
                    continue; // Ignora esta aresta
                }

                // Calcula a nova distância acumulada
                double distance = distances.get(currentCityBean) + edgeDistance;

                // Atualiza a distância mínima se o novo caminho for mais curto
                if (distances.get(neighbor) != null) {
                    if (distance < distances.get(neighbor)) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("|----> Updating distance for " + neighbor.getName() + " to " + distance);
                        }
                        distances.put(neighbor, distance);
                        predecessors.put(neighbor, currentCityBean);
                    }
                }
            }

            // Marca a cidade atual como visitada
            unvisitedCities.remove(currentCityBean);
            if (logger.isDebugEnabled()) {
                logger.debug("|--> Remaining unvisited cities: " + unvisitedCities);
            }
        }

        // Reconstrução do caminho, se necessário
        if (predecessors.containsKey(end)) {
            List<CityBean> path = new ArrayList<>();
            for (CityBean city = end; city != null; city = predecessors.get(city)) {
                path.add(city);
            }
            Collections.reverse(path);

            if (logger.isDebugEnabled()) {
                logger.debug("|----> Shortest path: " + path.stream().map(CityBean::getName).toList());
            }

            // Adicionando o log da distância entre cada aresta no caminho
            if (path.size() > 1 && logger.isDebugEnabled()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("|----> Edge distances:");
                }
                StringBuilder outputRoute = new StringBuilder();
                double totalDistance = 0.0;
                for (int i = 0; i < path.size() - 1; i++) {
                    CityBean from = path.get(i);
                    CityBean to = path.get(i + 1);
                    double edgeDistance = DistanceUtils.calculateDistance(
                            from.getLatitude(), from.getLongitude(),
                            to.getLatitude(), to.getLongitude()
                    );
                    if (logger.isDebugEnabled()) {
                        logger.debug("|-------> From " + from.getName() + " to " + to.getName() + ": " + edgeDistance + " km");
                    }
                    outputRoute.append(String.format("%s -> %s = %.2f \n", from.getName(), to.getName(), edgeDistance));
                    totalDistance += edgeDistance;
                }
                outputRoute.append("Distancia total: " + totalDistance);
                MainGUI.route = outputRoute.toString();
                if (logger.isDebugEnabled()) {
                    logger.debug("|-------------------------------------------------------------------------------------");
                }
            }

        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("|----> No path exists from " + start.getName() + " to " + end.getName());
                logger.debug("|-------------------------------------------------------------------------------------");
            }
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
