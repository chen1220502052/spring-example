package org.example.cache;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryCache {

    private ConcurrentHashMap<String, Object> cache;
    private int size;
    private int maxCapacity;

    private static class Holder{
        private static final MemoryCache INSTANCE = new MemoryCache();
    }

    private MemoryCache(){
        cache = new ConcurrentHashMap<>();
        size = 0;
        maxCapacity = 16;
    }



    public Object get(String key){
        return cache.get(key);
    }

    public boolean set(String key, Object value){
        cache.put(key, value);
        return true;
    }

    public static MemoryCache getInstance(){
        return Holder.INSTANCE;
    }

}
