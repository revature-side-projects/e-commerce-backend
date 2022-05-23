package com.revature.services;

import com.revature.models.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserService userService;

    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public Optional<User> findByCredentials(String email, String password) {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (!optionalUser.isPresent()) return optionalUser;

        User user = optionalUser.get();
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), user.getSaltBytes(), 65536, 128);
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = f.generateSecret(spec).getEncoded();
            password = new String(hash, StandardCharsets.ISO_8859_1);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        if (password.equals(user.getPassword())) return optionalUser;

        return Optional.empty();
    }

    public User register(User user) {
        byte[] salt;
        String verifiedSaltString;
        boolean saltValid;
        do {
            saltValid = true;
            salt = SaltMaker();
            verifiedSaltString = new String(salt, StandardCharsets.ISO_8859_1);// ISO_8859 makes byte array reconstruct-able
            byte[] unSalt = verifiedSaltString.getBytes(StandardCharsets.ISO_8859_1);
            if (!Arrays.equals(salt, unSalt)) {
                saltValid = false;
            }
        } while (!saltValid);//works first time in tests (tested 500 mil+ times)

        try {
            KeySpec spec = new PBEKeySpec(user.getPassword().toCharArray(), salt, 65536, 128);
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = f.generateSecret(spec).getEncoded();
            user.setPassword(hash);
            user.setSalt(verifiedSaltString);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        return userService.save(user);
    }

    private byte[] SaltMaker() {
        byte[] randBytes = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(randBytes);
        return randBytes;
    }
  
    @Override
    //TODO replace userId with UUID if allowed
    public void forgotPassword(String email){

            //TODO POST to reset reset request table {uuid,timestamp,userId}, if we end up being allowed to implement it
            userService.sendEmail(email);

    }
    @Override
    public Optional<User> findByUserId(Integer id) {
        return userService.findById(id);
    }
}
