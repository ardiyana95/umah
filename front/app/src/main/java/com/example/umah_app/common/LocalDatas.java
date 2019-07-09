package com.example.umah_app.common;

import android.annotation.SuppressLint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalDatas {

    private static Integer currentUser = 1;
    private static Integer userLeft = 1;
    @SuppressLint("UseSparseArrays")
    private static Map<Integer, Map<String, String>> mapFilter = new HashMap<>();
    @SuppressLint("UseSparseArrays")
    private static Map<Integer, Map<String, String>> mapSorting = new HashMap<>();

    public static Integer getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Integer currentUser) {
        LocalDatas.currentUser = currentUser;
    }

    public static Integer getUserLeft() {
        return userLeft;
    }

    public static void setUserLeft(Integer userLeft) {
        LocalDatas.userLeft = userLeft;
    }

    public static Map<Integer, Map<String, String>> getMapFilter() {
        return mapFilter;
    }

    public static void addMapFilter(Integer key, Map<String, String> map) {
        mapFilter.put(key, map);
    }

    public static Map<Integer, Map<String, String>> getMapSorting() {
        return mapSorting;
    }

    public static void addMapSorting(Integer key, Map<String, String> map) {
        mapSorting.put(key, map);
    }


    public static void setMapFilter(Map<Integer, Map<String, String>> mapFilter) {
        LocalDatas.mapFilter = mapFilter;
    }

    public static void setMapSorting(Map<Integer, Map<String, String>> mapSorting) {
        LocalDatas.mapSorting = mapSorting;
    }
}
