package ro.uaic.info.parcel.gatewayapp.api;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.uaic.info.parcel.gatewayapp.domain.exception.InternalServerErrorException;

@RequestMapping("/admin")
public interface AdminApi {
    @GetMapping("/awb")
    String allAwbsAdminPage(Model model) throws InternalServerErrorException;

    @GetMapping("/routes")
    String allDeliveryRoutesPage(Model model) throws InternalServerErrorException;
}
