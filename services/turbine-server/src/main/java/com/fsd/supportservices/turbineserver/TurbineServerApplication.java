package com.fsd.supportservices.turbineserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableTurbine
@EnableDiscoveryClient
public class TurbineServerApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(TurbineServerApplication.class).run(args);
	}
}
