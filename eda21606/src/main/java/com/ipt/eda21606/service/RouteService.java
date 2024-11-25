/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipt.eda21606.service;

import com.ipt.eda21606.model.CityBean;
import com.ipt.eda21606.model.GraphBean;
import static com.ipt.eda21606.util.Constants.VEHICLE_AUTONOMY_KM;
import com.ipt.eda21606.util.DistanceUtils;
import java.util.List;

public class RouteService {
    
    public static GraphBean buildGraph(List<CityBean> cities) {
        GraphBean graph = new GraphBean();

        // Add all cities as vertices
        for (CityBean city : cities) {
            graph.addCity(city);
        }

        // Add edges between cities within the autonomy range
        for (int i = 0; i < cities.size(); i++) {
            for (int j = i + 1; j < cities.size(); j++) {
                CityBean city1 = cities.get(i);
                CityBean city2 = cities.get(j);

                double distance = DistanceUtils.calculateDistance(
                        city1.getLatitude(), city1.getLongitude(),
                        city2.getLatitude(), city2.getLongitude()
                );

                if (distance <= VEHICLE_AUTONOMY_KM) {
                    graph.addEdge(city1, city2);
                    graph.addEdge(city2, city1); // Assuming it's undirected
                }
            }
        }

        return graph;
    }
}

