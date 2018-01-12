package cn.sibat.ebase.server;

import cn.sibat.ebase.server.dao.AuthorityDao;
import cn.sibat.ebase.server.dao.UserDao;
import cn.sibat.ebase.server.dao.UserStationDao;
import cn.sibat.ebase.server.dao.redis.JedisClient;
import cn.sibat.ebase.server.entity.Authority;
import cn.sibat.ebase.server.entity.User;
import cn.sibat.ebase.server.entity.UserStation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationTests.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private JedisClient jedisClient;
    @Autowired
    private AuthorityDao authorityDao;
    @Autowired
    private UserStationDao userStationDao;

    @Test
    public void jpaTest() {
        User user=new User("zsr","zsr@163.com","sr.zheng","kk");
        user.setAvatar("http://www.ai.com");

        List<Authority> list=authorityDao.findAll();
        user.setAuthorities(list);

        userDao.save(user);

        logger.info("save successful!");
    }

    @Test
    public void redisTest() {
        logger.info(jedisClient.get("kk"));
    }

    @Test
    public void authorityTest(){
        Authority authority=new Authority();
        authority.setId(1L);
        authority.setName("USER");

        authorityDao.save(authority);
    }

    @Test
    public void userStationTest(){
        UserStation userStation=new UserStation(1L,"kk");

        userStationDao.save(userStation);
    }

}
