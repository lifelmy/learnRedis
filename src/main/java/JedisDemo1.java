

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * jedis的测试
 */
public class JedisDemo1 {


    /**
     * 单实例的测试
     */
    @Test
    public void demo1(){
        Jedis jedis=new Jedis("192.168.232.129",6379);
        jedis.set("test","test");
        String value=jedis.get("test");
        System.out.println(value );
        jedis.close();
    }

    /**
     * 使用连接池连接
     */
    @Test
    public void demo2(){
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        //设置最大连接数
        jedisPoolConfig.setMaxTotal(30);
        //设置最大空闲数
        jedisPoolConfig.setMaxIdle(10);
        JedisPool jedisPool=new JedisPool(jedisPoolConfig,"192.168.232.129");
        //核心对象
        Jedis jedis=null;
        try {
            //通过连接池获得连接
            jedis=jedisPool.getResource();
            jedis.set("name","lmy");
            String value=jedis.get("name");
            System.out.println(value);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //释放资源
            if(jedis!=null){
                jedis.close();
            }
            if(jedisPool!=null){
                jedisPool.close();
            }
        }





    }
}
