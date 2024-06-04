package ro.uaic.info.parcel.database_management_service.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddUserRequestDto {
    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;
}
