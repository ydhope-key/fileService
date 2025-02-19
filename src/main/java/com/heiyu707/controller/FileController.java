package com.heiyu707.controller;

import com.heiyu707.entity.User;
import com.heiyu707.entity.UserFile;
import com.heiyu707.service.UserFileService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/file")
public class FileController {

    @Value("${normalFileRealPath}")
    private String normalFileRealPath;

    @Value("${musicFileRealPath}")
    private String musicFileRealPath;

    @Value("${videoFileRealPath}")
    private String videoFileRealPath;

    @Autowired
    private UserFileService userFileService;


    DecimalFormat df1 = new DecimalFormat("0.00");

    /*
     * 查询所有文件信息
     * */
    @GetMapping("/findAllJSON")
    @ResponseBody
    public List<UserFile> findAllJSON(HttpSession session, Model model) {
        // 在登录的session中获取用户的id
        User user = (User) session.getAttribute("user");
        // 根据用户id查询有的文件信息
        List<UserFile> userFiles = userFileService.findByUserId(user.getId());
        return userFiles;
    }



    /**
     * 删除文件
     */
    @GetMapping("/delete")
    public String delete(Integer id) throws FileNotFoundException {
        // 根据id查询信息
        UserFile userFile = userFileService.findById(id);
        // 删除服务器上的文件
        // 通过相对路径获取绝对路径
        String realPath = userFile.getPath();
        File file = new File(realPath + "/" + userFile.getNewFileName());
        if (file.exists()) file.delete(); // 删除服务器里的文件

        // 删除数据库中的文件
        userFileService.delete(id);
        return "redirect:/file/showAll";
    }

    /**
     * 下载文件
     */
    @GetMapping("/download")
    public void download(Integer id, String openStyle, HttpServletResponse response) throws IOException {
        openStyle = "inline".equals(openStyle) ? "inline" : "attachment";
        // 获取文件信息
        UserFile userFile = userFileService.findById(id);
        if ("attachment".equals(openStyle)){
            // 更新下载次数
            userFile.setDowncounts(userFile.getDowncounts() + 1); //下载次数+1
            userFileService.update(userFile);
        }
        // 根据 文件信息中文件名字 和 文件存储路径 获取文件真实路径
        String realPath = userFile.getPath();
        // 获取文件输入流
        FileInputStream is = new FileInputStream(new File(realPath, userFile.getNewFileName()));
        // 获取响应输出流
        response.setHeader("content-disposition", openStyle + ";fileName=" +
                URLEncoder.encode(userFile.getOldFileName(), "UTF-8"));
        ServletOutputStream os = response.getOutputStream();
        // 文件拷贝
        IOUtils.copy(is, os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
    }

    /**
     * 上传文件处理并保存文件信息到数据库中
     */
    @PostMapping("/upload")
    public String upload(MultipartFile aaa, HttpSession session) throws IOException {
        // 获取上传文件用户id
        User user = (User) session.getAttribute("user");
        // 获取文件原始名称
        String oldFileName = aaa.getOriginalFilename();
        // 获取文件后缀
        String extension = "." + FilenameUtils.getExtension(oldFileName);
        // 生成新的文件名称
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +
                UUID.randomUUID().toString().replace("-", "") + extension;
        // 文件大小
        Long size = aaa.getSize();
        // 文件类型
        String type = aaa.getContentType();
        // 根据日期生成文件目录
        // ResourceUtils.getURL("classpath:") 是获取当前文件的resources路径
        String realPath;
        if (type.startsWith("audio") ){
            realPath = musicFileRealPath;
        } else if(type.startsWith("video") ) {
            realPath = videoFileRealPath;
        } else{
            realPath = normalFileRealPath;
        }
        // 日期文件夹
//        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        String dateDirPath = realPath + "/" + date;
//        File dateDir = new File(dateDirPath);
        File realDir = new File(realPath);
        if (!realDir.exists()) realDir.mkdirs();
        // 处理文件上传
        aaa.transferTo(new File(realDir, newFileName));

        // 将文件信息放入数据库中
        UserFile userFile = new UserFile();
        userFile.setOldFileName(oldFileName).setNewFileName(newFileName).
                setExt(extension).setSize(String.valueOf(size)).setType(type).
                setPath(realPath).setUserId(user.getId());
        userFileService.save(userFile);

        return "redirect:/file/showAll";
    }

    /**
     * 上传文件处理并保存文件信息到数据库中
     */
    @PostMapping("/multipleUpload")
    public String multipleUpload(MultipartFile[] files, HttpSession session) throws IOException {
        // 获取上传文件用户id
        User user = (User) session.getAttribute("user");
        for(MultipartFile aaa : files){
            // 获取文件原始名称
            String oldFileName = aaa.getOriginalFilename();
            // 获取文件后缀
            String extension = "." + FilenameUtils.getExtension(oldFileName);
            // 生成新的文件名称
            String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +
                    UUID.randomUUID().toString().replace("-", "") + extension;
            // 文件大小
            Long size = aaa.getSize();
            // 文件类型
            String type = aaa.getContentType();
            // 根据日期生成文件目录
            // ResourceUtils.getURL("classpath:") 是获取当前文件的resources路径
            String realPath;
            if (type.startsWith("audio") ){
                realPath = musicFileRealPath;
            } else if(type.startsWith("video") ) {
                realPath = videoFileRealPath;
            } else{
                realPath = normalFileRealPath;
            }
            // 日期文件夹
//        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        String dateDirPath = realPath + "/" + date;
//        File dateDir = new File(dateDirPath);
            File realDir = new File(realPath);
            if (!realDir.exists()) realDir.mkdirs();
            // 处理文件上传
            aaa.transferTo(new File(realDir, newFileName));

            // 将文件信息放入数据库中
            UserFile userFile = new UserFile();
            userFile.setOldFileName(oldFileName).setNewFileName(newFileName).
                    setExt(extension).setSize(String.valueOf(size)).setType(type).
                    setPath(realPath).setUserId(user.getId());
            userFileService.save(userFile);
        }
        return "redirect:/file/showAll";
    }

    /**
     * 展示所有文件信息
     */
    @GetMapping("/showAll")
    public String showAll(HttpSession session, Model model) {
        // 在登录的session中获取用户的id
        User user = (User) session.getAttribute("user");
        // 根据用户id查询有的文件信息
        List<UserFile> userFiles = userFileService.findByUserId(user.getId());
        for (UserFile updateFile: userFiles){
            updateFile.setOldFileName(updateFile.getOldFileName().replace(updateFile.getExt(),""));
            updateFile.setSize(df1.format(Double.parseDouble(updateFile.getSize())/2024/2024)+"MB");
        }
        // 放入作用域中
        model.addAttribute("files", userFiles);
        return "showAll"; // 逻辑名
    }

    /*
     * 查询某一类型文件信息
     * */
    @GetMapping("/findJSONByType")
    public String findJSONByType(String type,HttpSession session,  Model model) {
        // 在登录的session中获取用户的id
        User user = (User) session.getAttribute("user");
        // 根据用户id查询有的文件信息
        List<UserFile> userFiles = userFileService.findByUserIdAndType(user.getId(),type);
        for (UserFile updateFile: userFiles){
            updateFile.setOldFileName(updateFile.getOldFileName().replace(updateFile.getExt(),""));
            updateFile.setSize(df1.format(Double.parseDouble(updateFile.getSize())/2024/2024)+"MB");
        }
        // 放入作用域中
        model.addAttribute("files", userFiles);
        if ("image".equals(type)) return "showImage";
        return "showAll"; // 逻辑名
    }

}

