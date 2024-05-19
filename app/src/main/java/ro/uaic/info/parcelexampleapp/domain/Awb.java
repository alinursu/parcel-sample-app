package ro.uaic.info.parcelexampleapp.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "awbs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Awb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uniqueNumber;

    private String shipmentAddress;
    private String deliveryAddress;

    @ManyToOne
    @JoinColumn(name="ordered_by_id")
    private User owner;

    @Column(name = "created_at")
    private Date creationDate;

    @Column(name = "last_updated_at")
    private Date lastUpdateDate;

    private AwbStatus status;

    private Double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Awb awb = (Awb) o;
        return Objects.equals(id, awb.id) && Objects.equals(uniqueNumber, awb.uniqueNumber) && Objects.equals(shipmentAddress, awb.shipmentAddress) && Objects.equals(deliveryAddress, awb.deliveryAddress) && Objects.equals(owner, awb.owner) && Objects.equals(creationDate, awb.creationDate) && Objects.equals(lastUpdateDate, awb.lastUpdateDate) && status == awb.status && Objects.equals(price, awb.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uniqueNumber, shipmentAddress, deliveryAddress, owner, creationDate, lastUpdateDate, status, price);
    }
}
