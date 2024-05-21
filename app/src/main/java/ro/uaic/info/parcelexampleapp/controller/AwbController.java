package ro.uaic.info.parcelexampleapp.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import ro.uaic.info.parcelexampleapp.api.AwbApi;
import ro.uaic.info.parcelexampleapp.domain.Awb;
import ro.uaic.info.parcelexampleapp.domain.User;
import ro.uaic.info.parcelexampleapp.domain.dto.CreateAwbDto;
import ro.uaic.info.parcelexampleapp.domain.exception.*;
import ro.uaic.info.parcelexampleapp.security.AuthenticatedUser;
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
    public String userAwbsPage(Model model, HttpServletRequest request) throws InternalServerErrorException {
        User user = userService.getUserByEmail(
                ((AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                        .getUsername()
        );

        if (user == null) {
            throw new InternalServerErrorException();
        }

        List<Awb> awbs = awbService.getAllAwbsForUser(user);

        model.addAttribute("awbList", awbs);

        return "awb/user-awbs-page";
    }

    @Override
    public String awbPage(Model model, String awbNumber) throws AwbDoesNotExistException, InternalServerErrorException, AwbIsNotYoursException {
        User user = userService.getUserByEmail(
                ((AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                        .getUsername()
        );

        if (user == null) {
            throw new InternalServerErrorException();
        }

        Awb awb = awbService.getAwbByNumber(awbNumber);

        if (awb.getOwner() != user) {
            throw new AwbIsNotYoursException(awbNumber);
        }

        model.addAttribute("awb", awb);
        return "awb/awb-page";
    }

    @Override
    public String createAwbPage(Model model) {
        model.addAttribute("createAwbRequestDto", new CreateAwbDto());
        return "awb/create-awb-page";
    }

    @Override
    public String createAwb(CreateAwbDto dto, HttpServletRequest request) throws InvalidJwtCookieException, MissingJwtCookieException, InvalidAwbDtoException {
        User user = userService.getUserByEmail(
                new JwtTokenUtils().getEmailFromJwtCookie(request)
        );

        Awb awb = awbService.addAwb(dto, user);
        return "redirect:/awb/" + awb.getUniqueNumber();
    }
}
