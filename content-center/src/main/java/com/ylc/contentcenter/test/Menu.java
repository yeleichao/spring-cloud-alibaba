package com.ylc.contentcenter.test;

import lombok.Builder;
import lombok.Data;

/**
 * @Description: TODO
 * @Date: 2019/9/28
 */
@Data
@Builder
public class Menu {
    private String name;

    private int calories;

}
