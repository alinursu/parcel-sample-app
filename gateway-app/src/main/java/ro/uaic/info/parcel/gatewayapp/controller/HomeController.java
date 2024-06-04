package ro.uaic.info.parcel.gatewayapp.controller;

import org.springframework.stereotype.Controller;
import ro.uaic.info.parcel.gatewayapp.api.HomeApi;

@Controller
public class HomeController implements HomeApi {
    @Override
    public String homePage() {
        return "home-page";
    }
}
