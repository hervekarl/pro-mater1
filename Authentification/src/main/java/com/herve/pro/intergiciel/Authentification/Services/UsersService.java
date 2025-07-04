package com.herve.pro.intergiciel.Authentification.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.herve.pro.intergiciel.Authentification.DTO.LoginDto;
import com.herve.pro.intergiciel.Authentification.DTO.SignUpDto;
import com.herve.pro.intergiciel.Authentification.Exceptions.UsersErrorExceptions;
import com.herve.pro.intergiciel.Authentification.Modeles.Users;
import com.herve.pro.intergiciel.Authentification.Repository.UsersRepository;

import lombok.Data;

@Service
@Data
public class UsersService {
    @Autowired
    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public List<Users> findAll() {
        List<Users> users = (List<Users>) usersRepository.findAll();
        if (users.isEmpty()) {
            return null;
        }
        return users;
    }

    public Optional<?> findUserById(Integer id) {
        Optional<Users> user = usersRepository.findById(id);
        if (user.isPresent()) {
            return user;
            
        }
        return Optional.empty();
    }
    public Optional<?> findUserByEmail(String email) {
        Optional<Users> user = usersRepository.findByEmail(email);
        if (user.isPresent()) {
            return user;
        }
        return Optional.empty();
    }
    public Users create(SignUpDto user) {
        Users usersToCreate = new Users();
        usersToCreate.setLastname(user.getLastname());
        usersToCreate.setFirstname(user.getFirstname());
        usersToCreate.setUsername(user.getUsername());
        usersToCreate.setBirthdate(user.getBirthdate());
        usersToCreate.setEmail(user.getEmail());
        usersToCreate.setPassword(passwordEncoder.encode(user.getPassword()));
        usersToCreate.setPhone(user.getPhone());
        return usersRepository.save(usersToCreate);
    }

    // Methode pour l'authentification
    public Users authenticate(LoginDto loginDto) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

            return usersRepository.findByUsername(loginDto.getUsername()).orElseThrow(() -> 
                new UsersErrorExceptions("User not found with username: " + loginDto.getUsername()));
            
    }

    public Users updateUsers(Users user) {
        Optional<Users> existingUser = usersRepository.findById(user.getId());
        if (existingUser.isPresent()) {
            Users updatedUser = existingUser.get();
            updatedUser.setLastname(user.getLastname());
            updatedUser.setFirstname(user.getFirstname());
            updatedUser.setUsername(user.getUsername());
            updatedUser.setBirthdate(user.getBirthdate());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
            updatedUser.setPhone(user.getPhone());
            return usersRepository.save(updatedUser);
        }
        throw new UsersErrorExceptions("User not found with id: " + user.getId());
    }
    public void deleteById(Integer id) {
        Optional<Users> user = usersRepository.findById(id);
        if (user.isPresent()) {
             usersRepository.deleteById(id); 
        }
        throw new UsersErrorExceptions("User not found with id: " + id);
       
    }
    public Users findByUsername(String username) {
        Users user = usersRepository.findByUsername(username)
            .orElseThrow(() -> new UsersErrorExceptions("User not found with username: " + username));
        if (user == null) {
            throw new UsersErrorExceptions("User not found with username: " + username);
        }
        return usersRepository.findByUsername(username) 
            .orElseThrow(() -> new UsersErrorExceptions("User not found with username: " + username));
    }

}

