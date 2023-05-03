package by.tms.eshop.controller;

import by.tms.eshop.dto.UserValidationDto;
import by.tms.eshop.service.Facade;
import by.tms.eshop.validator.ExcludeLogValidation;
import by.tms.eshop.validator.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import static by.tms.eshop.utils.Constants.Attributes.USER_ACCESS_PERMISSION;
import static by.tms.eshop.utils.Constants.MappingPath.CREATE_USER;
import static by.tms.eshop.utils.Constants.MappingPath.ESHOP;
import static by.tms.eshop.utils.Constants.MappingPath.LOGIN;
import static by.tms.eshop.utils.Constants.MappingPath.SUCCESS_REGISTER;
import static by.tms.eshop.utils.ControllerUtils.closeUserSession;
import static by.tms.eshop.utils.ControllerUtils.fillUserValidationError;
import static by.tms.eshop.utils.ControllerUtils.fillsLoginVerifyErrors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

//    private final UserService userService;
    private final UserValidator userValidator;
    private final Facade facade;

    @GetMapping("/login")
    public ModelAndView showLoginPage(HttpSession session) {
        ModelAndView modelAndView;
        if (session.getAttribute(USER_ACCESS_PERMISSION) != null) {
            modelAndView = new ModelAndView(ESHOP);
        } else {
            modelAndView = new ModelAndView(LOGIN);
        }
        return modelAndView;
    }

    @PostMapping("/login-verify")
    public ModelAndView showLoginVerifyPage(HttpServletRequest request,
                                            @Validated(Default.class) @ModelAttribute("user") UserValidationDto user,
//                                            @Validated(Default.class) @ModelAttribute("user") User user,
                                            BindingResult bindingResult,
                                            ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            fillsLoginVerifyErrors(bindingResult, modelAndView);
            modelAndView.setViewName(LOGIN);
        } else {
            facade.checkLoginUser(request, user, modelAndView);
        }
        return modelAndView;
    }

//    private void checkLoginUser(HttpServletRequest request, UserValidationDto user, ModelAndView modelAndView) {
//        Optional<User> incomingUser = userService.getUserByLogin(user.getLogin());
//        if (incomingUser.isPresent() && isVerifyUser(incomingUser.get(), user.getPassword())) {
//            UserDto userDto = makeUserDtoModelTransfer(incomingUser.get());
//            saveUserSession(request, userDto);
//            modelAndView.setViewName(ESHOP);
//        } else {
//            modelAndView.addObject("loginError", RECHECK_DATA);
//            modelAndView.setViewName(LOGIN);
//        }
//    }


    @GetMapping("/logout")
    public ModelAndView showLogoutPage(HttpSession session) {
        closeUserSession(session);
        return new ModelAndView(ESHOP);
    }



    @GetMapping("/create-user")
    public ModelAndView create() {
        return new ModelAndView(CREATE_USER);
    }

    @PostMapping("/create-user")
    public ModelAndView createUser(HttpServletRequest request,
                                   @Validated({Default.class, ExcludeLogValidation.class}) @ModelAttribute("user") UserValidationDto user,
//                                   @Validated({Default.class, ExcludeLogValidation.class}) @ModelAttribute("user") User user,
                                   BindingResult bindingResult,
                                   ModelAndView modelAndView) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            fillUserValidationError(bindingResult, modelAndView);
            modelAndView.setViewName(CREATE_USER);
        } else {
            facade.createAndLoginUser(request, user);
            modelAndView.setViewName(SUCCESS_REGISTER);
        }
        return modelAndView;
    }



//    private void createAndLoginUser(HttpServletRequest request, UserValidationDto user) {
//        User userEntity = makeUserModelTransfer(user);
//        userService.addUser(userEntity);
////            userService.addUser(user);
////            UserDto userDto = makeUserDtoModelTransfer(user);
////            saveUserSession(request, userDto);
//        saveUserSession(request, makeUserDtoModelTransfer(userEntity));
//    }
}