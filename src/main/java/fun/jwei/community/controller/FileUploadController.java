package fun.jwei.community.controller;

import fun.jwei.community.dto.UploadResultDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class FileUploadController {

    @Value("${uploadDir}")
    private String uploadDir;

    @ResponseBody
    @PostMapping("/imageUpload")
    public UploadResultDTO uploadImage(@RequestParam("editormd-image-file") MultipartFile file,
                                       @RequestParam("guid") String guid) {
        UploadResultDTO resultDTO = new UploadResultDTO();
        String originalFilename = file.getOriginalFilename();
        String dest = System.currentTimeMillis() + "_" + originalFilename;
        try {
            file.transferTo(new File(uploadDir + dest));
            resultDTO.setSuccess(1);
            resultDTO.setMessage("图片上传成功！");
            resultDTO.setUrl("/upload/" + dest);
        } catch (IOException e) {
            resultDTO.setSuccess(0);
            resultDTO.setMessage("图片上传失败！");
            e.printStackTrace();
        }
        return resultDTO;
    }

}
