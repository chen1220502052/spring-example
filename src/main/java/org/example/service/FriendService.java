package org.example.service;

import org.example.enntity.FriendShip;

public interface FriendService {
    boolean addFriend(long fid1, long fid2);

    FriendShip get(long id);
}
