package com.ylc.contentcenter.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.Balancer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description: 同集群下，优先调用 权重大的
 * @Date: 2019/10/2
 */
@Configuration
@Slf4j
public class NacosSameClusterWeightRule extends AbstractLoadBalancerRule {
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        /**
         * 1.找到指定服务的所有实例 A
         * 2.过滤出相同集群下的所有实例 B
         * 3.如果B是空，就用A
         * 4.基于权重的负载均衡算法，返回1个实例
         */

        try {
            String clusterName = nacosDiscoveryProperties.getClusterName();
            BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
            //得到想要请求的微服务的名称
            String name = loadBalancer.getName();

            //拿到服务发现的相关API
            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();

            //1.找到指定服务的所有实例 A
            List<Instance> instances = namingService.selectInstances(name, true);

           // instances.get(0).getMetadata();//获取元数据


           //2.过滤出相同集群下的所有实例 B
           List<Instance> sameClustInstances = instances.stream()
                   .filter(instance -> Objects.equals(instance.getClusterName(),clusterName))
                   .collect(Collectors.toList());

           //3.如果B是空，就用A
           List<Instance> instancesToBeChosen = new ArrayList<>();
           if(CollectionUtils.isEmpty(sameClustInstances)){
               instancesToBeChosen = instances;
               log.warn("发生跨集群的调用，name={}, clusterName={}, instances={}", name,clusterName,instances);
           }else{
               instancesToBeChosen = sameClustInstances;
           }

            //4.基于权重的负载均衡算法，返回1个实例

            //Instance instance = namingService.selectOneHealthyInstance(name);
            Instance instance = ExtendBalancer.getMyHostByRandomWeight(instancesToBeChosen);
            log.info("选择的实例是：port={}, instance={}",instance.getPort(),instance);

            return new NacosServer(instance);
        } catch (NacosException e) {
            log.error("发生异常了",e);
            return null;
        }
    }
}
class ExtendBalancer extends Balancer{
    public static Instance getMyHostByRandomWeight(List<Instance> list){
        return Balancer.getHostByRandomWeight(list);
    }
}

























