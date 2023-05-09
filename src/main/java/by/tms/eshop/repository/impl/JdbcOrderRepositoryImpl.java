package by.tms.eshop.repository.impl;

import by.tms.eshop.dto.OrderDto;
import by.tms.eshop.mapper.OrderDtoMapper;
import by.tms.eshop.model.Order;
import by.tms.eshop.model.OrderProduct;
import by.tms.eshop.model.Product;
import by.tms.eshop.repository.JdbcOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static by.tms.eshop.utils.RepositoryJdbcUtils.getOrder;
import static by.tms.eshop.utils.RepositoryJdbcUtils.getOrderProduct;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional
public class JdbcOrderRepositoryImpl implements JdbcOrderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    //    private static final String CREATE_ORDER = "insert into orders (id, date, user_id) values (?, ?, ?)";
//    private static final String SAVE_PRODUCT_IN_ORDER = "insert into order_products (order_id, product_id) values (?, ?)";
//    private static final String GET_ORDERS_BY_ID = "SELECT o.id, o.date, p.name, p.info, p.price, pc.category " +
//            "FROM Order o " +
//            "JOIN OrderProduct oc " +
//            "JOIN Product p " +
//            "JOIN ProductCategory pc " +
//            "WHERE o.user.id = :userId";
    private static final String GET_ORDERS_BY_ID = "select o.id, o.date, p.name, p.info, p.price, pc.category from orders o " +
            "join order_products oc on o.id = oc.order_id " +
            "join products p on p.id = oc.product_id " +
            "join product_category pc on pc.id = p.product_category_id where user_id=?";
    private static final String CHECK_ORDERS_NAME = "FROM Order o WHERE o.id = :name";
//    private static final String GET_ORDERS_NUMBER = "select id from orders where id=?";

    //    @Override
//    public void createOrder(String order, Long id) {
//        jdbcTemplate.update(CREATE_ORDER, order, Date.valueOf(LocalDate.now()), id);
//    }
//    @Transactional
    @Override
    public void createOrder(String name, Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(getOrder(name, id));
            tx.commit();
        }
    }

    //    @Override
//    public void saveProductInOrderConfigurations(String order, Product product) {
//        jdbcTemplate.update(SAVE_PRODUCT_IN_ORDER, order, product.getId());
//    }
//    @Transactional
    @Override
    public void saveProductInOrderConfigurations(String name, Product product) {
        OrderProduct orderProduct = getOrderProduct(name, product);
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(orderProduct);
            tx.commit();
        }
    }

    @Override
    public List<OrderDto> getOrdersById(Long id) {
        return jdbcTemplate.query(GET_ORDERS_BY_ID, new OrderDtoMapper(), id);
    }

//    @Override
//    public List<OrderDto> getOrdersById(Long id) {
//        try (Session session = sessionFactory.openSession()) {
//            return session.createQuery(GET_ORDERS_BY_ID, OrderDto.class)
//                    .setParameter("userId", id)
//                    .getResultList();
//        }
//    }

//    @Override
//    public boolean checkOrderNumber(String number) {
//        return jdbcTemplate.query(CHECK_ORDERS_NAME, new OrderIdMapper(), number).stream()
//                .anyMatch(order -> order.getId().equals(number));
//    }

    @Override
    public boolean checkOrderNumber(String name) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(CHECK_ORDERS_NAME, Order.class)
                    .setParameter("name", name)
                    .getResultList().stream()
                    .anyMatch(order -> order.getId().equals(name));
//
//            return session.createQuery(CHECK_ORDERS_NAME, boolean.class)
//                    .setParameter("name", name)
//                    .getSingleResult();
        }
    }

}