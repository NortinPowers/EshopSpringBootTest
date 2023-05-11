package by.tms.eshop.validator;

import by.tms.eshop.dto.UserFormDto;
import by.tms.eshop.model.User;
import by.tms.eshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

import static by.tms.eshop.utils.Constants.ErrorMessage.EXISTING_EMAIL;
import static by.tms.eshop.utils.Constants.ErrorMessage.EXISTING_USER;
import static by.tms.eshop.utils.Constants.ErrorMessage.PASSWORDS_MATCHING;
import static by.tms.eshop.utils.Constants.UserVerifyField.EMAIL;
import static by.tms.eshop.utils.Constants.UserVerifyField.LOGIN;
import static by.tms.eshop.utils.Constants.UserVerifyField.VERIFY_PASSWORD;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserFormDto user = (UserFormDto) target;
        checkUserLoginAndEmail(errors, user);
        checkPasswordInputVerify(errors, user);
    }

    private void checkUserLoginAndEmail(Errors errors, UserFormDto testUser) {
        Optional<User> user = userService.getVerifyUser(testUser.getLogin(), testUser.getEmail());
        if (user.isPresent()) {
            User foundUser = user.get();
            checkUserByLogin(errors, testUser, foundUser);
            checkUserByEmail(errors, testUser, foundUser);
        }
    }

    private static void checkUserByEmail(Errors errors, UserFormDto testUser, User foundUser) {
        if (foundUser.getEmail().equals(testUser.getEmail())) {
            errors.rejectValue(EMAIL, "", EXISTING_EMAIL);
        }
    }

    private static void checkUserByLogin(Errors errors, UserFormDto testUser, User foundUser) {
        if (foundUser.getLogin().equals(testUser.getLogin())) {
            errors.rejectValue(LOGIN, "", EXISTING_USER);
        }
    }

    private void checkPasswordInputVerify(Errors errors, UserFormDto user) {
        if (!user.getPassword().equals(user.getVerifyPassword())) {
            errors.rejectValue(VERIFY_PASSWORD, "", PASSWORDS_MATCHING);
        }
    }
}