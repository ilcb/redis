package me.ilcb.redis;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

import java.util.List;

/**
 * Created by Jasper on 2017/4/17.
 */
public class RedisSortDemo {
    private Jedis jedis = null;

    @Before
    public void prepare() {
        if (jedis == null) {
            jedis = new Jedis("localhost");
        }
    }

    /**
     * 时间复杂度：
     * O(N+M*log(M))，N 为要排序的列表或集合内的元素数量，M 为要返回的元素数量。
     * 如果只是使用sort命令的get选项获取数据而没有进行排序，时间复杂度 O(N)。
     */
    @Test
    public void sortList() {
        // 排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较
        // 一般sort用法,最简单的SORT使用方法是sort key。
        jedis.flushDB();
        jedis.lpush("list", "1");
        jedis.lpush("list", "4");
        jedis.lpush("list", "6");
        jedis.lpush("list", "3");
        jedis.lpush("list", "0");

        //List<String> list = jedis.sort("list");// 默认是升序
        SortingParams sortingParams = new SortingParams();
        sortingParams.desc();
        // sortingParameters.alpha();//当数据集中保存的是字符串值时，你可以用 ALPHA
        sortingParams.limit(0, 5);// 可用于分页查询

        List<String> list = jedis.sort("list", sortingParams); // 默认是升序
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        Assert.assertEquals("[6, 4, 3, 1, 0]", list.toString());
        jedis.flushDB();
    }

    /**
     * sort list
     * list结合hash的排序
     */
    @Test
    public void sortHash() {
        jedis.del("user:66", "user:55", "user:33", "user:22", "user:11", "userlist");
        jedis.lpush("userlist", "33");
        jedis.lpush("userlist", "22");
        jedis.lpush("userlist", "55");
        jedis.lpush("userlist", "11");

        jedis.hset("user:66", "name", "66");
        jedis.hset("user:55", "name", "55");
        jedis.hset("user:33", "name", "33");
        jedis.hset("user:22", "name", "79");
        jedis.hset("user:11", "name", "24");
        jedis.hset("user:11", "add", "beijing");
        jedis.hset("user:22", "add", "shanghai");
        jedis.hset("user:33", "add", "guangzhou");
        jedis.hset("user:55", "add", "chongqing");
        jedis.hset("user:66", "add", "xi'an");

        SortingParams sortingParameters = new SortingParams();
        // 符号 "->" 用于分割哈希表的键名(key name)和索引域(hash field)，格式为 "key->field" 。
        sortingParameters.get("user:*->name");
        sortingParameters.get("user:*->add");
        //sortingParameters.by("user:*->name");
        // sortingParameters.get("#");
        List<String> result = jedis.sort("userlist", sortingParameters);
        for (String item : result) {
            System.out.println("item...." + item);
        }
    }

    /**
     * sort set
     * set结合String的排序
     */
    @Test
    public void testSet() {
        jedis.del("tom:friend:list", "score:uid:123", "score:uid:456",
                "score:uid:789", "score:uid:101", "uid:123", "uid:456",
                "uid:789", "uid:101");

        jedis.sadd("tom:friend:list", "123"); // tom的好友列表
        jedis.sadd("tom:friend:list", "456");
        jedis.sadd("tom:friend:list", "789");
        jedis.sadd("tom:friend:list", "101");

        jedis.set("score:uid:123", "1000"); // 好友对应的成绩
        jedis.set("score:uid:456", "6000");
        jedis.set("score:uid:789", "100");
        jedis.set("score:uid:101", "5999");

        jedis.set("uid:123", "{'uid':123,'name':'lucy'}"); // 好友的详细信息
        jedis.set("uid:456", "{'uid':456,'name':'jack'}");
        jedis.set("uid:789", "{'uid':789,'name':'jay'}");
        jedis.set("uid:101", "{'uid':101,'name':'jolin'}");

        SortingParams sortingParameters = new SortingParams();

        sortingParameters.desc();
        // sortingParameters.limit(0, 2);
        // 注意GET操作是有序的，GET user_name_* GET user_password_*
        // 和 GET user_password_* GET user_name_*返回的结果位置不同
        sortingParameters.get("#");// GET 还有一个特殊的规则—— "GET #"
        // ，用于获取被排序对象(我们这里的例子是 user_id )的当前元素。
        sortingParameters.get("uid:*");
        sortingParameters.get("score:uid:*");
        sortingParameters.by("score:uid:*");
        // 对应的redis 命令是./redis-cli sort tom:friend:list by score:uid:* get # get
        // uid:* get score:uid:*
        List<String> result = jedis.sort("tom:friend:list", sortingParameters);
        for (String item : result) {
            System.out.println("item..." + item);
        }
    }
}
