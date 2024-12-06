/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipt.eda21606.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Data // Gera getters, setters, equals, hashCode e toString automaticamente
@NoArgsConstructor // Gera um construtor sem argumentos
@AllArgsConstructor // Gera um construtor com todos os atributos
@Builder
@EqualsAndHashCode
public class CityBean implements Serializable {

    private static final Logger logger = LogManager.getLogger(CityBean.class);
    
    private static final long serialVersionUID = 1L;
    private String name;           // city
    private String nameAscii;      // city_ascii
    private double latitude;       // lat
    private double longitude;      // lng
    private String pais;           // country
    private String iso2;           // iso2
    private String iso3;           // iso3
    private String adminName;      // admin_name
    private String capital;        // capital
    private String population;        // population
    private String id;               // id

    public static Optional<CityBean> findCityByName(String cityName, List<CityBean> cities) {
        if (cityName == null || cities == null) {
            throw new IllegalArgumentException("Nome da cidade ou lista de cidades não pode ser nulo.");
        }
        return cities.stream()
                .filter(city -> cityName.equalsIgnoreCase(city.getName()))
                .findFirst();
    }

    public static int findCityIndexByName(String cityName, List<CityBean> cities) {
        if (logger.isDebugEnabled()) {
            logger.debug("Searching city by: " + cityName);
        }
        if (cityName == null || cities == null) {
            throw new IllegalArgumentException("Nome da cidade ou lista de cidades não pode ser nulo.");
        }
        for (int i = 0; i < cities.size(); i++) {
            if (cityName.equalsIgnoreCase(cities.get(i).getName())) {
                return i; // Retorna o índice da cidade encontrada
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("City not found by: " + cityName);
        }
        return -1; // Retorna -1 se a cidade não for encontrada
    }
}
