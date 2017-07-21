package me.ilcb.redis;

import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created by Jasper on 2017/4/17.
 */
public class RedisHashDemo {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "James");
        map.put("age", "20");
        map.put("sex", "female");
        jedis.hmset("people", map);

        List<String> name = jedis.hmget("people", "name");

        System.out.println(jedis.hmget("people", "age"));

        System.out.println(jedis.hlen("people"));

        System.out.println(jedis.exists("people"));

        System.out.println(jedis.hkeys("people"));

        System.out.println(jedis.hvals("people"));

        Iterator<String> iter = jedis.hkeys("people").iterator();
        while(iter.hasNext()) {
            String key = iter.next();
            System.out.println(key + ": " + jedis.hmget("people", key));
        }


        List<String> values = jedis.hmget("people", new String[]{"name", "age", "sex"});
        System.out.println(values);

        // 清空数据
        System.out.println(jedis.flushDB());
        // 添加数据
        jedis.hset("hashs", "entryKey", "entryValue");
        jedis.hset("hashs", "entryKey1", "entryValue1");
        jedis.hset("hashs", "entryKey2", "entryValue2");

        // 判断某个值是否存在
        System.out.println(jedis.hexists("hashs", "entryKey"));

        // 获取指定的值
        System.out.println(jedis.hget("hashs", "entryKey"));

        System.out.println(jedis.hmget("hashs", "entryKey", "entryKey1"));

        // 删除指定的值
        System.out.println(jedis.hdel("hashs", "entryKey"));

        // 为key中的域 field 的值加上增量 increment
        System.out.println(jedis.hincrBy("hashs", "entryKey", 123l));
        // 获取所有的keys
        System.out.println(jedis.hkeys("hashs"));
        // 获取所有的values
        System.out.println(jedis.hvals("hashs"));

    }
}
