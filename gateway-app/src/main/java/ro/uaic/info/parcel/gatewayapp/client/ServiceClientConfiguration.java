package ro.uaic.info.parcel.gatewayapp.client;

import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceClientConfiguration {
    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }
}
