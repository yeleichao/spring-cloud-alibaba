package com.ylc.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * @Description: gateay 过滤器
 * @Date: 2019/10/19
 */
@Slf4j
@Component
public class PreLogGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {
    @Override
    public GatewayFilter apply(NameValueConfig config) {


        return ((exchange, chain) -> {
            log.info("请求进来了...{},{}",config.getName(), config.getValue());
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().build();

            ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
            return  chain.filter(modifiedExchange);

        });


    }
}



























