package ro.uaic.info.parcel.database_management_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.uaic.info.parcel.database_management_service.domain.DeliveryRoute;
import ro.uaic.info.parcel.database_management_service.repository.DeliveryRouteRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DeliveryRouteService {
    @Autowired
    private DeliveryRouteRepository deliveryRouteRepository;

    public List<DeliveryRoute> getAllDeliveryRoutes() {
        return StreamSupport.stream(deliveryRouteRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<DeliveryRoute> getAllDeliveryRoutesOfUser(Long id) {
        return getAllDeliveryRoutes().stream()
                .filter(dr -> dr.getDeliveryMan().getId().equals(id))
                .collect(Collectors.toList());
    }

    public DeliveryRoute addOrUpdate(DeliveryRoute route) {
        return deliveryRouteRepository.save(route);
    }
}
