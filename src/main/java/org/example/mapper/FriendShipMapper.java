package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.enntity.FriendShip;
import org.example.enntity.User;

public interface FriendShipMapper {
    @Update({"create table friend_ship (id bigint(20) NOT NULL AUTO_INCREMENT, " +
            "userId bigint(20) NOT NULL, friendId bigint(20) NOT NULL, PRIMARY KEY (id))"})
    int createTable();

    @Select({"select * from friend_ship where id = #{id}"})
    FriendShip getById(long id);

    @Select({"select * from friend_ship where userId = #{userId}"})
    FriendShip getByUserId(long userId);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert({"insert into friend_ship(userId, friendId) values( #{userId}, #{friendId})"})
    long insert(FriendShip friendShip);
}
