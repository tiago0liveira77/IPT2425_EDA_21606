/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipt.eda21606.util;

import com.ipt.eda21606.algorithm.DijkstraAlgorithm;
import com.ipt.eda21606.model.CityBean;
import static com.ipt.eda21606.util.Constants.EARTH_RADIUS_KM;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pcjoa
 */
public class DistanceUtils {

    private static final Logger logger = LogManager.getLogger(DistanceUtils.class);

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }

    public static CityBean getClosestCity(Map<CityBean, Double> distances) {
        CityBean closestCity = distances.entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 0 && entry.getValue() < Double.POSITIVE_INFINITY)
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
        return closestCity;
    }

    public static void printAllDistances(Map<CityBean, Double> distances) {
        distances.forEach((city, distance) -> {
            if (distance == Double.POSITIVE_INFINITY) {
                if (logger.isDebugEnabled()) {
                    logger.debug("| -> City " + city.getName() + " is unreachable.");
                }

            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("| ->Distance to " + city.getName() + ": " + distance + " km");
                }

            }
        });
        if (logger.isDebugEnabled()) {
            logger.debug("|-------------------------------------------------------------------------------------");
        }
    }

}
