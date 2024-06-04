package ro.uaic.info.parcel.deliveryservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ro.uaic.info.parcel.database_management_service.domain.Awb;
import ro.uaic.info.parcel.database_management_service.domain.DeliveryRoute;
import ro.uaic.info.parcel.database_management_service.domain.User;
import ro.uaic.info.parcel.deliveryservice.client.DBMServiceClient;
import ro.uaic.info.parcel.deliveryservice.client.UserServiceClient;
import ro.uaic.info.parcel.deliveryservice.domain.exception.UserNotLoggedInException;
import ro.uaic.info.parcel.deliveryservice.utils.DateUtils;
import ro.uaic.info.parcel.userservice.jwt.JwtTokenContent;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeliveryService {
    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private DBMServiceClient dbmServiceClient;

    @Autowired
    private AwbService awbService;

    public DeliveryRoute getDeliveryRouteForUser(String jwtToken) throws Exception {
        ResponseEntity<JwtTokenContent> jwtTokenResponse = userServiceClient.decodeJwtToken(jwtToken);
        if (jwtTokenResponse.getStatusCode().value() != HttpStatus.OK.value() || jwtTokenResponse.getBody() == null) {
            throw new UserNotLoggedInException();
        }

        String email = jwtTokenResponse.getBody().getEmail();

        ResponseEntity<User> userResponse = dbmServiceClient.getUserByEmail(email);
        if (userResponse.getStatusCode().value() != HttpStatus.OK.value() || userResponse.getBody() == null) {
            throw new UserNotLoggedInException();
        }

        User user = userResponse.getBody();

        ResponseEntity<List<DeliveryRoute>> deliveryRoutesOfUserResponse = dbmServiceClient.getAllDeliveryRoutesOfUser(user.getId());
        if (deliveryRoutesOfUserResponse.getStatusCode().value() != HttpStatus.OK.value() || deliveryRoutesOfUserResponse.getBody() == null) {
            throw new Exception();
        }

        List<DeliveryRoute> deliveryRoutesOfUser = deliveryRoutesOfUserResponse.getBody();
        Date today = new Date();

        Optional<DeliveryRoute> deliveryRouteOptional = deliveryRoutesOfUser.stream()
                .filter(dr -> DateUtils.areDatesOnSameDay(dr.getDate(), today))
                .findFirst();

        if (deliveryRouteOptional.isPresent()) {
            return deliveryRouteOptional.get();
        }

        DeliveryRoute deliveryRoute = generateNewDeliveryRouteForUser(user);
        ResponseEntity<DeliveryRoute> addRouteResponse = dbmServiceClient.addDeliveryRoute(deliveryRoute);
        if (addRouteResponse.getStatusCode().value() != HttpStatus.CREATED.value() || addRouteResponse.getBody() == null) {
            throw new Exception();
        }

        return addRouteResponse.getBody();
    }

    public List<DeliveryRoute> getAllDeliveryRoutesForToday() throws Exception {
        ResponseEntity<List<DeliveryRoute>> response = dbmServiceClient.getAllDeliveryRoutes();
        if (response.getStatusCode().value() != HttpStatus.OK.value() || response.getBody() == null) {
            throw new Exception();
        }

        Date now = new Date();

        return response.getBody().stream()
                .filter(dr -> DateUtils.areDatesOnSameDay(dr.getDate(), now))
                .collect(Collectors.toList());
    }

    private DeliveryRoute generateNewDeliveryRouteForUser(User user) throws Exception {
        Random random = new Random();

        List<Awb> eligibleAwbs = awbService.getAllAwbsEligibleForADeliveryRoute();
        int routeLength = random.nextInt(3) + 2; // Random int in [2, 5]

        List<Awb> awbsInRoute = new ArrayList<>();
        int index = 0;

        while (index < eligibleAwbs.size() && awbsInRoute.size() < routeLength) {
            Awb awb = eligibleAwbs.get(random.nextInt(eligibleAwbs.size()));

            if (!awbsInRoute.contains(awb) && awbIsNotPartOfAnyDeliveryRoute(awb)) {
                awbsInRoute.add(awb);
            }

            index++;
        }

        return DeliveryRoute.builder()
                .deliveryMan(user)
                .date(new Date())
                .awbs(awbsInRoute)
                .build();
    }

    private boolean awbIsNotPartOfAnyDeliveryRoute(Awb awb) throws Exception {
        ResponseEntity<List<DeliveryRoute>> response = dbmServiceClient.getAllDeliveryRoutes();
        if (response.getStatusCode().value() != HttpStatus.OK.value() || response.getBody() == null) {
            throw new Exception();
        }

        List<DeliveryRoute> deliveryRoutes = response.getBody();

        return deliveryRoutes.stream()
                .noneMatch(dr -> dr.getAwbs().contains(awb));
    }
}
