package io.hari.demo.dao;

import io.hari.demo.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends BaseDao<User>{
    List<User> findAllByContestsId(Long contestId);
    List<User> findAllByUsername(String username);

}
