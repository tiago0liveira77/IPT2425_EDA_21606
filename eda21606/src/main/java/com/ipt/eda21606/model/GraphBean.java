/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipt.eda21606.model;

import java.io.Serializable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class GraphBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Map<CityBean, List<CityBean>> adjacencies = new ConcurrentHashMap<>();

    public void addCity(CityBean city) {
        adjacencies.putIfAbsent(city, new CopyOnWriteArrayList<>());
    }

    public void addEdge(CityBean source, CityBean destination) {
        // Adiciona a conexão de source para destination
        adjacencies.computeIfAbsent(source, key -> new CopyOnWriteArrayList<>())
                .add(destination);

        // Adiciona a conexão inversa de destination para source
        adjacencies.computeIfAbsent(destination, key -> new CopyOnWriteArrayList<>())
                .add(source);
    }

    public List<CityBean> getAdjacencies(CityBean city) {
        return adjacencies.getOrDefault(city, Collections.emptyList());
    }

    public void displayGraph() {
        if (adjacencies.isEmpty()) {
            System.out.println("O grafo está vazio.");
        } else {
            adjacencies.forEach((city, neighbors) -> {
            System.out.println(city.getName() + " -> "
                    + neighbors.stream().map(CityBean::getName).toList());
        });
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
