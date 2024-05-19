package ro.uaic.info.parcelexampleapp.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public interface HomeApi {
    @GetMapping
    String homePage();
}
