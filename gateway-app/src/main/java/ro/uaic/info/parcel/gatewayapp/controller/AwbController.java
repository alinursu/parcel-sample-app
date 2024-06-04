package ro.uaic.info.parcel.gatewayapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ro.uaic.info.parcel.database_management_service.domain.Awb;
import ro.uaic.info.parcel.gatewayapp.api.AwbApi;
import ro.uaic.info.parcel.deliveryservice.domain.dto.CreateAwbDto;
import ro.uaic.info.parcel.deliveryservice.domain.exception.AwbDoesNotExistException;
import ro.uaic.info.parcel.deliveryservice.domain.exception.InvalidAwbDtoException;
import ro.uaic.info.parcel.gatewayapp.client.DeliveryServiceClient;
import ro.uaic.info.parcel.gatewayapp.domain.exception.InternalServerErrorException;
import ro.uaic.info.parcel.gatewayapp.domain.exception.MissingJwtCookieException;
import ro.uaic.info.parcel.gatewayapp.util.CookieExtractor;
import ro.uaic.info.parcel.paymentservice.domain.exception.AwbIsNotYoursException;

import java.util.List;

@Controller
public class AwbController implements AwbApi {
    @Autowired
    private DeliveryServiceClient deliveryServiceClient;

    @Override
    public String userAwbsPage(Model model, HttpServletRequest request) throws InternalServerErrorException, MissingJwtCookieException {
        String jwtToken = CookieExtractor.extractJwtTokenFromCookie(request);

        ResponseEntity<List<Awb>> response = deliveryServiceClient.getAwbsOfUser(jwtToken);
        if (response.getStatusCode().value() != HttpStatus.OK.value() || response.getBody() == null) {
            throw new InternalServerErrorException("An error occured.");
        }

        List<Awb> awbs = response.getBody();
        model.addAttribute("awbList", awbs);
        return "awb/user-awbs-page";
    }

    @Override
    public String awbPage(Model model, String awbNumber, HttpServletRequest request) throws AwbDoesNotExistException, InternalServerErrorException, AwbIsNotYoursException, MissingJwtCookieException {
        String jwtToken = CookieExtractor.extractJwtTokenFromCookie(request);

        ResponseEntity<Awb> response = deliveryServiceClient.getAwbOfUserByUniqueNumber(jwtToken, awbNumber);
        if (response.getStatusCode().value() != HttpStatus.OK.value() || response.getBody() == null) {
            throw new InternalServerErrorException("An error occured.");
        }

        Awb awb = response.getBody();
        model.addAttribute("awb", awb);
        return "awb/awb-page";
    }

    @Override
    public String createAwbPage(Model model) {
        model.addAttribute("createAwbRequestDto", new CreateAwbDto());
        return "awb/create-awb-page";
    }

    @Override
    public String createAwb(CreateAwbDto dto, HttpServletRequest request) throws InvalidAwbDtoException, MissingJwtCookieException, InternalServerErrorException {
        String jwtToken = CookieExtractor.extractJwtTokenFromCookie(request);

        ResponseEntity<Awb> response = deliveryServiceClient.createAwb(jwtToken, dto);
        if (response.getStatusCode().value() != HttpStatus.OK.value() || response.getBody() == null) {
            throw new InternalServerErrorException("An error occured.");
        }

        Awb awb = response.getBody();
        return "redirect:/awb/" + awb.getUniqueNumber();
    }
}
