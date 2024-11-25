/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipt.eda21606.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Data // Gera getters, setters, equals, hashCode e toString automaticamente
@NoArgsConstructor // Gera um construtor sem argumentos
@AllArgsConstructor // Gera um construtor com todos os atributos
@Builder
@EqualsAndHashCode
public class CityBean {
    private String name;           // city
    //private String nameAscii;      // city_ascii
    private double latitude;       // lat
    private double longitude;      // lng
    //private String pais;           // country
    //private String iso2;           // iso2
    //private String iso3;           // iso3
    //private String adminName;      // admin_name
    //private String capital;        // capital
    //private long population;        // population
    //private long id;               // id
    
    
}
