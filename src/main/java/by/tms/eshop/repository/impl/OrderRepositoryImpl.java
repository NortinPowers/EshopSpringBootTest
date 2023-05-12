package by.tms.eshop.repository.impl;

import by.tms.eshop.dto.OrderDto;
import by.tms.eshop.mapper.OrderDtoMapper;
import by.tms.eshop.model.Order;
import by.tms.eshop.model.OrderProduct;
import by.tms.eshop.model.Product;
import by.tms.eshop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static by.tms.eshop.utils.Constants.QueryParameter.NAME;
import static by.tms.eshop.utils.RepositoryJdbcUtils.getOrder;
import static by.tms.eshop.utils.RepositoryJdbcUtils.getOrderProduct;

@Slf4j
@Repository
@RequiredArgsConstructor
//@Transactional
public class OrderRepositoryImpl implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    private static final String GET_ORDERS_BY_ID = "select o.id, o.date, p.name, p.info, p.price, pc.category from orders o " +
            "join order_products oc on o.id = oc.order_id " +
            "join products p on p.id = oc.product_id " +
            "join product_category pc on pc.id = p.product_category_id where user_id=?";
    private static final String CHECK_ORDERS_NAME = "FROM Order WHERE id = :name";

    @Override
    public void createOrder(String name, Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(getOrder(name, id));
    }

    @Override
    public void saveProductInOrderConfigurations(String name, Product product) {
        OrderProduct orderProduct = getOrderProduct(name, product);
        Session session = sessionFactory.getCurrentSession();
        session.persist(orderProduct);
    }

    @Override
    public List<OrderDto> getOrdersById(Long id) {
        return jdbcTemplate.query(GET_ORDERS_BY_ID, new OrderDtoMapper(), id);
    }


//          Это работает, но я считаю что jdbcTemplate с мапером проще и лучше, чем трансформировать
//          данные возвращенного массива через анонимный интерфейс в объект. Метод проще не нагуглил
//          (оставил себе для примера, потом удалю)

//    @Override
//    public List<OrderDto> getOrdersById(Long id) {
//        Session session = sessionFactory.getCurrentSession();
//        return session.createNativeQuery(GET_ORDERS_BY_ID)
//                .setParameter(1, id)
//                .unwrap(NativeQuery.class)
//                .setResultTransformer(new ResultTransformer() {
//                    @Override
//                    public Object transformTuple(Object[] tuple, String[] aliases) {
//                        OrderDto orderDto = OrderDto.builder()
//                                .id((String) tuple[0])
//                                .date(((java.sql.Date) tuple[1]).toLocalDate())
//                                .productDto(ProductDto.builder()
//                                        .name((String) tuple[2])
//                                        .info((String) tuple[3])
//                                        .price((BigDecimal) tuple[4])
//                                        .category((String) tuple[5])
//                                        .build())
//                                .build();
//                        return orderDto;
//                    }
//                    @Override
//                    public List transformList(List collection) {
//                        return collection;
//                    }
//                })
//                .getResultList();
//    }

    @Override
    public boolean checkOrderNumber(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(CHECK_ORDERS_NAME, Order.class)
                .setParameter(NAME, name)
                .getResultList().stream()
                .anyMatch(order -> order.getId().equals(name));
    }
}