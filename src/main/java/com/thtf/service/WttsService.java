package com.thtf.service;

import com.thtf.bean.WttsEntity;
import java.util.List;

public interface WttsService {
//    Users save(Users u);
//    List<Users> saveAll(List list);
//    
    
    WttsEntity save(WttsEntity WttsEntity);
    
    List<WttsEntity> saveAll(List list);
    
    
    List<WttsEntity> findAll();
    
    
    
}
