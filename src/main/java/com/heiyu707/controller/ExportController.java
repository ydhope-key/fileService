package com.heiyu707.controller;

import com.heiyu707.entity.UserFile;
import com.heiyu707.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ExportController {

    @Autowired
    private ExportService exportService;

    /*
     * 查询所有音频文件信息
     * */
    @GetMapping("/findJSONByFileService")
    @ResponseBody
    public List<UserFile> findJSONByType(String type,HttpSession session) {

        // 根据type查询所有的音频文件信息
        List<UserFile> userFiles = exportService.findJSONByType(type);
        return userFiles;
    }
}
