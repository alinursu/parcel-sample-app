package ro.uaic.info.parcel.gatewayapp.api;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.uaic.info.parcel.deliveryservice.domain.dto.CreateAwbDto;
import ro.uaic.info.parcel.deliveryservice.domain.exception.AwbDoesNotExistException;
import ro.uaic.info.parcel.deliveryservice.domain.exception.InvalidAwbDtoException;
import ro.uaic.info.parcel.gatewayapp.domain.exception.InternalServerErrorException;
import ro.uaic.info.parcel.gatewayapp.domain.exception.MissingJwtCookieException;
import ro.uaic.info.parcel.paymentservice.domain.exception.AwbIsNotYoursException;

@RequestMapping("/awb")
public interface AwbApi {
    @GetMapping
    String userAwbsPage(Model model, HttpServletRequest request) throws InternalServerErrorException, MissingJwtCookieException;

    @GetMapping("/{awbNumber}")
    String awbPage(Model model, @PathVariable String awbNumber, HttpServletRequest request) throws AwbDoesNotExistException, InternalServerErrorException, AwbIsNotYoursException, MissingJwtCookieException;

    @GetMapping("/add")
    String createAwbPage(Model model);

    @PostMapping("/add")
    String createAwb(CreateAwbDto dto, HttpServletRequest request) throws InvalidAwbDtoException, MissingJwtCookieException, InternalServerErrorException;
}
