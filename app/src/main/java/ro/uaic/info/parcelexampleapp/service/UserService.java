package ro.uaic.info.parcelexampleapp.service;

import ro.uaic.info.parcelexampleapp.domain.User;
import ro.uaic.info.parcelexampleapp.domain.dto.DtoValidator;
import ro.uaic.info.parcelexampleapp.domain.dto.UserLoginDto;
import ro.uaic.info.parcelexampleapp.domain.dto.UserRegisterDto;
import ro.uaic.info.parcelexampleapp.domain.exception.EmailAlreadyInUseException;
import ro.uaic.info.parcelexampleapp.domain.exception.IncorrectCredentialsException;
import ro.uaic.info.parcelexampleapp.domain.exception.InvalidDtoException;
import ro.uaic.info.parcelexampleapp.repository.UserRepository;
import ro.uaic.info.parcelexampleapp.security.jwt.JwtTokenContent;
import ro.uaic.info.parcelexampleapp.security.jwt.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean registerUser(UserRegisterDto dto) throws InvalidDtoException, EmailAlreadyInUseException {
        if (!DtoValidator.isValid(dto)) {
            throw new InvalidDtoException("register user");
        }

        if (userRepository.findByEmail(dto.getEmail()) != null) {
            throw new EmailAlreadyInUseException(dto.getEmail());
        }

        User entity = User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();

        userRepository.save(entity);
        return true;
    }

    public String loginUser(UserLoginDto dto) throws InvalidDtoException, IncorrectCredentialsException {
        if (!DtoValidator.isValid(dto)) {
            throw new InvalidDtoException("login user");
        }

        User entity = userRepository.findByEmail(dto.getEmail());

        if (entity == null || !entity.getPassword().equals(dto.getPassword())) {
            throw new IncorrectCredentialsException();
        }

        return new JwtTokenUtils().createToken(
                JwtTokenContent.builder()
                        .email(entity.getEmail())
                        .fullName(entity.getFirstName() + " " + entity.getLastName())
                        .build()
        );
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
