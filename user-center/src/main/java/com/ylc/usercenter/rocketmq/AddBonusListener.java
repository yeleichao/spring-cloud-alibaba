package com.ylc.usercenter.rocketmq;

import com.ylc.usercenter.domain.dto.messaging.UserAndBonusMsgDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @Description: MQ消费者
 * @Date: 2019/10/5
 */
@Service
@Slf4j
@RocketMQMessageListener(consumerGroup = "consumer-groupo", topic = "add-bonus")
public class AddBonusListener implements RocketMQListener<UserAndBonusMsgDTO> {
    @Override
    public void onMessage(UserAndBonusMsgDTO message) {
        //当收到消息的时候，执行的业务
        log.info("收到信息---》{}",message.toString());
    }
}
