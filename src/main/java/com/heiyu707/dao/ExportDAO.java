package com.heiyu707.dao;

import com.heiyu707.entity.User;
import com.heiyu707.entity.UserFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExportDAO {
    // 根据type查询所有的音频文件信息
    List<UserFile> findAudioByType();
}
