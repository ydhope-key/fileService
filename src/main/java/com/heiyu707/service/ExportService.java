package com.heiyu707.service;

import com.heiyu707.entity.UserFile;

import java.util.List;

public interface ExportService {
    List<UserFile> findJSONByType(String type);
}
