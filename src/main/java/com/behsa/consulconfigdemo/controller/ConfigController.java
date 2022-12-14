package com.behsa.consulconfigdemo.controller;

import com.behsa.consulconfigdemo.DemoServiceDiscovery;
import com.behsa.consulconfigdemo.config.Configurations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ConfigController {
    private Configurations configurations;

    private  ApplicationContext context;

    private DemoServiceDiscovery serviceDiscovery;

    @Autowired
    public ConfigController(Configurations configurations, ApplicationContext context,DemoServiceDiscovery serviceDiscovery) {
        this.configurations = configurations;
        this.context = context;
        this.serviceDiscovery = serviceDiscovery;
    }

    @GetMapping("/hostname")
    public ResponseEntity<String> returnHostName(){
        this.configurations = context.getBean(Configurations.class);
        return ResponseEntity.ok(configurations.getHostName());
    }
    @GetMapping("/services")
    public ResponseEntity<List<String>> returnServiceList(){
        return ResponseEntity.ok(serviceDiscovery.getAvailableServicesFromConsul());
    }

    @GetMapping("/services/{instanceName}")
    public ResponseEntity<List<ServiceInstance>> returnServiceInstance(@PathVariable("instanceName") String instanceName){
        return ResponseEntity.ok(serviceDiscovery.getAvailableServiceByInstance(instanceName));
    }


}
