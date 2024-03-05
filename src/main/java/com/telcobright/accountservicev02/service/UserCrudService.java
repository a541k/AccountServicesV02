package com.telcobright.accountservicev02.service;

import com.telcobright.accountservicev02.dto.ResellerRelationBody;
import com.telcobright.accountservicev02.dto.UserDto;
import com.telcobright.accountservicev02.entities.User;
import com.telcobright.accountservicev02.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserCrudService {

    @Autowired
    UserRepository userRepo;

    public ResponseEntity<String> createUser(User user) {
        try{
            //input validity check
            if(user.getUserName().isBlank() || user.getPassword().isEmpty()) throw new Exception("Required Field Left Empty");
            if(userRepo.findByEmail(user.getEmail())!=null) throw new Exception("An Account Already Exists With That Email");

            //accountRepo.save(account);
            userRepo.save(user);
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<UserDto> getUserById(int id) {
        try{

            User user = checkUserExistence(id); // returns user if exist else throw exception

            UserDto userDto = new UserDto();
            userDto.setUserName(user.getUserName());
            userDto.setEmail(user.getEmail());

            return new ResponseEntity<>(userDto, HttpStatus.FOUND);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(new UserDto(), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> addResellerRelation(Integer userId, ResellerRelationBody relation) {
        try{
            User user = checkUserExistence(userId); // returns user if exist else throw exception
            User parent = null;
            if(relation.getParentId()!=null)
                parent = checkUserExistence(relation.getParentId());

            //List<User> childrenList = new ArrayList<>();

            for(int childId : relation.getChildrenUserIdList()){
                User child = checkUserExistence(childId);
                child.setParent(user);
                user.getChildren().add(child);
            }
            user.setParent(parent);
            userRepo.save(user);

            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    // returns user if exist else throw exception
    private User checkUserExistence(Integer userId) throws Exception{
        Optional<User> optionalUser = userRepo.findById(userId);
        if(optionalUser.isEmpty()) throw new Exception("USER DOES NOT EXIST");
        return optionalUser.get();
    }


}
