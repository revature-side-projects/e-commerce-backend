package com.revature.ecommerce.controllers;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< Updated upstream
=======
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
>>>>>>> Stashed changes
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.ecommerce.models.User;
import com.revature.ecommerce.services.ProfileService;




<<<<<<< Updated upstream
=======


>>>>>>> Stashed changes
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;
<<<<<<< Updated upstream

    private HttpSession session;

    @GetMapping("/{id}")
    public Optional<User> getProfileById(@PathVariable int id){
    	session.getAttribute("user");
        return profileService.getProfileById(id);
=======
    HttpSession session;

   

    @GetMapping("/myprofile")
    public ResponseEntity<User> getProfileByEmail(@RequestBody String email){
    	
    	Optional <User> userData = profileService.getProfileByEmail(email);
    	session.getAttribute("user");
    	if (userData.isPresent()) {
			return new ResponseEntity<>(userData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
      

    }

    @PutMapping("/update")
    public User editProfile(@RequestBody User user) {
        return profileService.editProfile(user);
    }
    

}



