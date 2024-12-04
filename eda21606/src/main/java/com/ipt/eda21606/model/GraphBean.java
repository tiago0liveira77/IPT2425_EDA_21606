/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipt.eda21606.model;
import java.io.Serializable;

import java.util.*;

public class GraphBean implements Serializable{
    private static final long serialVersionUID = 1L;
    private Map<CityBean, List<CityBean>> adjacencies = new HashMap<>();

    public void addCity(CityBean city) {
        adjacencies.putIfAbsent(city, new ArrayList<>());
    }

    public void addEdge(CityBean source, CityBean destination) {
        // Adiciona a conexão de source para destination, garantindo que não seja duplicada
        adjacencies.putIfAbsent(source, new ArrayList<>());
        if (!adjacencies.get(source).contains(destination)) {
            adjacencies.get(source).add(destination);
        }

        // Adiciona a conexão inversa de destination para source, garantindo que não seja duplicada
        adjacencies.putIfAbsent(destination, new ArrayList<>());
        if (!adjacencies.get(destination).contains(source)) {
            adjacencies.get(destination).add(source);
        }
    }

    public List<CityBean> getAdjacencies(CityBean city) {
        return adjacencies.getOrDefault(city, Collections.emptyList());
    }

    public void displayGraph() {
        for (Map.Entry<CityBean, List<CityBean>> entry : adjacencies.entrySet()) {
            System.out.println(entry.getKey().getName() + " -> "
                    + entry.getValue().stream().map(CityBean::getName).toList());
        }
    }

    public Set<CityBean> getCities() {
        return adjacencies.keySet();
    }
    
    public Optional<CityBean> findCityByName(String name) {
        return adjacencies.keySet().stream()
                     .filter(city -> name.equalsIgnoreCase(city.getName()))
                     .findFirst();
    }
}
