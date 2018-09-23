package be.dewolf.ms.user.userclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
@EnableFeignClients
//@EnableDiscoveryClient
//@RibbonClient(name = "say-hello", configuration = SayHelloConfiguration.class)
public class UserclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserclientApplication.class, args);
    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private ReportingServiceClient reportingServiceClient;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/hi")
    public String hi() {
        return reportingServiceClient.runningPort();
    }

    @RequestMapping("/hi-manual")
    public String runningServerManually() {
        final List<ServiceInstance> instances = this.discoveryClient.getInstances("reportingservice");
        for(final ServiceInstance instance : instances){
            System.out.println("Instance: " + instance.getHost().toString());
            System.out.println("Port: " + instance.getPort());
            System.out.println("URI: " + instance.getUri().toString());
        }
        return this.restTemplate.getForObject("http://reportingservice/runningport", String.class);
//        List<ServiceInstance> reportingservice = this.discoveryClient.getInstances("reportingservice");

    }

}

@FeignClient("reportingservice")
interface ReportingServiceClient {
    @RequestMapping("/runningport")
    String runningPort();
}