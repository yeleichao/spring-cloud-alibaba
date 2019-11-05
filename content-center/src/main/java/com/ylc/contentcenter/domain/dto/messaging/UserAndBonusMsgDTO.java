package com.ylc.contentcenter.domain.dto.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: rocketmq发送消息的dto
 * @Date: 2019/10/5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAndBonusMsgDTO {

    private Integer userId;

    private Integer bonus;
}
