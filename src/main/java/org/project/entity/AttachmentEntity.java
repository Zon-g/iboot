package org.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "附件信息实体类", description = "附件信息实体类")
public class AttachmentEntity {

    @ApiModelProperty(value = "附件文件名", dataType = "String", example = "20100101010203.txt")
    private String filename;

    @ApiModelProperty(value = "附件文件格式", dataType = "String", example = "sql")
    private String format;

    @ApiModelProperty(value = "附件文件大小", dataType = "long", example = "1024")
    private long size;

    @ApiModelProperty(value = "附件类型", dataType = "String", example = "Avatar")
    private String type;

    @ApiModelProperty(value = "附件绝对路径", dataType = "String", example = "D:\\document\\text.txt")
    private String absolutePath;

    @ApiModelProperty(value = "附件上次修改时间", dataType = "Date", example = "2010-02-03 04:05:06")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastModify;

    /**
     * Accepts an instance of <code>Class File</code>, parses that instance and returns
     * an instance of current class.
     *
     * @param file specified instance of <code>File</code>
     * @return instance of current class
     */
    public static AttachmentEntity of(File file) {
        AttachmentEntity attachmentEntity = new AttachmentEntity();
        String[] strings = file.getName().split("\\.");
        attachmentEntity.setFilename(strings[0]);
        attachmentEntity.setFormat(strings[1]);
        attachmentEntity.setSize(file.length());
        attachmentEntity.setType(file.getParentFile().getName());
        attachmentEntity.setAbsolutePath(file.getAbsolutePath());
        attachmentEntity.setLastModify(new Date(file.lastModified()));
        return attachmentEntity;
    }

}
