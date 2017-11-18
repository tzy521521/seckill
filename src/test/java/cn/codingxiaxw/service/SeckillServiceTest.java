package cn.codingxiaxw.service;

import cn.codingxiaxw.dto.Exposer;
import cn.codingxiaxw.dto.SeckillExecution;
import cn.codingxiaxw.entity.Seckill;
import cn.codingxiaxw.exception.RepeatKillException;
import cn.codingxiaxw.exception.SeckillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml",
                        "classpath:spring/spring-service.xml"})

public class SeckillServiceTest {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());


    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list=seckillService.getSeckillList();
        //System.out.println(seckills);
        logger.info("list={}",list);
        /*
         * [main] DEBUG org.mybatis.spring.SqlSessionUtils - Closing non transactional SqlSession
         * 不是事务空控制下，只读操作。
         */
    }

    @Test
    public void getById() throws Exception {
        long seckillId=1000;
        Seckill seckill=seckillService.getById(seckillId);
        //System.out.println(seckill);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void testExportSeckillUrl() throws Exception{
        long id=1000;
        Exposer exposer=seckillService.exportSeckillUrl(id);
        logger.info("exposer={}",exposer);
        /*
         *[main] INFO  c.c.service.SeckillServiceTest -
         * exposer=Exposer{
         * exposed=true, md5='bf204e2683e7452aa7db1a50b5713bae',
         * seckillId=1000, now=0, start=0, end=0
         * }
         */
    }

    @Test
    public void testExecuteSeckill() throws Exception {
        long seckillId=1000;
        long phone=13567808686L;
        String md5="bf204e2683e7452aa7db1a50b5713bae";
        SeckillExecution execution=seckillService.executeSeckill(seckillId,phone,md5);
        logger.info("execution={}",execution);
        //执行2次 重复秒杀，成功，最好用try-catch,而不是抛给Test
        //cn.codingxiaxw.exception.RepeatKillException: seckill repeated
        //首次执行时
        /*
23:49:22.756 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Creating a new SqlSession
23:49:22.766 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7b205dbd]
23:49:22.776 [main] DEBUG o.m.s.t.SpringManagedTransaction - JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@5f354bcf [wrapping: com.mysql.jdbc.JDBC4Connection@3daf7722]] will be managed by Spring
23:49:22.783 [main] DEBUG c.c.d.S.insertSuccessKilled - ==>  Preparing: INSERT ignore INTO success_killed(seckill_id,user_phone,state) VALUES (?,?,0)
23:49:22.823 [main] DEBUG c.c.d.S.insertSuccessKilled - ==> Parameters: 1000(Long), 13567808686(Long)
23:49:22.825 [main] DEBUG c.c.d.S.insertSuccessKilled - <==    Updates: 1
23:49:22.835 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7b205dbd]
23:49:22.835 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7b205dbd] from current transaction
23:49:22.836 [main] DEBUG c.c.dao.SeckillDao.reduceNumber - ==>  Preparing: UPDATE seckill SET number = number-1 WHERE seckill_id=? AND start_time <= ? AND end_time >= ? AND number > 0;
23:49:22.839 [main] DEBUG c.c.dao.SeckillDao.reduceNumber - ==> Parameters: 1000(Long), 2017-11-18 23:49:22.701(Timestamp), 2017-11-18 23:49:22.701(Timestamp)
23:49:22.841 [main] DEBUG c.c.dao.SeckillDao.reduceNumber - <==    Updates: 1
23:49:22.842 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7b205dbd]
23:49:22.842 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7b205dbd] from current transaction
23:49:22.845 [main] DEBUG c.c.d.S.queryByIdWithSeckill - ==>  Preparing: SELECT sk.seckill_id, sk.user_phone, sk.create_time, sk.state, s.seckill_id "seckill.seckill_id", s.name "seckill.name", s.number "seckill.number", s.start_time "seckill.start_time", s.end_time "seckill.end_time", s.create_time "seckill.create_time" FROM success_killed sk INNER JOIN seckill s ON sk.seckill_id=s.seckill_id WHERE sk.seckill_id=? and sk.user_phone=?
23:49:22.846 [main] DEBUG c.c.d.S.queryByIdWithSeckill - ==> Parameters: 1000(Long), 13567808686(Long)
23:49:22.884 [main] DEBUG c.c.d.S.queryByIdWithSeckill - <==      Total: 1
23:49:22.891 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7b205dbd]
23:49:22.893 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Transaction synchronization committing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7b205dbd]
23:49:22.893 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7b205dbd]
23:49:22.894 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7b205dbd]
23:49:22.902 [main] INFO  c.c.service.SeckillServiceTest - execution=SeckillExecution{seckillId=1000, state=0, stateInfo='null', successKilled=SuccessKilled{seckillId=1000, userPhone=13567808686, state=0, createTime=Sat Nov 18 23:49:22 CST 2017}}
         */
    }

    @Test//完整逻辑代码测试，注意可重复执行，测试的是可开启秒杀的的商品，和没有开启秒杀的商品
    public void testSeckillLogic() throws Exception {
        /**
         * 没有开启秒杀的商品会输出 md5='null'
         * exposed=false, md5='null', seckillId=1001, now=1511020550399, start=1511020800000, end=1511020920000
         * 秒杀开启的商品 测试结果类似 testExecuteSeckill()
         */
        long seckillId=1001;
        Exposer exposer=seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed())
        {

            System.out.println(exposer);

            long userPhone=13476191876L;
            String md5=exposer.getMd5();

            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
                System.out.println(seckillExecution);
            }catch (RepeatKillException e)
            {
                e.printStackTrace();
            }catch (SeckillCloseException e1)
            {
                e1.printStackTrace();
            }
        }else {
            //秒杀未开启
            System.out.println(exposer);
        }
    }
}