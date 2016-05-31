package by.epam.carrental.service;

import by.epam.carrental.dao.DAOFactory;
import by.epam.carrental.dao.OrderDAO;
import by.epam.carrental.dao.exception.DAOException;
import by.epam.carrental.entity.Order;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 *
 * <p>
 *     Класс OrderService для выполнения некоторых логических действий с заказами.
 * </p>
 */
public class OrderService {

    private static final Logger LOG = LogManager.getLogger(OrderService.class.getName());

    private static final OrderService instance = new OrderService();

    private OrderService() {

    }

    public static OrderService getInstance() {
        return instance;
    }

    /**
     * Предача данных на DAO для вставки заказа
     * @param userId id пользователя, оформившего данный заказ
     * @param carId id заказанного автомобиля
     * @param supposedDateFrom планируемая дата получения автомобиля пользователем
     * @param supposedDateTo планируемая дата возврата автомобиля пользователем
     * @param shippingPlace место доставки автомобиля для пользователя
     * @param returnPlace место возврата автомобиля поьзователем
     * @return true - заказ добавлен, false - заказ не добавлен
     * @throws ServiceException ошибка при вставке заказа в базу данных
     */
    public boolean addOrder(int userId, int carId, String supposedDateFrom, String supposedDateTo,
                            String shippingPlace, String returnPlace) throws ServiceException {
        Order order = new Order();
        order.setUserId(userId);
        order.setCarId(carId);
        order.setSupposedDateFrom(supposedDateFrom);
        order.setSupposedDateTo(supposedDateTo);
        order.setShippingPlace(shippingPlace);
        order.setReturnPlace(returnPlace);
        DAOFactory factory = DAOFactory.getInstance();
        OrderDAO orderDAO = factory.getOrderDAO();
        try {
            List<Integer> carIds = orderDAO.takeOrderByOrder(order);
            boolean carUsed = false;
            for (Integer id : carIds) {
                if (id == carId) {
                    carUsed = true;
                    break;
                }
            }
            if (!carUsed) {
                orderDAO.addOrder(order);
                return true;
            } else {
                return false;
            }
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Передача данных на DAO для поиска заказа по id пользователя, оформившего заказ
     * @param userId id пользователя
     * @return список закзов, оформленных определенным пользователем
     * @throws ServiceException ошибка при поиске заказов
     */
    public List<Order> findOrdersByUserId(int userId) throws ServiceException {
        LOG.debug("OrderService : findOrdersByUserId");
        List<Order> orders = null;
        DAOFactory daoFactory = DAOFactory.getInstance();
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        try {
            orders = orderDAO.findOrdersByUserId(userId);
            return orders;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Передача данных на DAO дя поиска заказа по его id
     * @param orderId id закза
     * @return заказ
     * @throws ServiceException ошибка при поиске заказа
     */
    public Order findOrderByOrderId(int orderId) throws ServiceException {
        LOG.debug("OrderService : findOrdersByUserId");
        Order order = null;
        DAOFactory daoFactory = DAOFactory.getInstance();
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        try {
            order = orderDAO.findOrderByOrderId(orderId);
            return order;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Передача данных на DAO для получения списка всех закзов
     * @return список всех заказов
     * @throws ServiceException ошибка при получении списков всех заказов
     */
    public List<Order> takeAllOrders() throws ServiceException {
        LOG.debug("OrderService : takeAllOrders");
        List<Order> orders = null;
        DAOFactory daoFactory = DAOFactory.getInstance();
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        try {
            orders = orderDAO.takeAllOrders();
            return orders;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Передача данных на DAO для получения заказа для представления его админу
     * @param orderId id заказа
     * @return заказ
     * @throws ServiceException ошибка при получении заказа
     */
    public Order takeAdminOrderByOrderId(int orderId) throws ServiceException {
        LOG.debug("OrderService : takeAdminOrderByOrderId");
        Order order = null;
        DAOFactory daoFactory = DAOFactory.getInstance();
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        try {
            order = orderDAO.takeAdminOrderByOrderId(orderId);
            return order;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Передача данных на DAO для изменения статуса заказа
     * @param status новый статус заказа
     * @param orderId id заказа
     * @throws ServiceException ошибка при изменении статуса заказа
     */
    public void updateStatusById(String status, int orderId) throws ServiceException {
        LOG.debug("OrderService : updateStatusById : starts");
        DAOFactory daoFactory = DAOFactory.getInstance();
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        try {
            orderDAO.updateStatusById(status, orderId);
            LOG.debug("OrderService : updateStatusById : ends");
        } catch (DAOException ex) {
            LOG.error("OrderService : updateStatusById : ERROR");
            throw new ServiceException(ex);
        }
    }

    /**
     * Передача данных на DAO для изменения статуса заказа и указания причины смены заказа
     * @param status статус заказа
     * @param orderId id заказа
     * @param orderInfo причина смены статуса заказа
     * @throws ServiceException ошибка при изменении статуса заказа
     */
    public void updateStatusById(String status, int orderId, String orderInfo) throws ServiceException {
        LOG.debug("OrderService : updateStatusById");
        DAOFactory daoFactory = DAOFactory.getInstance();
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        try {
            orderDAO.updateStatusById(status, orderId, orderInfo);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /*public void updateRealDateByOrderId(String realDateFrom, String realDateTo, int orderId) throws ServiceException {
        LOG.debug("OrderService : updateRealDateByOrderId");
        DAOFactory daoFactory = DAOFactory.getInstance();
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        try {
            orderDAO.updateRealDateByOrderId(realDateFrom, realDateTo, orderId);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }*/

    /**
     * Передача данных на DAO для установления/обновления стоимости ущерба, нанесенного пользователем
     * @param orderId id заказа
     * @param damagePrice стоимость ущерба
     * @throws ServiceException ошибка при изменении стоимости ущерба
     */
    public void updateDamagePriceByOrderId(int orderId, double damagePrice) throws ServiceException {
        LOG.debug("OrderService : updateDamagePriceByOrderId");
        DAOFactory daoFactory = DAOFactory.getInstance();
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        try {
            orderDAO.updateDamagePriceByOrderId(orderId, damagePrice);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Передача данных на DAO для установления времени, когда пользователь взял машину в пользование
     * @param orderId id заказа
     * @param date дата и время, когда пользователь взял машину в пользование
     * @throws ServiceException ошибка при установке даты и времени
     */
    public void updateRealTimeFrom(int orderId, String date) throws ServiceException {
        LOG.debug("OrderService : updateRealTimeFrom");
        DAOFactory daoFactory = DAOFactory.getInstance();
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        try {
            orderDAO.updateRealTimeFrom(orderId, date);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Передача данных на DAO для установления времени, когда пользователь вернул машину
     * @param orderId id заказа
     * @param date дата и время, когда пользователь вернул машину
     * @throws ServiceException ошибка при установке даты и времени
     */
    public void updateRealTimeTo(int orderId, String date) throws ServiceException {
        LOG.debug("OrderService : updateRealTimeTo");
        DAOFactory daoFactory = DAOFactory.getInstance();
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        try {
            orderDAO.updateRealTimeTo(orderId, date);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }
}
