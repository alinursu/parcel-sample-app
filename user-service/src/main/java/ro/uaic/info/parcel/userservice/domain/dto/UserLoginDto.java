package ro.uaic.info.parcel.userservice.domain.dto;

import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserLoginDto {
    @NonNull
    private String email;

    @NonNull
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLoginDto that = (UserLoginDto) o;
        return email.equals(that.email) && password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
