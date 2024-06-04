package ro.uaic.info.parcel.database_management_service.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ExceptionResponseDto {
    private String error;
}
