package org.example.dao;

import org.example.enntity.FriendShip;
import org.example.mapper.FriendShipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FriendShipDao {

    @Autowired
    private FriendShipMapper friendShipMapper;

    public boolean createTable(){
        return friendShipMapper.createTable() == 0;
    }

    public FriendShip get(long id){
        return friendShipMapper.getById(id);
    }

    public FriendShip getByUserId(long userId){
        return friendShipMapper.getByUserId(userId);
    }

    public long insert(FriendShip friendShip){
        friendShipMapper.insert(friendShip);
        return friendShip.getId();
    }
}
