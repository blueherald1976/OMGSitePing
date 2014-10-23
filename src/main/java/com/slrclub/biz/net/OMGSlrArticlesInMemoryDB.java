package com.slrclub.biz.net;

import java.util.HashMap;

public class OMGSlrArticlesInMemoryDB {
    private static final int HASH_MAX_SIZE = 1500;
	private static HashMap<String, String> keyHashMap = new HashMap<String, String>();

    public static boolean containsKey(int key) {
    	return containsKey(Integer.toString(key));
    }
    
    public static boolean containsKey(String key) {
        return keyHashMap.containsKey(key);
    }
    
    public static void putKey(int key) {
    	putKey(Integer.toString(key));
    }
    
    public static void putKey(String key) {
 	   // return keyHashMap.put(key, value);
 	   if(keyHashMap.size() > HASH_MAX_SIZE) {
 		   keyHashMap.clear();
 		   keyHashMap = null;
 		   keyHashMap = new HashMap<String, String>();
 	   }
 	   
 	   keyHashMap.put(key, "Y");
    }
}
