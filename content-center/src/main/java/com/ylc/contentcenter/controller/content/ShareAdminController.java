package com.ylc.contentcenter.controller.content;

import com.ylc.contentcenter.domain.dto.content.ShareAuditDTO;
import com.ylc.contentcenter.domain.entity.share.Share;
import com.ylc.contentcenter.service.content.ShareService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: TODO
 * @Date: 2019/10/5
 */
@RestController
@RequestMapping("/admin/shares")
public class ShareAdminController {

    @Autowired
    private ShareService shareService;



    @PutMapping("/audit/{id}")
    public Share auditById(@PathVariable Integer id, @RequestBody ShareAuditDTO dto){
        //TODO 认证、授权
        return shareService.auditById(id,dto);
    }

}












