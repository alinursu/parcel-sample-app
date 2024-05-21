package ro.uaic.info.parcelexampleapp.controller;

import org.springframework.ui.Model;
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
    public String allAwbsAdminPage(Model model) {
        List<Awb> awbs = awbService.getAllAwbsNotDelivered();
        model.addAttribute("awbList", awbs);
        return "admin/all-awbs-page";
    }

    @Override
    public String allDeliveryRoutesPage(Model model) {
        List<DeliveryRoute> deliveryRoutes = deliveryService.getAllDeliveryRoutesForToday();
        model.addAttribute("routesList", deliveryRoutes);
        return "admin/all-routes-page";
    }
}
