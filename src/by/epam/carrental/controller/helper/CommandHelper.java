package by.epam.carrental.controller.helper;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.CommandName;
import by.epam.carrental.command.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CommandHelper {

    private Map<CommandName, Command> commands = new HashMap<>();
    private static final Logger LOG = LogManager.getLogger(CommandHelper.class.getName());
    private final static CommandHelper instance = new CommandHelper();

    private CommandHelper() {
        LOG.debug("CommandHelper : init");
        commands.put(CommandName.TO_REGISTRATION, new ToRegPageCommand());
        commands.put(CommandName.LOGIN_USER, new LoginUserCommand());
        commands.put(CommandName.CHANGE_LOCALE, new ChangeLocalCommand());
        commands.put(CommandName.REGISTER, new RegisterUserCommand());
        commands.put(CommandName.LOG_OUT_USER, new LogOutCommand());
        commands.put(CommandName.VIEW_TYPE_BY_DATE, new ViewTypeByDateCommand());
        commands.put(CommandName.VIEW_CAR, new ViewCarCommand());
        commands.put(CommandName.TO_MAKE_ORDER, new ToMakeOrderCommand());
        commands.put(CommandName.MAKE_ORDER, new MakeOrderCommand());
        commands.put(CommandName.VIEW_ORDERS_USER, new ViewOrdersUserCommand());
        commands.put(CommandName.VIEW_ORDER_USER, new ViewOrderUserCommand());
        commands.put(CommandName.VIEW_ORDERS_ADMIN, new ViewOrdersAdminCommand());
        commands.put(CommandName.VIEW_ORDER_ADMIN, new ViewOrderAdminCommand());
        commands.put(CommandName.UPDATE_STATUS, new UpdateStatusCommand());
        commands.put(CommandName.UPDATE_REAL_DATE_FROM, new UpdateRealDateFromCommand());
        commands.put(CommandName.UPDATE_REAL_DATE_TO, new UpdateRealDateToCommand());
        commands.put(CommandName.UPDATE_DAMAGE_PRICE, new UpdateDamagePrice());
        commands.put(CommandName.VIEW_ALL_CARS, new ViewAllCarsCommand());
        commands.put(CommandName.TO_PRIV_OFFICE_ADMIN, new ToPrivateOfficeAdminCommand());
        commands.put(CommandName.TO_PRIV_OFFICE_USER, new ToPrivateOfficeUserCommand());
        commands.put(CommandName.TO_HOME_PAGE, new ToHomePageCommand());
        commands.put(CommandName.VIEW_ALL_UNUSED_CARS, new ViewAllUnusedCarsCommand());
        commands.put(CommandName.CANCEL_ORDER, new CancelOrderCommand());
        commands.put(CommandName.TO_PAYMENT_PAGE, new ToPaymentCommand());
        commands.put(CommandName.PAY, new PayCommand());
        commands.put(CommandName.PAY_FOR_DAMAGE, new PayForDamageCommand());
        commands.put(CommandName.VIEW_ALL_USERS, new ViewAllUsersCommand());
        commands.put(CommandName.VIEW_USER, new ViewUserCommand());
        commands.put(CommandName.TO_ADD_CAR, new ToAddCarCommand());
        commands.put(CommandName.TAKE_MODELS, new TakeModelsCommand());
        commands.put(CommandName.ADD_CAR, new AddCarCommand());
        commands.put(CommandName.DELETE_CAR, new DeleteCarCommand());
        commands.put(CommandName.VIEW_TYPE, new ViewTypeCommand());
    }

    public Command getCommand(String commandName) {
        Command command = null;
        CommandName key = null;

        commandName = commandName.replace('-', '_').toUpperCase();
        key = CommandName.valueOf(commandName);
        command = commands.get(key);

        return command;
    }

    public static CommandHelper getInstance() {
        return instance;
    }
}
