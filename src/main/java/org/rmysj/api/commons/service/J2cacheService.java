package org.rmysj.api.commons.service;

public interface J2cacheService {
    public String addCache(String key,String value);

    public String queryCache(String type);

    public String getKey(String key);

    public String deleteKey(String key);
}
