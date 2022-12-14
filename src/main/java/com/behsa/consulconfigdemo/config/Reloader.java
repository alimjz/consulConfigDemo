package com.behsa.consulconfigdemo.config;

import com.ecwid.consul.v1.ConsulClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class Reloader {

    @Autowired
    private ConsulClient consulClient;

    @Autowired
    private ApplicationContext context;
    @EventListener
    public void reload(EnvironmentChangeEvent event){
        DefaultSingletonBeanRegistry beanRegistry = (DefaultSingletonBeanRegistry) context.getAutowireCapableBeanFactory();
        beanRegistry.destroySingleton("confs");
        Configurations properties = new Configurations();
        properties.setHostName(consulClient.getKVValue("config/ConfigDemo/app/config/hostname").getValue().getDecodedValue());
        properties.setIp(consulClient.getKVValue("config/ConfigDemo/app/config/ip").getValue().getDecodedValue());
        properties.setPort(consulClient.getKVValue("config/ConfigDemo/app/config/port").getValue().getDecodedValue());
        beanRegistry.registerSingleton("confs", properties);
        consulClient.setKVValue("config/ConfigDemo/app/config/hostname","omid");
        System.out.println("Reload.");
    }
}
