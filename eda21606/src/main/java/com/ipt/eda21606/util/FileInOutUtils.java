/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipt.eda21606.util;

import com.ipt.eda21606.model.CityBean;
import com.ipt.eda21606.model.GraphBean;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.ipt.eda21606.util.Constants.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileInOutUtils {

    private static final Logger logger = LogManager.getLogger(FileInOutUtils.class);

    public static List<CityBean> readRawFile(String path) {
        if (logger.isDebugEnabled()) {
            logger.debug("READING RAW FILE");
        }
        List<CityBean> cities = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Remove as aspas e separa os valores por vírgulas
                String[] fields = line.replace("\"", "").split(",");
                //ignorar primeiro registo em caso de ser registo indicativo de campos
                if ("city".equals(fields[0])) {
                    continue;
                }

                System.out.println(line);

                // Constrói o objeto CityBean com os valores relevantes
                cities.add(CityBean.builder()
                        .name(fields[0]) // name
                        .nameAscii(fields[1]) // nameAscii
                        .latitude(Double.parseDouble(fields[2])) // latitude
                        .longitude(Double.parseDouble(fields[3])) // longitude
                        .pais(fields[4]) // pais (country)
                        .iso2(fields[5]) // iso2
                        .iso3(fields[6]) // iso3
                        .adminName(fields[7]) // adminName
                        .capital(fields[8]) // capital
                        .population(fields[9]) // population
                        .id(fields[10]) // id
                        .build());
            }
            return cities;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GraphBean readGraphFile(String filePath) {
        if (logger.isDebugEnabled()) {
            logger.debug("READING GRAPH FILE");
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            GraphBean graph = (GraphBean) ois.readObject();
            System.out.println("GraphBean lido com sucesso de: " + filePath);
            return graph;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveGraphFile(GraphBean graph, String path) {
        if (logger.isDebugEnabled()) {
            logger.debug("SAVING RAW FILE");
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(graph);
            System.out.println("GraphBean salvo com sucesso em: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
