package by.tms.eshop.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@EqualsAndHashCode
@Builder
@Getter
public class SimpleOrderDto {

    private String id;
    private LocalDate date;
    private Long userId;
}