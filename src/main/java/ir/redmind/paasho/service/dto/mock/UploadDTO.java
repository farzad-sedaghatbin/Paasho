package ir.redmind.paasho.service.dto.mock;

import org.springframework.web.multipart.MultipartFile;

public class UploadDTO {
    MultipartFile file;
    String code;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
