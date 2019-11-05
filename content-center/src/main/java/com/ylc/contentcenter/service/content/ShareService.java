package com.ylc.contentcenter.service.content;

import com.ylc.contentcenter.dao.share.ShareMapper;
import com.ylc.contentcenter.domain.dto.content.ShareAuditDTO;
import com.ylc.contentcenter.domain.dto.content.ShareDTO;
import com.ylc.contentcenter.domain.dto.messaging.UserAndBonusMsgDTO;
import com.ylc.contentcenter.domain.dto.user.UserDTO;
import com.ylc.contentcenter.domain.entity.share.Share;
import com.ylc.contentcenter.feignclient.UserCenterFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @Description: TODO
 * @Date: 2019/9/15
 */
@Slf4j
@Service
public class ShareService {

    @Autowired
    private ShareMapper shareMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserCenterFeignClient userCenterFeignClient;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public ShareDTO findById(Integer id){
        //获取分享详情
        Share share = shareMapper.selectByPrimaryKey(id);
        //发布人id
        Integer userId = share.getUserId();

       /* // 用户中心所有实例的信息
        List<ServiceInstance> instances = discoveryClient.getInstances("user-center");

        String targetUrl = instances.stream()
                //数据变换
                .map(instance-> instance.getUri().toString()+ "/users/{id}")
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("当前没有实例"));

        log.info("请求的目标地址：{}",targetUrl);*/

        //"http://localhost:8080/users/{id}
        //这里使用了ribbon客户端负载均衡调用
       // ResponseEntity<UserDTO> responseEntity = restTemplate.getForEntity("http://user-center/users/{id}", UserDTO.class,userId);

        //使用feign调用
        UserDTO userDTO = userCenterFeignClient.findById(userId);


        ShareDTO shareDTO = new ShareDTO();
        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickName(userDTO.getWxNickname());
        return shareDTO;

    }

    public Share auditById(Integer id, ShareAuditDTO dto) {
        //1.查询share是否存在，不存在或者当前的audit_status不正确

        Share share = this.shareMapper.selectByPrimaryKey(id);
        if(share == null){
            throw new IllegalArgumentException("参数非法，该分享不存在");
        }
        if(!Objects.equals("NOT_YET", share.getAuditStatus())){
            throw new IllegalArgumentException("参数非法，该分享已审核通过或拒绝");
        }

        //2.审核资源
        //share.setAuditStatus(dto.getAuditStatusEnum().toString());
        //shareMapper.updateByPrimaryKey(share);

        //3.如果时pass，那么为发布人添加积分
        //向rocketmq中投递消息
        /*rocketMQTemplate.convertAndSend("add-bonus",
                UserAndBonusMsgDTO.builder()
                .userId(share.getUserId())
                .bonus(50)
                .build()
        );*/

        String uuid = UUID.randomUUID().toString();
        rocketMQTemplate.sendMessageInTransaction("tx-add-bonus-group","add-bonus",
                MessageBuilder.withPayload(
                        UserAndBonusMsgDTO.builder()
                            .userId(share.getUserId()).bonus(50).build()
                        )
                        .setHeader(RocketMQHeaders.TRANSACTION_ID, uuid)
                        .setHeader("share_id", id)
                        .build(),
                //有用处
                dto
        );



        return share;

    }

    //2.审核资源
    @Transactional(rollbackFor = Exception.class)
    public void auditByIdInDB(Integer id, ShareAuditDTO auditDTO) {
        Share share = Share.builder()
                .id(id)
                .auditStatus(auditDTO.getAuditStatusEnum().toString())
                .reason(auditDTO.getReason()).build();
        shareMapper.updateByPrimaryKeySelective(share);
    }
}























