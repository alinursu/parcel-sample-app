package ro.uaic.info.parcelexampleapp.service;

import ro.uaic.info.parcelexampleapp.domain.Awb;
import ro.uaic.info.parcelexampleapp.domain.DeliveryRoute;
import ro.uaic.info.parcelexampleapp.domain.User;
import ro.uaic.info.parcelexampleapp.repository.DeliveryRouteRepository;
import ro.uaic.info.parcelexampleapp.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DeliveryService {
    @Autowired
    private AwbService awbService;

    @Autowired
    private DeliveryRouteRepository deliveryRouteRepository;

    public DeliveryRoute getDeliveryRouteForUser(User user) {
        Date today = new Date();

        Optional<DeliveryRoute> deliveryRouteOptional = deliveryRouteRepository.findAllByDeliveryMan(user).stream()
                .filter(dr -> DateUtils.areDatesOnSameDay(dr.getDate(), today))
                .findFirst();

        if (deliveryRouteOptional.isPresent()) {
            return deliveryRouteOptional.get();
        }

        DeliveryRoute deliveryRoute = generateNewDeliveryRouteForUser(user);
        deliveryRoute = deliveryRouteRepository.save(deliveryRoute);
        return deliveryRoute;
    }

    public List<DeliveryRoute> getAllDeliveryRoutesForToday() {
        Date today = new Date();

        return getAllDeliveryRoutes().stream()
                .filter(dr -> DateUtils.areDatesOnSameDay(dr.getDate(), today))
                .collect(Collectors.toList());
    }

    private DeliveryRoute generateNewDeliveryRouteForUser(User user) {
        Random random = new Random();

        List<Awb> eligibleAwbs = awbService.getAllAwbsEligibleForADeliveryRoute();
        int routeLength = random.nextInt(3) + 2; // Random int in [2, 5]

        List<Awb> awbsInRoute = new ArrayList<>();
        int index = 0;

        while (index < eligibleAwbs.size() && routeLength < awbsInRoute.size()) {
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

    private boolean awbIsNotPartOfAnyDeliveryRoute(Awb awb) {
        return getAllDeliveryRoutes().stream()
                .noneMatch(dr -> dr.getAwbs().contains(awb));
    }

    private List<DeliveryRoute> getAllDeliveryRoutes() {
        return StreamSupport.stream(deliveryRouteRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
