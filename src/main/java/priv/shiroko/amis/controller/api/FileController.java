package priv.shiroko.amis.controller.api;

import com.fasterxml.uuid.Generators;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import priv.shiroko.amis.entity.Attachment;
import priv.shiroko.amis.mapper.AttachmentMapper;
import priv.shiroko.amis.utils.ApiResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@RestController
@RequestMapping("/api/file")
@Slf4j
public class FileController {
    @Value("${amis.upload-folder}")
    private String uploadFolder;

    @Resource
    AttachmentMapper attachmentMapper;

    @PostMapping("/upload")
    public ApiResult upload(@RequestParam("file") MultipartFile file) {
        ApiResult result = new ApiResult();
        if (file == null) {
            result.setStatus(ApiResult.Status.FAILED);
            result.setMessage("文件参数为空。");
            result.setReason("null file");
            return result;
        }
        UUID uuid = Generators.timeBasedGenerator().generate();
        File destfile = new File(uploadFolder + File.separator + uuid.toString());
        if (!destfile.getParentFile().exists()) {
            log.info("Creating path " + destfile.getParentFile().getPath());
            destfile.getParentFile().mkdirs();
        }
        try {
            Attachment attachment = new Attachment(uuid, file.getOriginalFilename());
            System.out.println(attachment);
            attachmentMapper.createNewFile(attachment);
            Files.copy(file.getInputStream(), destfile.toPath());
            result.setStatus(ApiResult.Status.OK);
            result.setData(uuid.toString());
        } catch (IOException e) {
            log.error("Error while upload file: " + e.toString());
            result.setStatus(ApiResult.Status.FAILED);
            result.setMessage("上传文件失败！");
            result.setReason("Upload failed");
        }
        return result;
    }

    @GetMapping("/download")
    public String download(HttpServletResponse response, @RequestParam("uuid") UUID uuid) {
        ApiResult result = new ApiResult();
        String filename = attachmentMapper.getFilename(uuid.toString());
        File file = new File(uploadFolder + File.separator + uuid.toString());
        if (filename == null || !file.isFile()) {
            return "文件不存在！";
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), StandardCharsets.ISO_8859_1));
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException ignore) {
            return "下载失败！";
        }
        return null;
    }

    @GetMapping("/filename")
    public ApiResult filename(@RequestParam("uuid") UUID uuid) {
        ApiResult result = new ApiResult();
        String filename = attachmentMapper.getFilename(uuid.toString());
        if (filename == null) {
            result.setMessage("文件不存在。");
            result.setStatus(ApiResult.Status.FAILED);
        } else {
            result.setStatus(ApiResult.Status.OK);
            result.setData(filename);
        }
        return result;
    }
}
