package ro.uaic.info.parcelexampleapp.service;

import ro.uaic.info.parcelexampleapp.domain.Awb;
import ro.uaic.info.parcelexampleapp.domain.AwbStatus;
import ro.uaic.info.parcelexampleapp.domain.exception.AwbAlreadyPayedException;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public Awb payAwb(Awb awb) throws AwbAlreadyPayedException {
        // Dummy implementation for the payment mechanism
        if (awb.getStatus() != AwbStatus.AWAITING_PAYMENT) {
            throw new AwbAlreadyPayedException(awb.getUniqueNumber());
        }

        awb.setStatus(AwbStatus.ORDERED);
        return awb;
    }
}
