package org.example.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.enntity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public boolean createTable(){
        try(SqlSession session = sqlSessionFactory.openSession(true)){
            long result = session.update("org.example.mapper.UserMapper.createTable");
            return result > 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public User get(long id){
        try(SqlSession session = sqlSessionFactory.openSession(true)){
            User user = session.selectOne("org.example.mapper.UserMapper.getById", id);
            return user;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAll(){
        try(SqlSession session = sqlSessionFactory.openSession(true)){
            List<User> users = session.selectList("org.example.mapper.UserMapper.getAll");
            return users;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public long insert(String username){
        try(SqlSession session = sqlSessionFactory.openSession(true)){
            User user = new User();
            user.setName(username);
            session.insert("org.example.mapper.UserMapper.insert", user);
            return  user.getId();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
