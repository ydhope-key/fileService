package com.heiyu707.service;

import com.heiyu707.entity.UserFile;
import com.heiyu707.dao.ExportDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExportServiceImpl implements ExportService{

    @Autowired
    private ExportDAO exportDAO;

    @Override
    public List<UserFile> findJSONByType(String type) {
        return exportDAO.findJSONByType(type);
    }
}
