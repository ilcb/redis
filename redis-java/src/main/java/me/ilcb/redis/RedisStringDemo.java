package me.ilcb.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by Jasper on 2017/4/14.
 */
public class RedisStringDemo {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        System.out.println("Connect to server successfully!");

        System.out.println("=============String==========================");
        // 清空数据
        System.out.println(jedis.flushDB());
        jedis.set("key", "aaa");
        System.out.println("存储key: " + jedis.get("key"));

        // 若key不存在，则存储
        jedis.setnx("foo", "foo not exists");
        System.out.println("如果foo不存在就存储foo: " + jedis.get("foo"));

        // 覆盖数据
        jedis.set("key", "bbb");
        System.out.println("覆盖key后的值: " + jedis.get("key"));

        //添加数据
        jedis.append("foo", ", append value");
        System.out.println("追加foo的值: " + jedis.get("foo"));

        //设置key的有效期，并存储数据
        jedis.setex("foo", 2, "foo not exists");
        System.out.println("设置foo的有效期，存储数据: " + jedis.get("foo"));

        try{
            Thread.sleep(3000);
        } catch (InterruptedException e) {

        }
        System.out.println(jedis.get("foo"));

        //获取病更改数据
        jedis.set("foo", "foo update");
        System.out.println("更新foo的值，返回之前的值: " + jedis.getSet("foo", "foo modify"));

        //截取value的值
        System.out.println("截取foo的值: " + jedis.getrange("foo", 1, 3));

        System.out.println("设置key-value对: " + jedis.mset("key1", "value1", "key2", "value2", "key3", "value3"));
        System.out.println(jedis.mget("key1", "key2", "key3"));
        System.out.println(jedis.del(new String[] { "foo", "foo1", "foo3" }));
    }
}
