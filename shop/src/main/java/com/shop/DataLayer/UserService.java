package com.shop.DataLayer;

import com.shop.models.User;
import com.shop.models.UserFactory;
import com.shop.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public String registerUser(User user, boolean isManager) throws Exception{
        //start Registering
        logger.info("Registering user start: {}", user.getEmail());
        try {

            if (userRepository.findByEmail(user.getEmail()) != null) {
                logger.warn("User with this email already exists: {}", user.getEmail());
                return  "User with this email already exists: "+user.getEmail();
            }

            // Create new User object and set properties
            UserFactory userFactory = new UserFactory();
            User newuser = userFactory.createUser(isManager);
            newuser.setName(user.getName());
            newuser.setEmail(user.getEmail());
            newuser .setPassword(user.getPassword());

            // Save user
            userRepository.save(user);
            logger.info("User registered successfully: {}", newuser.getEmail());
        }
        catch (Exception e){
            throw new Exception(e);
        }

        return user.getName()+ " add successfully";
    }

    public int login(String email, String password) throws Exception {
        logger.info("login with email: {}", email);
    try {

        // Find the user by email
        User user = userRepository.findByEmail(email);

        // Validate user existence and password
        //if user not found
        if (user == null) {
            logger.info("Login failed for user with email: {} not found", email);
            throw new IllegalArgumentException("Invalid email, user not found");
        }
        //if there is user found and correct password
        else if (user.getPassword().equals(password)) {
            logger.info("Login successful for user with email: {}", email);
            //return token
            //here uts user ID
            return user.getUserID();

        }
        //if the password wrong
        else {
            logger.warn("Login failed wrong password for user with email: {}", email);
            throw new IllegalArgumentException("wrong password");
        }


    }catch (Exception e){
        throw new Exception(e);
    }
    }
}
