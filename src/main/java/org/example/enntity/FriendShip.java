package org.example.enntity;

import java.io.Serializable;

public class FriendShip implements Serializable {
    private static final long serialVersionUID = 1;

    private long id;
    private long userId;
    private long friendId;

    public FriendShip(){}

    public FriendShip(long userId, long friendId){
        this.userId = userId;
        this.friendId = friendId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getFriendId() {
        return friendId;
    }

    public void setFriendId(long friendId) {
        this.friendId = friendId;
    }

    @Override
    public String toString() {
        return "FriendShip{" +
                "id=" + id +
                ", userId=" + userId +
                ", friendId=" + friendId +
                '}';
    }
}
