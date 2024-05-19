package ro.uaic.info.parcelexampleapp.controller;

import ro.uaic.info.parcelexampleapp.api.AwbApi;
import ro.uaic.info.parcelexampleapp.domain.Awb;
import ro.uaic.info.parcelexampleapp.domain.User;
import ro.uaic.info.parcelexampleapp.domain.dto.CreateAwbDto;
import ro.uaic.info.parcelexampleapp.domain.exception.AwbDoesNotExistException;
import ro.uaic.info.parcelexampleapp.domain.exception.InvalidDtoException;
import ro.uaic.info.parcelexampleapp.domain.exception.InvalidJwtCookieException;
import ro.uaic.info.parcelexampleapp.domain.exception.MissingJwtCookieException;
import ro.uaic.info.parcelexampleapp.security.jwt.JwtTokenUtils;
import ro.uaic.info.parcelexampleapp.service.AwbService;
import ro.uaic.info.parcelexampleapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AwbController implements AwbApi {
    @Autowired
    private AwbService awbService;

    @Autowired
    private UserService userService;

    @Override
    public String userAwbsPage(HttpServletRequest request) throws InvalidJwtCookieException, MissingJwtCookieException {
        User user = userService.getUserByEmail(
                new JwtTokenUtils().getEmailFromJwtCookie(request)
        );

        List<Awb> awbs = awbService.getAllAwbsForUser(user);
        // TODO: add to model
        return "awb/user-awbs-page";
    }

    @Override
    public String awbPage(String awbNumber) throws AwbDoesNotExistException {
        Awb awb = awbService.getAwbByNumber(awbNumber);

        // TODO: add to model
        return "awb/awb-page";
    }

    @Override
    public String createAwb(CreateAwbDto dto, HttpServletRequest request) throws InvalidDtoException, InvalidJwtCookieException, MissingJwtCookieException {
        User user = userService.getUserByEmail(
                new JwtTokenUtils().getEmailFromJwtCookie(request)
        );

        Awb awb = awbService.addAwb(dto, user);
        return "redirect:/awb/" + awb.getUniqueNumber();
    }
}
