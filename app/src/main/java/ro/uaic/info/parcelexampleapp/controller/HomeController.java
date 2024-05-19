package ro.uaic.info.parcelexampleapp.controller;

import ro.uaic.info.parcelexampleapp.api.HomeApi;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController implements HomeApi {
    @Override
    public String homePage() {
        return "home-page";
    }
}
