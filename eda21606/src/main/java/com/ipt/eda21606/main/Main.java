/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.ipt.eda21606.main;

import com.ipt.eda21606.algorithm.DijkstraAlgorithm;
import com.ipt.eda21606.model.CityBean;
import com.ipt.eda21606.model.GraphBean;
import com.ipt.eda21606.service.RouteService;
import com.ipt.eda21606.util.CSVUtils;
import static com.ipt.eda21606.util.Constants.*;
import static com.ipt.eda21606.util.DistanceUtils.*;
import com.ipt.eda21606.util.FileInOutUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    static DijkstraAlgorithm dijkstraAlgorithm;
    static Map<CityBean, Double> distances;
    static GraphBean graph;

    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        startApp();
    }

    public static void startApp() {
        //Se ja existe ficheiro de graph
        if (FileInOutUtils.fileInputGraph.exists()) {
            graph = FileInOutUtils.readGraphFile(INPUT_GRAPH_FILE_PATH);
            if (graph == null) {
                if (logger.isErrorEnabled()) {
                    logger.error("GRAFO INVÁLIDO");
                }
                return;
            }
            graph.displayGraph();
            if (logger.isDebugEnabled()) {
                logger.debug("App Iniciada por ficheiro já existente");
            }
        } else if (FileInOutUtils.fileInputRaw.exists()) { //Se nao existe, le o de entrada e cria o grafo
            List<CityBean> cities = FileInOutUtils.readRawFile(INPUT_RAW_FILE_PATH);
            GraphBean graph = RouteService.buildGraph(cities);
            FileInOutUtils.saveGraphFile(graph, INPUT_GRAPH_FILE_PATH);
            if (logger.isDebugEnabled()) {
                logger.debug("App Iniciada pela primeira vez. Criar e guardar grafo.");
            }
        } else {
            if (logger.isErrorEnabled()) {
                logger.error("NÃO FORAM ENCONTRADOS FICHEIROS DE ENTRADA VÁLIDOS.");
            }
            return;
        }
        dijkstraAlgorithm = new DijkstraAlgorithm(graph);
        makePathFromTo();
    }

    public static void makePathFromTo() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduza cidade de origem: ");
        String city1Name = scanner.nextLine();

        System.out.print("Introduza cidade de destino: ");
        String city2Name = scanner.nextLine();

        CityBean city1 = graph.findCityByName(city1Name).orElse(null);
        CityBean city2 = graph.findCityByName(city2Name).orElse(null);
        distances = dijkstraAlgorithm.findShortestPath(city1, city2);
    }

}
