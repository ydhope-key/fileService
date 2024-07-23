package com.heiyu707.service;

import com.heiyu707.entity.UserFile;
import java.util.List;


public interface UserFileService {
    List<UserFile> findByUserId(Integer id);

    List<UserFile> findByUserIdAndType(Integer id, String type);

    void save(UserFile userFile);

    UserFile findById(Integer id);

    void update(UserFile userFile);

    void delete(Integer id);
}
