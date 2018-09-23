package be.dewolf.ms.reportingservice

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@RestController
class ReportingServiceApplication {

    private final Logger logger = LoggerFactory.getLogger(ReportingServiceApplication.class)

    public static void main(String[] args) {
        SpringApplication.run(ReportingServiceApplication.class, args)
    }

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value('${server.port}')
    private String port

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

    @RequestMapping("/")
    public String hi() {
        logger.info('hi')
        return 'hi'
    }

    @RequestMapping("/runningport")
    public String runningPort() {
        return port
    }

}
