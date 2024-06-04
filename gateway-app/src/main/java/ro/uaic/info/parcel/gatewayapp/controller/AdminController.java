package ro.uaic.info.parcel.gatewayapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ro.uaic.info.parcel.database_management_service.domain.Awb;
import ro.uaic.info.parcel.database_management_service.domain.DeliveryRoute;
import ro.uaic.info.parcel.gatewayapp.api.AdminApi;
import ro.uaic.info.parcel.gatewayapp.client.DeliveryServiceClient;
import ro.uaic.info.parcel.gatewayapp.domain.exception.InternalServerErrorException;

import java.util.List;

@Controller
public class AdminController implements AdminApi {
    @Autowired
    private DeliveryServiceClient deliveryServiceClient;

    @Override
    public String allAwbsAdminPage(Model model) throws InternalServerErrorException {
        ResponseEntity<List<Awb>> response = deliveryServiceClient.getAllNonDeliveredAwbs();
        if (response.getStatusCode().value() != HttpStatus.OK.value() || response.getBody() == null) {
            throw new InternalServerErrorException("An error occured.");
        }

        List<Awb> awbs = response.getBody();
        model.addAttribute("awbList", awbs);
        return "admin/all-awbs-page";
    }

    @Override
    public String allDeliveryRoutesPage(Model model) throws InternalServerErrorException {
        ResponseEntity<List<DeliveryRoute>> response = deliveryServiceClient.getAllRoutesForToday();
        if (response.getStatusCode().value() != HttpStatus.OK.value() || response.getBody() == null) {
            throw new InternalServerErrorException("An error occured.");
        }

        List<DeliveryRoute> deliveryRoutes = response.getBody();
        model.addAttribute("routesList", deliveryRoutes);
        return "admin/all-routes-page";
    }
}
