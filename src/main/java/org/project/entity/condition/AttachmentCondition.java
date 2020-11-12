package org.project.entity.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "附件信息条件查询辅助实体类", description = "该实体类用于辅助查询附件信息")
public class AttachmentCondition {

    @ApiModelProperty(value = "附件类型", dataType = "String", example = "Avatar")
    private String type;

}
