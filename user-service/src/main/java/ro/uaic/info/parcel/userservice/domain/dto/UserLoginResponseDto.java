package ro.uaic.info.parcel.userservice.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserLoginResponseDto {
    @NonNull
    private String cookieName;

    @NonNull
    private String token;
}
