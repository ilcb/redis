package me.ilcb.redis;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * Created by Jasper on 2017/4/17.
 */
public class RedisSetDemo {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1");
        jedis.sadd("skey", "1");
        jedis.sadd("skey", "2");
        jedis.sadd("skey", "3");
        jedis.sadd("skey", "4");

        Set<String> set = jedis.smembers("skey");
        System.out.println(set);

        // 移除
        jedis.srem("skey", "3");
        System.out.println(jedis.smembers("skey"));

        // 是否是skey集合的元素
        System.out.println(jedis.sismember("skey", "4"));

        // 返回集合元素的个数
        System.out.println(jedis.scard("skey"));

        // 清空数据
        System.out.println(jedis.flushDB());

        // 添加数据
        jedis.sadd("skey", "HashSet");
        jedis.sadd("skey", "SortedSet");
        jedis.sadd("skey", "TreeSet");

        //判断value是否在set中
        System.out.println(jedis.sismember("skey", "TreeSet"));

        // 出栈
        System.out.println(jedis.spop("skey"));
        System.out.println(jedis.smembers("skey"));

        jedis.sadd("skey1", "aaa");
        jedis.sadd("skey1", "bbb");
        jedis.sadd("skey1", "ccc");
        jedis.sadd("skey1", "ddd");

        //交集
        System.out.println(jedis.sinter("skey", "skey1"));

        // 并集
        System.out.println(jedis.sunion("skey", "skey1"));

        // 差集
        System.out.println(jedis.sdiff("skey", "skey1"));
    }
}
