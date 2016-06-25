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

    private static final String PAYED_VALUE = "payed";
    private static final String NEW_VALUE = "new";
    private static final String DELIVERED_VALUE = "delivered";

    private final OrderDAO ORDER_DAO = DAOFactory.getInstance().getOrderDAO();

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
        LOG.debug(ServiceConstant.SERVICE_ADD_ORDER_STARTS_MSG);
        Order order = new Order();
        order.setUserId(userId);
        order.setCarId(carId);
        order.setSupposedDateFrom(supposedDateFrom);
        order.setSupposedDateTo(supposedDateTo);
        order.setShippingPlace(shippingPlace);
        order.setReturnPlace(returnPlace);
        try {
            List<Integer> carIds = ORDER_DAO.takeOrderByOrder(order);
            boolean carUsed = false;
            for (Integer id : carIds) {
                if (id == carId) {
                    carUsed = true;
                    break;
                }
            }
            if (!carUsed) {
                ORDER_DAO.addOrder(order);
                LOG.debug(ServiceConstant.SERVICE_ADD_ORDER_ENDS_MSG);
                return true;
            } else {
                LOG.debug(ServiceConstant.SERVICE_ADD_ORDER_ENDS_MSG);
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
    public List<Order> findOrdersByUserId(int userId, int pageNumber, int ordersOnPage) throws ServiceException {
        LOG.debug(ServiceConstant.SERVICE_FIND_ORSERS_BY_USER_STARTS_MSG);
        List<Order> orders = null;
        int startPage = orderToStartPage(pageNumber, ordersOnPage);
        try {
            orders = ORDER_DAO.findOrdersByUserId(userId, startPage, ordersOnPage);
            LOG.debug(ServiceConstant.SERVICE_FIND_ORSERS_BY_USER_ENDS_MSG);
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
        LOG.debug(ServiceConstant.SERVICE_FIND_ORDER_BY_ID_STARTS_MSG);
        Order order = null;
        try {
            order = ORDER_DAO.findOrderByOrderId(orderId);
            LOG.debug(ServiceConstant.SERVICE_FIND_ORDER_BY_ID_ENDS_MSG);
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
    public List<Order> takeAllOrders(int pageNumber, int ordersOnPage) throws ServiceException {
        LOG.debug(ServiceConstant.SERVICE_TAKE_ALL_ORDERS_STARTS_MSG);
        List<Order> orders = null;
        int startPage = orderToStartPage(pageNumber, ordersOnPage);
        try {
            orders = ORDER_DAO.takeAllOrders(startPage, ordersOnPage);
            LOG.debug(ServiceConstant.SERVICE_TAKE_ALL_ORDERS_ENDS_MSG);
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
        LOG.debug(ServiceConstant.SERVICE_TAKE_ADMIN_ORDER_BY_ID_STARTS_MSG);
        Order order = null;
        try {
            order = ORDER_DAO.takeAdminOrderByOrderId(orderId);
            LOG.debug(ServiceConstant.SERVICE_TAKE_ADMIN_ORDER_BY_ID_ENDS_MSG);
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
        LOG.debug(ServiceConstant.SERVICE_UPDATE_STATUS_BY_ID_STARTS_MSG);
        try {
            ORDER_DAO.updateStatusById(status, orderId);
            LOG.debug(ServiceConstant.SERVICE_UPDATE_STATUS_BY_ID_ENDS_MSG);
        } catch (DAOException ex) {
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
        LOG.debug(ServiceConstant.SERVICE_UPDATE_STATUS_BY_ID_STARTS_MSG);
        try {
            ORDER_DAO.updateStatusById(status, orderId, orderInfo);
            LOG.debug(ServiceConstant.SERVICE_UPDATE_STATUS_BY_ID_ENDS_MSG);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }


    /**
     * Передача данных на DAO для установления/обновления стоимости ущерба, нанесенного пользователем
     * @param orderId id заказа
     * @param damagePrice стоимость ущерба
     * @throws ServiceException ошибка при изменении стоимости ущерба
     */
    public void updateDamagePriceByOrderId(int orderId, double damagePrice) throws ServiceException {
        LOG.debug(ServiceConstant.SERVICE_UPDATE_DAMAGE_PRICE_BY_ID_STARTS_MSG);
        try {
            ORDER_DAO.updateDamagePriceByOrderId(orderId, damagePrice);
            LOG.debug(ServiceConstant.SERVICE_UPDATE_DAMAGE_PRICE_BY_ID_ENDS_MSG);
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
        LOG.debug(ServiceConstant.SERVICE_UPDATE_REAL_TIME_FROM_STARTS_MSG);
        try {
            ORDER_DAO.updateRealTimeFrom(orderId, date);
            LOG.debug(ServiceConstant.SERVICE_UPDATE_REAL_TIME_FROM_ENDS_MSG);
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
        LOG.debug(ServiceConstant.SERVICE_UPDATE_REAL_TIME_TO_STARTS_MSG);
        try {
            ORDER_DAO.updateRealTimeTo(orderId, date);
            LOG.debug(ServiceConstant.SERVICE_UPDATE_REAL_TIME_TO_ENDS_MSG);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public int countPageAmountUserOrders(int userId, int amountOrdersOnPage) throws ServiceException {
        LOG.debug(ServiceConstant.SERVICE_COUNT_PAGE_AMOUNT_USER_ORDERS_STARTS_MSG);
        int pageAmount = 0;
        int ordersAmount = 0;
        try {
            ordersAmount = ORDER_DAO.countUserOrders(userId);
            if (ordersAmount % amountOrdersOnPage != 0) {
                pageAmount = (ordersAmount / amountOrdersOnPage) + 1;
            } else {
                pageAmount = (ordersAmount / amountOrdersOnPage);
            }
            LOG.debug(ServiceConstant.SERVICE_COUNT_PAGE_AMOUNT_USER_ORDERS_ENDS_MSG);
            return pageAmount;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public int countPageAmountAllOrders(int amountOrdersOnPage) throws ServiceException {
        LOG.debug(ServiceConstant.SERVICE_COUNT_PAGE_AMOUNT_ALL_ORDERS_STARTS_MSG);
        int pageAmount = 0;
        int ordersAmount = 0;
        try {
            ordersAmount = ORDER_DAO.countAllOrders();
            if (ordersAmount % amountOrdersOnPage != 0) {
                pageAmount = (ordersAmount / amountOrdersOnPage) + 1;
            } else {
                pageAmount = (ordersAmount / amountOrdersOnPage);
            }
            LOG.debug(ServiceConstant.SERVICE_COUNT_PAGE_AMOUNT_ALL_ORDERS_ENDS_MSG);
            return pageAmount;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public boolean isMayDelete(int carId) throws ServiceException {
        LOG.debug(ServiceConstant.SERVICE_IS_MAY_DELETE_STARTS_MSG);
        List<String> orderStatuses = null;
        try {
            orderStatuses = ORDER_DAO.findStatusByCarId(carId);
            for (String orderStatus : orderStatuses) {
                if (orderStatus.equals(NEW_VALUE) || orderStatus.equals(PAYED_VALUE) || orderStatus.equals(DELIVERED_VALUE)) {
                    LOG.debug(ServiceConstant.SERVICE_IS_MAY_DELETE_ENDS_MSG);
                    return false;
                }
            }
            LOG.debug(ServiceConstant.SERVICE_IS_MAY_DELETE_ENDS_MSG);
            return true;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    private int orderToStartPage(int pageNumber, int ordersOnPage) {
        LOG.debug(ServiceConstant.SERVICE_ORDER_TO_START_PAGE_STARTS_MSG);
        LOG.debug(ServiceConstant.SERVICE_ORDER_TO_START_PAGE_ENDS_MSG);
        return ((pageNumber * ordersOnPage) - ordersOnPage);
    }
}
