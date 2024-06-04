package ro.uaic.info.parcel.database_management_service.repository;

import org.springframework.data.repository.CrudRepository;
import ro.uaic.info.parcel.database_management_service.domain.DeliveryRoute;
import ro.uaic.info.parcel.database_management_service.domain.User;

import java.util.List;

public interface DeliveryRouteRepository extends CrudRepository<DeliveryRoute, Long> {
    List<DeliveryRoute> findAllByDeliveryMan(User deliveryMan);
}
