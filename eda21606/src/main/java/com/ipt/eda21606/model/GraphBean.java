/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipt.eda21606.model;

import java.util.*;

public class GraphBean {
    private Map<CityBean, List<CityBean>> adjacencies = new HashMap<>();

    public void addCity(CityBean city) {
        adjacencies.putIfAbsent(city, new ArrayList<>());
    }

    public void addEdge(CityBean source, CityBean destination) {
        adjacencies.get(source).add(destination);
    }

    public List<CityBean> getAdjacencies(CityBean city) {
        return adjacencies.getOrDefault(city, Collections.emptyList());
    }

    public void displayGraph() {
        for (Map.Entry<CityBean, List<CityBean>> entry : adjacencies.entrySet()) {
            System.out.println(entry.getKey().getName() + " -> " +
                               entry.getValue().stream().map(CityBean::getName).toList());
        }
    }
}
