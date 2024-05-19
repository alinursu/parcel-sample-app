package ro.uaic.info.parcelexampleapp.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateAwbDto {
    @NonNull
    private String shipmentAddress;

    @NonNull
    private String deliveryAddress;
}
