package cn.com.demo.common.shiro;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 * Created by demo on 15/4/27.
 */
public class RedisCache<K, V> implements Cache<K, V> {
    private JedisPool pool;
    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RedisCache(JedisPool pool){
        if (pool == null) {
            throw new IllegalArgumentException("Cache argument cannot be null.");
        }
        this.pool = pool;
    }

    @Override
    public V get(K k) throws CacheException {
        Jedis jedis = pool.getResource();
        try {
            String key = objectMapper.writeValueAsString(k);
            logger.info("Getting object from cache , the key is  " + key);
            String value = jedis.get(key);
            logger.info("Getting object from cache , the value is  " + value);

            V v = null;
            if(value != null){
                v = (V)objectMapper.readValue(value, SimpleAuthorizationInfo.class);
            }
            return v;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            pool.returnResourceObject(jedis);
            logger.info("return resource after get!");
        }
    }

    @Override
    public V put(K k, V v) throws CacheException {
        Jedis jedis = pool.getResource();
        try {
            String key = objectMapper.writeValueAsString(k);
            logger.info("put object to cache , the key is  " + key);
            String value = objectMapper.writeValueAsString(v);
            logger.info("put object to cache , the value is  " + value);
            jedis.set(key, value);
            return v;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }finally {
            pool.returnResourceObject(jedis);
            logger.info("return resource after put!");
        }
    }

    @Override
    public V remove(K k) throws CacheException {
        Jedis jedis = pool.getResource();
        logger.info("remove object from cache , the key is  " + k);
        try {
            String key = objectMapper.writeValueAsString(k);
            String value = jedis.get(key);
            jedis.del(key);
            return objectMapper.convertValue(value, new TypeReference<V>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }finally {
            pool.returnResourceObject(jedis);
            logger.info("return resource after remove!");
        }
    }

    @Override
    public void clear() throws CacheException {
        logger.info("clear cache" );
        pool.getResource().flushAll();
    }

    @Override
    public int size() {
        long size = pool.getResource().dbSize();
        return (int)size;
    }

    @Override
    public Set<K> keys() {
        return null;//TODO
    }

    @Override
    public Collection<V> values() {
        return null;//TODO
    }
}
