package ro.uaic.info.parcel.deliveryservice.domain.dto;

import lombok.*;
import ro.uaic.info.parcel.database_management_service.domain.AwbStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateAwbStatusDto {
    @NonNull
    private AwbStatus newStatus;
}
