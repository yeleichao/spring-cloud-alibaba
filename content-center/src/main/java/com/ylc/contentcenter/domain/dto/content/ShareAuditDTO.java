package com.ylc.contentcenter.domain.dto.content;

import com.ylc.contentcenter.domain.enums.AuditStatusEnum;
import lombok.Data;

/**
 * @Description: TODO
 * @Date: 2019/10/5
 */
@Data
public class ShareAuditDTO {

    /**
     * 审核状态
     */
    private AuditStatusEnum auditStatusEnum;

    /**
     * 原因
     */
    private String reason;
























}
