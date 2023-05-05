package by.tms.eshop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class User {

    private Long id;
    private String password;
    private String login;
    private String name;
    private String surname;
    private String email;
    private LocalDate birthday;
}