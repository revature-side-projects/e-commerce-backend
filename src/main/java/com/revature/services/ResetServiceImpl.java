package com.revature.services;

import com.revature.models.ResetRequest;
import com.revature.models.User;
import com.revature.repositories.ResetRequestRepository;
import com.revature.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResetServiceImpl implements ResetService {

    @Autowired
    private ResetRequestRepository resetRequestRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResetRequest findById(int id) {
        System.out.println("running");
        Optional<ResetRequest> rp = resetRequestRepository.findById(id);
        return rp.orElse(null);
    }

    public ResetRequest createEntry(){
        ResetRequest rr = new ResetRequest();
        rr.setTimeStamp(System.currentTimeMillis());
        return resetRequestRepository.save(rr);
    }

    @Override
    public boolean compareTimestamp(long timeStamp) {
        long day = 86400000;
        return System.currentTimeMillis() - timeStamp < day;
    }

    @Override
    public User reset(String password, ResetRequest resetRequest){
        User user = new User();
        user.setPassword(password);
        user.setId(resetRequest.getUserId());
        return userRepository.save(user);
    }
}

