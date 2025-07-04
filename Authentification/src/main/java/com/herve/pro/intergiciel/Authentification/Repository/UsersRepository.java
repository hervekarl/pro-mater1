package com.herve.pro.intergiciel.Authentification.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.herve.pro.intergiciel.Authentification.Modeles.Users;

public interface UsersRepository extends CrudRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmail(String email);

    Optional<Users> findByPhone(String phone);
    

}
