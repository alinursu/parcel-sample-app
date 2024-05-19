package ro.uaic.info.parcelexampleapp.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "delivery_routes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DeliveryRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "delivery_man_id")
    private User deliveryMan;

    @Column(name = "delivery_date")
    private Date date;

    @ManyToMany
    private List<Awb> awbs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryRoute that = (DeliveryRoute) o;
        return Objects.equals(id, that.id) && Objects.equals(deliveryMan, that.deliveryMan) && Objects.equals(date, that.date) && Objects.equals(awbs, that.awbs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deliveryMan, date, awbs);
    }
}
