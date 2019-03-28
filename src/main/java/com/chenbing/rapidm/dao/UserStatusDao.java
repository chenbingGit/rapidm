package com.chenbing.rapidm.dao;


import com.eqianzhuang.insurance.entity.UserStatusEntity;

public interface UserStatusDao {
    UserStatusEntity get(String openid);
}
