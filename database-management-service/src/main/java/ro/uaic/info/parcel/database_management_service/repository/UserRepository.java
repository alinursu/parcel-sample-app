package ro.uaic.info.parcel.database_management_service.repository;

import org.springframework.data.repository.CrudRepository;
import ro.uaic.info.parcel.database_management_service.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
