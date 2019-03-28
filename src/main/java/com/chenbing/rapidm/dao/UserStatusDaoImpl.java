package com.chenbing.rapidm.dao;

import com.eqianzhuang.insurance.entity.UserStatusEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

@Repository
public class UserStatusDaoImpl implements UserStatusDao {

    private static String redisKeyPrefix = "UserStatus_wxba7248accdeb1e56";

    @Autowired
    RedisConnectionFactory factory;

    @Override
    public UserStatusEntity get(String openid) {
        byte[] key = (redisKeyPrefix+openid).getBytes();
        byte[] bytes = factory.getConnection().get(key);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(bais);
            return  (UserStatusEntity) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
