package cn.com.demo.common.shiro;

import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.apache.shiro.util.Initializable;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by demo on 15/4/27.
 */
public class RedisCacheManager implements CacheManager, Destroyable, Initializable {
    private String host = "localhost";
    private int port = 6379;
    private String password = "";
    private int timeout = 0;
    private JedisPool pool;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new RedisCache<>(this.pool);
    }

    @Override
    public void destroy() throws Exception {
        pool.close();
    }

    @Override
    public void init() throws ShiroException {
        this.pool = new JedisPool(new JedisPoolConfig(), host, port, timeout, password);
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
