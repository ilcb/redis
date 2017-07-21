package me.ilcb.redis;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by Jasper on 2017/4/14.
 */
public class RedisListDemo {
    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        jedis.del("lkey");
        System.out.println(jedis.flushDB());
        System.out.println("Connection to server sucessfully");
        //存储数据到列表中
        jedis.lpush("lkey", "aaa");
        jedis.lpush("lkey", "bbb");
        jedis.lpush("lkey", "ccc");

        // 获取存储的数据并输出, -1表示获取所有值
        List<String> list = jedis.lrange("lkey", 0, -1);
        for(int i=0; i<list.size(); i++) {
            System.out.println("lkey存储值为: "+list.get(i));
        }

        // 列表长度
        System.out.println("list长度: " + jedis.llen("lkey"));

        // 排序
       // System.out.println("排序后的list: " + jedis.sort("lkey"));

        // 字串
        System.out.println("字串: " + jedis.lrange("lkey", 0, 3));

        // 修改列表中单个值
        jedis.lset("lkey", 0, "111");

        //获取指定下标的值
        System.out.println(jedis.lindex("lkey", 0));

        // 修改列表中单个值
        jedis.lset("lkey", 0, "hello list!");

        // 删除列表指定下标的值
        System.out.println(jedis.lrem("lkey", 0, "111"));

        // 删除区间以外的值
        System.out.println(jedis.ltrim("lkey", 0, 1));

        //列表出栈
        System.out.println(jedis.lpop("lkey"));

        // 整个列表值
        System.out.println(jedis.lrange("lkey", 0, -1));
    }
}
