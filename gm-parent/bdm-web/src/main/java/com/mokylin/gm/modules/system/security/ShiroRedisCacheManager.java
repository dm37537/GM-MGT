package com.mokylin.gm.modules.system.security;

import com.mokylin.gm.cached.ICached;
import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

public class ShiroRedisCacheManager extends AbstractCacheManager {
    private ICached cached;

    @Override
    protected Cache createCache(String cacheName) throws CacheException {
        return new ShiroRedisCache<String, Object>(cacheName, cached);
    }

    public ICached getCached() {
        return cached;
    }

    public void setCached(ICached cached) {
        this.cached = cached;
    }

}
