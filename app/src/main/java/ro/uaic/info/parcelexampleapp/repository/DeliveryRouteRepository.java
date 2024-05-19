package ro.uaic.info.parcelexampleapp.repository;

import ro.uaic.info.parcelexampleapp.domain.DeliveryRoute;
import ro.uaic.info.parcelexampleapp.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeliveryRouteRepository extends CrudRepository<DeliveryRoute, Long> {
    List<DeliveryRoute> findAllByDeliveryMan(User deliveryMan);
}
