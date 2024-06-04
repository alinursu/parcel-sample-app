package ro.uaic.info.parcel.paymentservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ro.uaic.info.parcel.database_management_service.domain.Awb;
import ro.uaic.info.parcel.database_management_service.domain.AwbStatus;
import ro.uaic.info.parcel.database_management_service.domain.User;
import ro.uaic.info.parcel.paymentservice.client.DBMServiceClient;
import ro.uaic.info.parcel.paymentservice.client.UserServiceClient;
import ro.uaic.info.parcel.paymentservice.domain.exception.AwbAlreadyPayedException;
import ro.uaic.info.parcel.paymentservice.domain.exception.AwbDoesNotExistException;
import ro.uaic.info.parcel.paymentservice.domain.exception.AwbIsNotYoursException;
import ro.uaic.info.parcel.userservice.jwt.JwtTokenContent;

@Service
public class PaymentService {
    @Autowired
    DBMServiceClient dbmServiceClient;

    @Autowired
    UserServiceClient userServiceClient;

    public void payAwb(String awbNumber, String jwtToken) throws AwbAlreadyPayedException, AwbDoesNotExistException, AwbIsNotYoursException {
        ResponseEntity<Awb> response = dbmServiceClient.getAwbByUniqueNumber(awbNumber);
        if (response.getStatusCode().value() == HttpStatus.NOT_FOUND.value() || response.getBody() == null) {
            throw new AwbDoesNotExistException(awbNumber);
        }

        Awb awb = response.getBody();

        ResponseEntity<JwtTokenContent> jwtContentsResponse = userServiceClient.decodeJwtToken(jwtToken);
        if (jwtContentsResponse.getStatusCode() != HttpStatus.OK || jwtContentsResponse.getBody() == null) {
            throw new AwbIsNotYoursException(awbNumber);
        }

        String email = jwtContentsResponse.getBody().getEmail();
        ResponseEntity<User> userResponse = dbmServiceClient.getUserByEmail(email);

        if (userResponse.getStatusCode() != HttpStatus.OK || userResponse.getBody() == null) {
            throw new AwbIsNotYoursException(awbNumber);
        }

        if (!awb.getOwner().equals(userResponse.getBody())) {
            throw new AwbIsNotYoursException(awbNumber);
        }

        // Dummy implementation for the payment mechanism
        if (awb.getStatus() != AwbStatus.AWAITING_PAYMENT) {
            throw new AwbAlreadyPayedException(awb.getUniqueNumber());
        }

        awb.setStatus(AwbStatus.ORDERED);
        dbmServiceClient.updateAwb(awb);
    }
}
