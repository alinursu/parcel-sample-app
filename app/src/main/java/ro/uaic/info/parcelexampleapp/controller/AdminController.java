package ro.uaic.info.parcelexampleapp.controller;

import ro.uaic.info.parcelexampleapp.api.AdminApi;
import ro.uaic.info.parcelexampleapp.domain.Awb;
import ro.uaic.info.parcelexampleapp.domain.DeliveryRoute;
import ro.uaic.info.parcelexampleapp.service.AwbService;
import ro.uaic.info.parcelexampleapp.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AdminController implements AdminApi {
    @Autowired
    private AwbService awbService;

    @Autowired
    private DeliveryService deliveryService;

    @Override
    public String allAwbsAdminPage() {
        List<Awb> awbs = awbService.getAllAwbsNotDelivered();
        // TODO: add to model
        return "admin/all-awbs-page";
    }

    @Override
    public String allDeliveryRoutesPage() {
        List<DeliveryRoute> deliveryRoutes = deliveryService.getAllDeliveryRoutesForToday();
        // TODO: add to model
        return "admin/all-routes-page";
    }
}
