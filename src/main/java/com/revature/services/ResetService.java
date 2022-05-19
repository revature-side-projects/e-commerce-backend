package com.revature.services;

import com.revature.models.ResetCode;
import com.revature.repositories.ResetRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface ResetService {




    ResetCode save (ResetCode resetCode);
}
