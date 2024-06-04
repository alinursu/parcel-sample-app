package ro.uaic.info.parcel.database_management_service.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddAwbRequestDto {
    @NonNull
    private String uniqueNumber;

    @NonNull
    private String shipmentAddress;

    @NonNull
    private String deliveryAddress;

    @NonNull
    private Long ownerId;

    @NonNull
    private Double price;
}
