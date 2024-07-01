package org.example.libraryrest.service;


import org.example.libraryrest.entity.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private MyUserService myUserService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MyUser myUser = myUserService.getUserByEmail(email);
        if(myUser !=null){
            return User.builder().username(myUser.getEmail())
                    .password(myUser.getPassword())
                    .roles(getRoles(myUser))
                    .build();
        }
        else{
            throw new UsernameNotFoundException(email);
        }
    }
    private String[] getRoles(MyUser user){
        if(user.getRole()==null){
            return new String[]{"USER"};
        }
        return user.getRole().split(",");
    }
}

