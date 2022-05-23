package com.revature.services;

import com.revature.models.ResetPassword;
import com.revature.repositories.ResetPasswordRepository;

public class ResetServiceImpl implements ResetService {
    private final ResetPasswordRepository passwordRepository;

    public ResetServiceImpl(ResetPasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    @Override
    public ResetPassword findById(String id) {
        return passwordRepository.findbyId(id);
    }

    @Override
    public boolean compareTimestamp(String id, long timeStamp) {
       ResetPassword resetPassword = passwordRepository.findbyId(id);
       long day = 86400000;
        if (resetPassword != null)
            if (System.currentTimeMillis()- resetPassword.getTimeStamp() < day){
                return true;

             }
                else{
                        return false;
                }

        else{
            return false;
        }

    }


}
