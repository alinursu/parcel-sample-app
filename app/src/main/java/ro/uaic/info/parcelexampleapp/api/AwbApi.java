package ro.uaic.info.parcelexampleapp.api;

import ro.uaic.info.parcelexampleapp.domain.dto.CreateAwbDto;
import ro.uaic.info.parcelexampleapp.domain.exception.AwbDoesNotExistException;
import ro.uaic.info.parcelexampleapp.domain.exception.InvalidDtoException;
import ro.uaic.info.parcelexampleapp.domain.exception.InvalidJwtCookieException;
import ro.uaic.info.parcelexampleapp.domain.exception.MissingJwtCookieException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/awb")
public interface AwbApi {
    @GetMapping
    String userAwbsPage(HttpServletRequest request) throws InvalidJwtCookieException, MissingJwtCookieException;

    @GetMapping("/{awbNumber}")
    String awbPage(@PathVariable String awbNumber) throws AwbDoesNotExistException;

    @PostMapping
    String createAwb(CreateAwbDto dto, HttpServletRequest request) throws InvalidDtoException, InvalidJwtCookieException, MissingJwtCookieException;
}
