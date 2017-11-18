package cn.codingxiaxw.dao;

import cn.codingxiaxw.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置spring和junit整合，这样junit在启动时就会加载spring容器
 * 因为如果(不配置)直接写方法的话，可能会需要SeckillDao的依赖？？？
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
    //Resource,->会在Spring容器中查找seckillDao的实现类（本项目使用了mapper代理的方式实现了DAO）
    //seckillDao提示有错，可能是idea本身配置问题，或者其他原因，实际没错，单元测试可以通过
    @Resource
    private SeckillDao seckillDao;
    @Test
    public void reduceNumber() throws Exception {
        /*
         * 19:24:20.224 [main] DEBUG org.mybatis.spring.transaction.SpringManagedTransaction -
         *       JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@51891008 [wrapping: com.mysql.jdbc.JDBC4Connection@2f953efd]]
         *       will not be managed by Spring
         * 这个JDBC连接从才c3p0数据库链接池直接拿到，没有被Spring托管
         * Spring声明式事务，会托管JDBC链接????
         * [main] DEBUG cn.codingxiaxw.dao.SeckillDao.reduceNumber - ==>
         *    Preparing: UPDATE seckill SET number = number-1 WHERE seckill_id=? AND start_time <= ? AND end_time >= ? AND number > 0;
         * [main] DEBUG cn.codingxiaxw.dao.SeckillDao.reduceNumber - ==>
         *   Parameters: 1000(Long), 2017-11-18 19:24:19.392(Timestamp), 2017-11-18 19:24:19.392(Timestamp)
         * 19:24:20.379 [main] DEBUG cn.codingxiaxw.dao.SeckillDao.reduceNumber - <==    Updates: 0
         * 19:24:20.380 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4722ef0c]
         */
        long seckillId=1000;
        Date date=new Date();
        int updateCount=seckillDao.reduceNumber(seckillId,date);
        System.out.println(updateCount);
    }

    @Test
    public void queryById() throws Exception {
        //long seckillId=1000;
        Seckill seckill=seckillDao.queryById(1000);
        System.out.println(seckill.getName());
        /*
         * 1000元秒杀iphone6
         */
    }

    @Test
    public void queryAll() throws Exception {
        /*
         *Caused by: org.apache.ibatis.binding.BindingException:
         * Parameter 'offset' not found. Available parameters are [arg1, arg0, param1, param2]
         * java没有保存形参的记录
         * queryAll(0,100)-queryAll(arg1,arg2)
         */
        List<Seckill> seckills=seckillDao.queryAll(0,100);
        for (Seckill seckill : seckills) {
            System.out.println(seckill.getName());
        }
        /*
         * 解决方法：mybatis提供了注解
         * 1000元秒杀iphone6
         * 800元秒杀ipad
         * 6600元秒杀mac book pro
         * 7000元秒杀iMac
         */
    }

}