package com.revature.services;

import com.revature.models.ResetPassword;

public interface ResetService {

    ResetPassword findById(String id);
    boolean compareTimestamp(String id, long timeStamp);


}
