package by.epam.carrental.dao;

import by.epam.carrental.dao.exception.DAOException;
import by.epam.carrental.entity.Order;

import java.util.List;

/**
 * Интерфейс, описывающий получение, обновление, вставку
 * сущностей класса Order из/в базы(у) данных
 */
public interface OrderDAO {

    /**
     * Добавление заказа
     * @param order добавляемый заказ
     * @throws DAOException ошибка при добавлении заказа
     */
    public void addOrder(Order order) throws DAOException;

    /**
     * Поиск id автомобилей, которые заняты/забронированы в определенный
     * временной интервал
     * @param order заказ
     * @return список id автомобилей
     * @throws DAOException ошибка при получении списка id автомобилей
     */
    public List<Integer> takeOrderByOrder(Order order) throws DAOException;

    /**
     * Поиск заказов по id пользователя
     * @param userId id пользователя
     * @return список заказов
     * @throws DAOException ошибка при получении списка заказов
     */
    public List<Order> findOrdersByUserId(int userId) throws DAOException;

    /**
     * Поиск заказов по id заказа
     * @param orderId id заказа
     * @return искомый заказ
     * @throws DAOException ошибка при поиске заказа
     */
    public Order findOrderByOrderId(int orderId) throws DAOException;

    /**
     * Получение всех заказов
     * @return список всех заказов
     * @throws DAOException ошибка при получении списка заказов
     */
    public List<Order> takeAllOrders() throws DAOException;

    /**
     * Получение заказа по id заказа, для представления его админу
     * @param orderId id заказа
     * @return искомый заказ
     * @throws DAOException ошибка при поиске заказа
     */
    public Order takeAdminOrderByOrderId(int orderId) throws DAOException;

    /**
     * Изменеие статуса заказа, без указания причины
     * @param status новый статус заказа
     * @param orderId id изменяемого заказа
     * @throws DAOException ошибка при изменении заказа
     */
    public void updateStatusById(String status, int orderId) throws DAOException;

    /**
     * Изменение статуса заказа, с указанием причины изменения
     * @param status новый статус заказа
     * @param orderId id изменяемого заказа
     * @param orderInfo причина изменения статуса заказа
     * @throws DAOException ошибка при изменении статуса заказа
     */
    public void updateStatusById(String status, int orderId, String orderInfo) throws DAOException;

    /**
     * Измение стоимости ущерба
     * @param orderId id заказа
     * @param damagePrice стоимость ущерба
     * @throws DAOException ошибка при изменении стоимости ущерба
     */
    public void updateDamagePriceByOrderId(int orderId, double damagePrice) throws DAOException;

    /**
     * Изменение/установка даты и времени доставки автомобиля поьзователю
     * @param orderId id заказа
     * @param date дата и время доставки автомомбиля пользователю
     * @throws DAOException шибка при изменениии/установке даты и времени
     * доставки автомомбиля пользователю
     */
    public void updateRealTimeFrom(int orderId, String date) throws DAOException;

    /**
     * Изменение/установка даты и времени возврата автомобиля поьзователем
     * @param orderId id заказа
     * @param date дата и время возврата автоммобиля пользователем
     * @throws DAOException ошибка при изменении/установке даты и времени
     * возврата автомобиля пользователю
     */
    public void updateRealTimeTo(int orderId, String date) throws DAOException;
}
