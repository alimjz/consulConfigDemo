package com.behsa.consulconfigdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoServiceDiscovery {
    private DiscoveryClient discoveryClient;

    @Autowired
    public DemoServiceDiscovery(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public List<String> getAvailableServicesFromConsul(){
        List<String> services = discoveryClient.getServices();
        return services;
    }

    public List<ServiceInstance> getAvailableServiceByInstance(String instanceName){
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(instanceName);
        return serviceInstances;
    }
}
