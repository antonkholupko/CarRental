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

    private static final String FIND_ORSERS_BY_USER_MSG = "OrderService : findOrdersByUserId";
    private static final String FIND_ORDER_BY_ID = "OrderService : findOrdersByOrderId";
    private static final String TAKE_ALL_ORDERS = "OrderService : takeAllOrders";
    private static final String TAKE_ADMIN_ORDER_BY_ID = "OrderService : takeAdminOrderByOrderId";
    private static final String UPDATE_STATUS_BY_ID_STARTS = "OrderService : updateStatusById : starts";
    private static final String UPDATE_STATUS_BY_ID_ENDS = "OrderService : updateStatusById : ends";
    private static final String UPDATE_STATUS_BY_ID_ERROR = "OrderService : updateStatusById : ERROR";
    private static final String UPDATE_DAMAGE_PRICE_BY_ID = "OrderService : updateDamagePriceByOrderId";
    private static final String UPDATE_REAL_TIME_FROM = "OrderService : updateRealTimeFrom";
    private static final String UPDATE_REAL_TIME_TO = "OrderService : updateRealTimeTo";
    private static final String ORDER_TO_START_PAGE = "OrderService : orderToStartPage";
    private static final String COUNT_PAGE_AMOUNT_USER_ORDERS = "OrderService : countPageAmountUserOrders";
    private static final String COUNT_PAGE_AMOUNT_ALL_ORDERS_MSG = "OrderService : countPageAmountAllCars";
    private static final String IS_MAY_DELETE_STARTS_MSG = "OrderService : isMayDelete : starts";
    private static final String IS_MAY_DELETE_ENDS_MSG = "OrderService : isMayDelete : ends";

    private static final String PAYED_VALUE = "payed";
    private static final String NEW_VALUE = "new";
    private static final String DELIVERED_VALUE = "delivered";

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
    public List<Order> findOrdersByUserId(int userId, int pageNumber, int ordersOnPage) throws ServiceException {
        LOG.debug(FIND_ORSERS_BY_USER_MSG);
        List<Order> orders = null;
        DAOFactory daoFactory = DAOFactory.getInstance();
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        int startPage = orderToStartPage(pageNumber, ordersOnPage);
        try {
            orders = orderDAO.findOrdersByUserId(userId, startPage, ordersOnPage);
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
        LOG.debug(FIND_ORDER_BY_ID);
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
    public List<Order> takeAllOrders(int pageNumber, int ordersOnPage) throws ServiceException {
        LOG.debug(TAKE_ALL_ORDERS);
        List<Order> orders = null;
        DAOFactory daoFactory = DAOFactory.getInstance();
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        int startPage = orderToStartPage(pageNumber, ordersOnPage);
        try {
            orders = orderDAO.takeAllOrders(startPage, ordersOnPage);
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
        LOG.debug(TAKE_ADMIN_ORDER_BY_ID);
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
        LOG.debug(UPDATE_STATUS_BY_ID_STARTS);
        DAOFactory daoFactory = DAOFactory.getInstance();
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        try {
            orderDAO.updateStatusById(status, orderId);
            LOG.debug(UPDATE_STATUS_BY_ID_ENDS);
        } catch (DAOException ex) {
            LOG.error(UPDATE_STATUS_BY_ID_ERROR);
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
        LOG.debug(UPDATE_STATUS_BY_ID_STARTS);
        DAOFactory daoFactory = DAOFactory.getInstance();
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        try {
            orderDAO.updateStatusById(status, orderId, orderInfo);
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
        LOG.debug(UPDATE_DAMAGE_PRICE_BY_ID);
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
        LOG.debug(UPDATE_REAL_TIME_FROM);
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
        LOG.debug(UPDATE_REAL_TIME_TO);
        DAOFactory daoFactory = DAOFactory.getInstance();
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        try {
            orderDAO.updateRealTimeTo(orderId, date);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public int countPageAmountUserOrders(int userId, int amountOrdersOnPage) throws ServiceException {
        LOG.debug(COUNT_PAGE_AMOUNT_USER_ORDERS);
        int pageAmount = 0;
        int ordersAmount = 0;
        DAOFactory factory = DAOFactory.getInstance();
        OrderDAO dao = factory.getOrderDAO();
        try {
            ordersAmount = dao.countUserOrders(userId);
            if (ordersAmount % amountOrdersOnPage != 0) {
                pageAmount = (ordersAmount / amountOrdersOnPage) + 1;
            } else {
                pageAmount = (ordersAmount / amountOrdersOnPage);
            }
            return pageAmount;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public int countPageAmountAllOrders(int amountOrdersOnPage) throws ServiceException {
        LOG.debug(COUNT_PAGE_AMOUNT_ALL_ORDERS_MSG);
        int pageAmount = 0;
        int ordersAmount = 0;
        DAOFactory factory = DAOFactory.getInstance();
        OrderDAO dao = factory.getOrderDAO();
        try {
            ordersAmount = dao.countAllOrders();
            if (ordersAmount % amountOrdersOnPage != 0) {
                pageAmount = (ordersAmount / amountOrdersOnPage) + 1;
            } else {
                pageAmount = (ordersAmount / amountOrdersOnPage);
            }
            return pageAmount;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public boolean isMayDelete(int carId) throws ServiceException {
        LOG.debug(IS_MAY_DELETE_STARTS_MSG);
        DAOFactory factory = DAOFactory.getInstance();
        OrderDAO dao = factory.getOrderDAO();
        List<String> orderStatuses = null;
        try {
            orderStatuses = dao.findStatusByCarId(carId);
            for (String orderStatus : orderStatuses) {
                if (orderStatus.equals(NEW_VALUE) || orderStatus.equals(PAYED_VALUE) || orderStatus.equals(DELIVERED_VALUE)) {
                    LOG.debug(IS_MAY_DELETE_ENDS_MSG);
                    return false;
                }
            }
            LOG.debug(IS_MAY_DELETE_ENDS_MSG);
            return true;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    private int orderToStartPage(int pageNumber, int ordersOnPage) {
        LOG.debug(ORDER_TO_START_PAGE);
        return ((pageNumber * ordersOnPage) - ordersOnPage);
    }
}
