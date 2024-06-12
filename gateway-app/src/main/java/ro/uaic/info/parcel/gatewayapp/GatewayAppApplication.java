package ro.uaic.info.parcel.gatewayapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableAutoConfiguration(exclude = { FlywayAutoConfiguration.class })
public class GatewayAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayAppApplication.class, args);
	}

}
