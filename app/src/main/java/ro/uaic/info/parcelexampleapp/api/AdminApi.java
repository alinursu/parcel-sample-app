package ro.uaic.info.parcelexampleapp.api;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
public interface AdminApi {
    @GetMapping("/awb")
    String allAwbsAdminPage(Model model);

    @GetMapping("/routes")
    String allDeliveryRoutesPage(Model model);
}
