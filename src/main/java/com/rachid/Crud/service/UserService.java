package com.rachid.Crud.service;

import com.rachid.Crud.model.Roles;
import com.rachid.Crud.model.Users;
import com.rachid.Crud.repos.RoleRepo;
import com.rachid.Crud.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(Users user) throws  Exception{
        if (checkIfUserExist(user.getUsername())){
            throw new Exception("user Alread existe");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        userRepo.save(user);
        addRoleUser(user.getUsername(), "USER");
    }
    public Boolean checkIfUserExist(String username){

        return userRepo.findByUsername(username)!=null ? true : false;
    }
    public void addRoleUser(String username,String roleName){
        Roles role = roleRepo.findByRoleName(roleName);
        Users user = userRepo.findByUsername(username);
        user.getRoles().add(role);
        userRepo.save(user);
    }

}
