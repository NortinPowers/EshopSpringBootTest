package by.tms.eshop.repository.impl;

import by.tms.eshop.domain.Order;
import by.tms.eshop.domain.Product;
import by.tms.eshop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static by.tms.eshop.utils.Constants.QueryParameter.NAME;
import static by.tms.eshop.utils.RepositoryJdbcUtils.getOrder;

@Slf4j
@Repository
@RequiredArgsConstructor
//@Transactional
public class OrderRepositoryImpl implements OrderRepository {

//    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

//    private static final String GET_ORDERS_BY_ID = "select o.id, o.date, p.name, p.info, p.price, pc.category from orders o " +
//            "join order_products oc on o.id = oc.order_id " +
//            "join products p on p.id = oc.product_id " +
//            "join product_category pc on pc.id = p.product_category_id where user_id=?";
    private static final String CHECK_ORDERS_NAME = "FROM Order WHERE name = :name";

//    @Override
//    public void createOrder(String name, Long id) {
//        Session session = sessionFactory.getCurrentSession();
//        session.persist(getOrder(name, id));
//    }

    @Override
    public Long createOrder(String name, Long id) {
        Session session = sessionFactory.getCurrentSession();
        Order order = getOrder(name, id);
        session.persist(order);
        return order.getId();
    }

//    @Override
//    public void saveProductInOrderConfigurations(String name, Product product) {
//        OrderProduct orderProduct = getOrderProduct(name, product);
//        Session session = sessionFactory.getCurrentSession();
//        session.persist(orderProduct);
//    }

    @Override
    public void saveProductInOrderConfigurations(Long id, List<Product> products) {
        Session session = sessionFactory.getCurrentSession();
        Order order = session.get(Order.class, id);
        order.setProducts(products);
        products.forEach(product -> product.setOrders(new ArrayList<>(List.of(order))));
    }

    @Override
    public List<Order> getOrdersById(Long id) {
//    public List<OrderDto> getOrdersById(Long id) {
//        String GET_ORDERS_BY_IDs = "select o.id, o.date, p.name, p.info, p.price, pc.category from orders o " +
//                "join order_products oc on o.id = oc.order_id " +
//                "join products p on p.id = oc.product_id " +
//                "join product_category pc on pc.id = p.product_category_id where user_id=?";
//        String GET_NEW = "FROM Order WHERE user.id = :id";
//        return jdbcTemplate.query(GET_ORDERS_BY_ID, new OrderDtoMapper(), id);
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Order WHERE user.id = :userId", Order.class)
                .setParameter("userId", id)
                .getResultList();
//        List<OrderDto> orderDtos;
//        for (Order order : orders) {
//            OrderDto.builder()
//                    .id(order.getName())
//                    .date(order.getDate())
//                    .userId(order.getUser().getId())
//                    .productDto(ProductDto.builder()
//                            .name(order.).build())
//        }

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
                .anyMatch(order -> order.getName().equals(name));
    }
}