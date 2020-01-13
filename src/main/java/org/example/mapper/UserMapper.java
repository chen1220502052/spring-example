package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.enntity.User;

import java.util.List;

//@Mapper
public interface UserMapper {

    @Update({"create table user (id bigint(20) NOT NULL AUTO_INCREMENT, " +
            "name varchar(255) NOT NULL, PRIMARY KEY (id))"})
    int createTable();

    @Select({"select * from user where id = #{id}"})
    User getById(long id);

    @Select({"select * from user"})
    List<User> getAll();

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert({"insert into user(name) values( #{name})"})
    long insert(User user);
}
