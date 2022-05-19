package com.revature.services;

import com.revature.models.ResetCode;
import com.revature.repositories.ResetRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ResetServiceImpl implements ResetService{
    @Autowired
    private ResetRepository resetRepository;



    @Override
    public ResetCode save(ResetCode resetCode) {
        return resetRepository.save(resetCode);
    }
}
