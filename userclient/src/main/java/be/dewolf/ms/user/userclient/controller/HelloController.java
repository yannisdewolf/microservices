package be.dewolf.ms.user.userclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class HelloController {


    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/hi-manual")
    public String runningServerManually() {
        final List<ServiceInstance> instances = this.discoveryClient.getInstances("reportingservice");
        for(final ServiceInstance instance : instances){
            System.out.println("Instance: " + instance.getHost().toString());
            System.out.println("Port: " + instance.getPort());
            System.out.println("URI: " + instance.getUri().toString());
        }
        return this.restTemplate.getForObject("http://reportingservice/runningport", String.class);

    }


}
