package me.ilcb.redis;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;


/**
 * Created by Jasper on 2017/4/17.
 */
public class RedisKeyDemo {
    public static void main(String[] args) {
        //启动本地的redis
        Jedis jedis = new Jedis("localhost");
        System.out.println("connection to redis successfully!");

        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
        System.out.println(jedis.flushDB());
    }
}
