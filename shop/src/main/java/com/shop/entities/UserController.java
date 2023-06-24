package com.shop.entities;

import com.shop.DataLayer.UserService;
import com.shop.ShopApplication;
import com.shop.models.Buyer;
import com.shop.models.Manager;
import com.shop.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(ShopApplication.class);
    @Autowired
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //register new Buyer
    @PostMapping("/registerBuyer")
    public String registerUser(@RequestBody Buyer user) {
        // Validate input and check if user already exists
        try {
            String answer = userService.registerUser(user,false);
            return answer;
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return "Registration failed, "+e.getMessage();
        }
    }
    //register new Manager API (give access just to the Manager)
    @PostMapping("/registerManager")
    public String registerUser(@RequestBody Manager user) {
        // Validate input and check if user already exists
        try {
            String answer = userService.registerUser(user,true);
            return answer;
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return "Registration failed, "+e.getMessage();
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
        try {
            logger.info("Login request received for user with email: {}", user.getEmail());

            // Call the login method of the UserService
            int token = userService.login(user.getEmail(), user.getPassword());

            logger.info("Login successful for user with email: {}", user.getEmail());
            //can to task if token get real token
            return ResponseEntity.ok(token);


        } catch (Exception e) {
            logger.error("Login failed: {}", e.getMessage());
            return sendError(e.getMessage());
        }
    }



    public ResponseEntity<String> sendError(String str) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(str);
    }
}
