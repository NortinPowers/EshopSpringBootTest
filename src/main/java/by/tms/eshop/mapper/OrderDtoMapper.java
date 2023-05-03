package by.tms.eshop.mapper;

import by.tms.eshop.dto.OrderDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static by.tms.eshop.utils.RepositoryJdbcUtils.getProductSimpleBuild;

public class OrderDtoMapper implements RowMapper<OrderDto> {

    @Override
    public OrderDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return OrderDto.builder()
                .id(rs.getString("id"))
                .date(LocalDate.parse(rs.getString("date")))
                .productDto(getProductSimpleBuild(rs))
                .build();
    }
}