package ro.uaic.info.parcelexampleapp.api;

import org.springframework.ui.Model;
import ro.uaic.info.parcelexampleapp.domain.dto.CreateAwbDto;
import ro.uaic.info.parcelexampleapp.domain.exception.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/awb")
public interface AwbApi {
    @GetMapping
    String userAwbsPage(Model model, HttpServletRequest request) throws InternalServerErrorException;

    @GetMapping("/{awbNumber}")
    String awbPage(Model model, @PathVariable String awbNumber) throws AwbDoesNotExistException, InternalServerErrorException, AwbIsNotYoursException;

    @GetMapping("/add")
    String createAwbPage(Model model);

    @PostMapping("/add")
    String createAwb(CreateAwbDto dto, HttpServletRequest request) throws InvalidJwtCookieException, MissingJwtCookieException, InvalidAwbDtoException;
}
