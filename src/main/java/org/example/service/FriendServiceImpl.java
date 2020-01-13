package org.example.service;

import org.example.dao.FriendShipDao;
import org.example.enntity.FriendShip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendServiceImpl implements FriendService {

    private static Logger logger = LoggerFactory.getLogger(FriendServiceImpl.class);

    @Autowired
    private FriendShipDao friendShipDao;

    @Transactional
    @Override
    public boolean addFriend(long fid1, long fid2) {
        friendShipDao.insert(new FriendShip(fid1, fid2));
//        try { // 测试事务是否生效
//            Thread.currentThread().sleep(1000 * 30);
//        } catch (InterruptedException e) {
//            logger.error(e.getMessage());
//        }
        friendShipDao.insert(new FriendShip(fid2, fid1));
        return true;
    }

    public FriendShip get(long id){
        return friendShipDao.get(id);
    }
}
