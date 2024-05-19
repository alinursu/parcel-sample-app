package ro.uaic.info.parcelexampleapp.domain.dto;

import ro.uaic.info.parcelexampleapp.domain.AwbStatus;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateAwbStatusDto {
    @NonNull
    private AwbStatus newStatus;
}
