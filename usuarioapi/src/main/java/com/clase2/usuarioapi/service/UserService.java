package com.clase2.usuarioapi.service;

import com.clase2.usuarioapi.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final Map<String, User> users = new HashMap<>();

    public void saveUser(User user){
        users.put(user.getEmail(), user);
    }

    public List<Map<String, String>> listSummary(){
        List<Map<String, String>> summary = new ArrayList<>();

        for(User u:users.values()){
            Map<String, String> data = new HashMap<>();
            data.put("email", u.getEmail());
            data.put("name", u.getName());
            summary.add(data);
        }

        return summary;
    }

    public User getByEmail(String email){
        return users.get(email);
    }

    public boolean updateUser(String email, User newUser){
        if(users.containsKey(email)){
            newUser.setEmail(email);  //Para mantener email original
            users.put(email, newUser);
            return true;
        }

        return false;
    }

    public boolean deleteUser(String email){
        return users.remove(email) != null;
    }
}
