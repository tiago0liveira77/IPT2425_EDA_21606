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
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    static Map<CityBean, Double> distances;
    static GraphBean graph;

    public static void main(String[] args) {
        startApp();
    }
    
    public static void startApp(){
        //Se ja existe ficheiro de graph
        if (FileInOutUtils.fileInputGraph.exists()) {
            graph = FileInOutUtils.readGraphFile(INPUT_GRAPH_FILE_PATH);
            graph.displayGraph();
            if (logger.isDebugEnabled()){
                logger.debug("App Iniciada por ficheiro já existente");
            }
        } else if (FileInOutUtils.fileInputRaw.exists()) { //Se nao existe, le o de entrada e cria o grafo
            List<CityBean> cities = FileInOutUtils.readRawFile(INPUT_RAW_FILE_PATH);
            GraphBean graph = RouteService.buildGraph(cities);
            FileInOutUtils.saveGraphFile(graph, INPUT_GRAPH_FILE_PATH);
            if (logger.isDebugEnabled()){
                logger.debug("App Iniciada pela primeira vez. Criar e guardar grafo.");
            }
        } else {
            if (logger.isErrorEnabled()){
                logger.error("NÃO FORAM ENCONTRADOS FICHEIROS DE ENTRADA VÁLIDOS.");
            }
        }
    }

}
