package com.ylc.contentcenter.rocketmq;

import com.ylc.contentcenter.domain.dto.content.ShareAuditDTO;
import com.ylc.contentcenter.service.content.ShareService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

/**
 * @Description: 事务rocketmq执行
 * @Date: 2019/10/5
 */
@RocketMQTransactionListener(txProducerGroup = "tx-add-bonus-group")
@Slf4j
public class AddBonusTransactionListener implements RocketMQLocalTransactionListener {
    @Autowired
    private ShareService shareService;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        MessageHeaders headers = msg.getHeaders();
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        Integer shareId = Integer.valueOf((String) headers.get("share_id"));
        try{
            this.shareService.auditByIdInDB(shareId, (ShareAuditDTO) arg);
            return RocketMQLocalTransactionState.COMMIT;
        }catch (Exception e){
            return RocketMQLocalTransactionState.ROLLBACK;
        }


    }

    /**
     * 未收到确认时执行此方法
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        log.info("收到会查信息---》{}",msg.toString());
        return RocketMQLocalTransactionState.COMMIT;
    }
}
