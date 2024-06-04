package ro.uaic.info.parcel.deliveryservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ro.uaic.info.parcel.database_management_service.domain.Awb;
import ro.uaic.info.parcel.database_management_service.domain.AwbStatus;
import ro.uaic.info.parcel.database_management_service.domain.User;
import ro.uaic.info.parcel.database_management_service.domain.dto.AddAwbRequestDto;
import ro.uaic.info.parcel.deliveryservice.client.DBMServiceClient;
import ro.uaic.info.parcel.deliveryservice.client.UserServiceClient;
import ro.uaic.info.parcel.deliveryservice.domain.dto.CreateAwbDto;
import ro.uaic.info.parcel.deliveryservice.domain.dto.DtoValidator;
import ro.uaic.info.parcel.deliveryservice.domain.exception.AwbDoesNotExistException;
import ro.uaic.info.parcel.deliveryservice.domain.exception.InvalidAwbDtoException;
import ro.uaic.info.parcel.deliveryservice.domain.exception.UserNotLoggedInException;
import ro.uaic.info.parcel.userservice.jwt.JwtTokenContent;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AwbService {
    @Autowired
    private DBMServiceClient dbmServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;

    public List<Awb> getAllAwbsEligibleForADeliveryRoute() throws Exception {
        ResponseEntity<List<Awb>> allAwbsResponse = dbmServiceClient.getAllAwbs();
        if (allAwbsResponse.getStatusCode().value() != HttpStatus.OK.value() || allAwbsResponse.getBody() == null) {
            throw new Exception();
        }

        List<Awb> allAwbs = allAwbsResponse.getBody();

        return allAwbs.stream()
                .filter(awb -> awb.getStatus() == AwbStatus.ORDERED || awb.getStatus() == AwbStatus.IN_DEPOSIT)
                .collect(Collectors.toList());
    }

    public Awb updateAwbStatus(String awbNumber, AwbStatus newStatus) throws Exception {
        ResponseEntity<Awb> awbResponse = dbmServiceClient.getAwbByUniqueNumber(awbNumber);
        if (awbResponse.getStatusCode().value() != HttpStatus.OK.value() || awbResponse.getBody() == null) {
            throw new AwbDoesNotExistException(awbNumber);
        }

        Awb awb = awbResponse.getBody();
        awb.setStatus(newStatus);

        ResponseEntity<Awb> awbUpdateResponse = dbmServiceClient.updateAwb(awb);
        if (awbUpdateResponse.getStatusCode().value() != HttpStatus.OK.value()) {
            throw new Exception("Cannot update awb status");
        }

        return awbUpdateResponse.getBody();
    }

    public Awb addAwb(String jwtToken, CreateAwbDto dto) throws Exception {
        ResponseEntity<JwtTokenContent> jwtTokenResponse = userServiceClient.decodeJwtToken(jwtToken);
        if (jwtTokenResponse.getStatusCode().value() != HttpStatus.OK.value() || jwtTokenResponse.getBody() == null) {
            throw new UserNotLoggedInException();
        }

        String email = jwtTokenResponse.getBody().getEmail();
        ResponseEntity<User> userResponse = dbmServiceClient.getUserByEmail(email);
        if (userResponse.getStatusCode().value() != HttpStatus.OK.value() || userResponse.getBody() == null) {
            throw new UserNotLoggedInException();
        }

        DtoValidator.isValid(dto);

        ResponseEntity<Awb> addAwbResponse = dbmServiceClient.addAwb(
                AddAwbRequestDto.builder()
                        .deliveryAddress(dto.getDeliveryAddress())
                        .shipmentAddress(dto.getShipmentAddress())
                        .price(100D)
                        .uniqueNumber(generateUniqueNumber())
                        .ownerId(userResponse.getBody().getId())
                        .build()
        );
        if (addAwbResponse.getStatusCode().value() != HttpStatus.CREATED.value() || addAwbResponse.getBody() == null) {
            throw new Exception();
        }

        return addAwbResponse.getBody();
    }

    public List<Awb> getAllAwbsOfUser(String jwtToken) throws Exception {
        ResponseEntity<JwtTokenContent> jwtResponse = userServiceClient.decodeJwtToken(jwtToken);
        if (jwtResponse.getStatusCode().value() != HttpStatus.OK.value() || jwtResponse.getBody() == null) {
            throw new UserNotLoggedInException();
        }

        String email = jwtResponse.getBody().getEmail();
        ResponseEntity<User> userResponse = dbmServiceClient.getUserByEmail(email);
        if (userResponse.getStatusCode().value() != HttpStatus.OK.value() || userResponse.getBody() == null) {
            throw new UserNotLoggedInException();
        }

        ResponseEntity<List<Awb>> allAwbsResponse = dbmServiceClient.getAllAwbs();
        if (allAwbsResponse.getStatusCode().value() != HttpStatus.OK.value() || allAwbsResponse.getBody() == null) {
            throw new Exception();
        }

        return allAwbsResponse.getBody().stream()
                .filter(awb -> awb.getOwner().equals(userResponse.getBody()))
                .collect(Collectors.toList());
    }

    public Awb getAwbOfUserWithUniqueNumber(String jwtToken, String uniqueNumber) throws Exception {
        List<Awb> allAwbsOfUser = getAllAwbsOfUser(jwtToken);
        Optional<Awb> awb = allAwbsOfUser.stream()
                .filter(a -> a.getUniqueNumber().equals(uniqueNumber))
                .findFirst();

        if (awb.isEmpty()) {
            throw new AwbDoesNotExistException(uniqueNumber);
        }

        return awb.get();
    }

    public List<Awb> getAllNonDeliveredAwbs() throws Exception {
        ResponseEntity<List<Awb>> allAwbsResponse = dbmServiceClient.getAllAwbs();
        if (allAwbsResponse.getStatusCode().value() != HttpStatus.OK.value() || allAwbsResponse.getBody() == null) {
            throw new Exception();
        }

        return allAwbsResponse.getBody().stream()
                .filter(awb -> !awb.getStatus().equals(AwbStatus.DELIVERED))
                .collect(Collectors.toList());
    }

    private String generateUniqueNumber() {
        int n = 16;

        // length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString = new String(array, StandardCharsets.UTF_8);

        // Create a StringBuffer to store the result
        StringBuilder r = new StringBuilder();

        // Append first 20 alphanumeric characters
        // from the generated random String into the result
        for (int k = 0; k < randomString.length(); k++) {

            char ch = randomString.charAt(k);

            if (((ch >= 'a' && ch <= 'z')
                    || (ch >= 'A' && ch <= 'Z')
                    || (ch >= '0' && ch <= '9'))
                    && (n > 0)) {

                r.append(ch);
                n--;
            }
        }

        // return the resultant string
        return r.toString();
    }
}
