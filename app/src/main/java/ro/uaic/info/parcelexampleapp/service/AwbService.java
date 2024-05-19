package ro.uaic.info.parcelexampleapp.service;

import ro.uaic.info.parcelexampleapp.domain.Awb;
import ro.uaic.info.parcelexampleapp.domain.AwbStatus;
import ro.uaic.info.parcelexampleapp.domain.User;
import ro.uaic.info.parcelexampleapp.domain.dto.CreateAwbDto;
import ro.uaic.info.parcelexampleapp.domain.dto.DtoValidator;
import ro.uaic.info.parcelexampleapp.domain.exception.AwbDoesNotExistException;
import ro.uaic.info.parcelexampleapp.domain.exception.InvalidDtoException;
import ro.uaic.info.parcelexampleapp.repository.AwbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AwbService {
    @Autowired
    private AwbRepository awbRepository;

    public Awb getAwbByNumber(String awbNumber) throws AwbDoesNotExistException {
        Awb awb = awbRepository.findByUniqueNumber(awbNumber);
        if (awb == null) {
            throw new AwbDoesNotExistException(awbNumber);
        }
        return awb;
    }

    public List<Awb> getAllAwbsForUser(User user) {
        return getAllAwbs().stream()
                .filter(awb -> awb.getOwner().equals(user))
                .collect(Collectors.toList());
    }

    public Awb addAwb(CreateAwbDto dto, User user) throws InvalidDtoException {
        if (!DtoValidator.isValid(dto)) {
            throw new InvalidDtoException("add awb");
        }

        Awb awb = Awb.builder()
                .uniqueNumber(generateUniqueNumber())
                .deliveryAddress(dto.getDeliveryAddress())
                .shipmentAddress(dto.getShipmentAddress())
                .creationDate(new Date())
                .lastUpdateDate(new Date())
                .status(AwbStatus.AWAITING_PAYMENT)
                .owner(user)
                .build();

        awb = awbRepository.save(awb);
        return awb;
    }

    public Awb updateAwb(Awb newAwb) {
        newAwb = awbRepository.save(newAwb);
        newAwb.setLastUpdateDate(new Date());
        return newAwb;
    }

    public List<Awb> getAllAwbsEligibleForADeliveryRoute() {
        return getAllAwbs().stream()
                .filter(awb -> awb.getStatus() == AwbStatus.ORDERED || awb.getStatus() == AwbStatus.IN_DEPOSIT)
                .collect(Collectors.toList());
    }

    public List<Awb> getAllAwbsNotDelivered() {
        return getAllAwbs().stream()
                .filter(awb -> awb.getStatus() != AwbStatus.DELIVERED)
                .collect(Collectors.toList());
    }

    private List<Awb> getAllAwbs() {
        return StreamSupport.stream(awbRepository.findAll().spliterator(), false)
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
