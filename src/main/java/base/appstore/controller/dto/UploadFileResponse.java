package base.appstore.controller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class UploadFileResponse {
    private String fileName;
    private String fileType;
    private long size;

}
