package org.example.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.enntity.User;
import org.example.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Repository
public class UserDao {

    private static Logger logger = LoggerFactory.getLogger(UserDao.class);

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private UserMapper userMapper;


    public boolean createTable(){
        try(SqlSession session = sqlSessionFactory.openSession(true)){
            long result = session.update("org.example.mapper.UserMapper.createTable");
            return result == 0;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return false;
    }

    public User get(long id){
//        try(SqlSession session = sqlSessionFactory.openSession(true)){
//            User user = session.selectOne("org.example.mapper.UserMapper.getById", id);
//            return user;
//        }catch (Exception e){
//            logger.error(e.getMessage());
//        }
//        return null;
        return userMapper.getById(id);
    }

    public List<User> getAll(){
//        try(SqlSession session = sqlSessionFactory.openSession(true)){
//            List<User> users = session.selectList("org.example.mapper.UserMapper.getAll");
//            return users;
//        }catch (Exception e){
//            logger.error(e.getMessage());
//        }
//        return null;
        return userMapper.getAll();
    }

    public long insert(String username){
//        try(SqlSession session = sqlSessionFactory.openSession(true)){
//            User user = new User();
//            user.setName(username);
//            session.insert("org.example.mapper.UserMapper.insert", user);
//            return  user.getId();
//        }catch (Exception e){
//            logger.error(e.getMessage());
//        }
//        return 0;
        User user = new User(username);
        userMapper.insert(user);
        return user.getId();
    }
}
