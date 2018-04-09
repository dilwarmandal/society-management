package com.society.repositories;

import com.society.entities.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Dilwar on 04-05-2017.
 */
public interface UserRepository extends JpaRepository<AppUser, Integer> {
    AppUser findById(Integer userId);

    AppUser findByUserName(String userName);

    List<AppUser> findAllByOrderByIdAsc();

    AppUser findAppUserByEmail(String email);
}
