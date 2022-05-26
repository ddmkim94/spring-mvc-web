package hello.itemservice.http.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadInfo {

    private String username;
    private int age;
    private String fileName;
    private String extension;
    private long fileSize;
    private String contentType;

    public UploadInfo(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
