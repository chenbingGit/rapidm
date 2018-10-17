package com.chenbing.rapidm.dao;


import com.eqianzhuang.efinancial.entity.UserStatusEntity;

public interface UserStatusDao {
    UserStatusEntity get(String openid);
}
