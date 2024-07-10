package com.heiyu707.dao;

import com.heiyu707.entity.UserFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserFileDAO {
    // 根据登录用户id获取用户的文件目录
    List<UserFile> findByUserId(Integer id);
    // 保存用户的文件记录
    void save(UserFile userFile);
    // 根据id找到文件信息
    UserFile findById(Integer id);
    // 更新下载次数
    void update(UserFile userFile);
    // 删除数据库中的文件
    void delete(Integer id);
}
