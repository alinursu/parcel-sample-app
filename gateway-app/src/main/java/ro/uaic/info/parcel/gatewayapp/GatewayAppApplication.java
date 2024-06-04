package ro.uaic.info.parcel.gatewayapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GatewayAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayAppApplication.class, args);
	}

}
