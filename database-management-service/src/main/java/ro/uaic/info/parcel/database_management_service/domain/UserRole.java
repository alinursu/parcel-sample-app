package ro.uaic.info.parcel.database_management_service.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "user_roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(id, userRole.id) && Objects.equals(user, userRole.user) && Objects.equals(role, userRole.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, role);
    }
}
