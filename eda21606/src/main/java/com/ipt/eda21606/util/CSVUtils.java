/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipt.eda21606.util;

import com.ipt.eda21606.model.CityBean;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


public class CSVUtils {
    public static List<CityBean> readCities(String path) {
        List<CityBean> cities = new ArrayList<>();
        try (Reader reader = new FileReader(path)) {
            Iterable<CSVRecord> inputItems = CSVFormat.DEFAULT
                    .withHeader()
                    .withFirstRecordAsHeader()
                    .parse(reader);

            for (CSVRecord inputItem : inputItems) {
                String nome = inputItem.get("city");
                double latitude = Double.parseDouble(inputItem.get("lat"));
                double longitude = Double.parseDouble(inputItem.get("lng"));
                cities.add(
                            CityBean.builder()
                                    .name(nome)
                                    .latitude(latitude)
                                    .longitude(longitude)
                                    .build());
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        }
        return cities;
    }
}
