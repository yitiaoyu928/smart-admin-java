package net.lab1024.sa.admin.module.business.project.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.lab1024.sa.base.common.domain.PageParam;
import org.hibernate.validator.constraints.Length;

/**
 * 项目列表查询
 *
 * @Author 1024创新实验室
 * @Date 2026-02-04
 */
@Data
public class ProjectQueryForm extends PageParam {

    @Schema(description = "搜索词")
    @Length(max = 20, message = "搜索词最多20字符")
    private String keyword;

    @Schema(description = "项目类型，APP,WEB,Windows")
    private String type;

    @Schema(description = "是否禁用")
    private Boolean disabledFlag;

    @Schema(description = "删除标识", hidden = true)
    private Boolean deletedFlag;

}