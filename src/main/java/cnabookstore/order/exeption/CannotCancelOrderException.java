package cnabookstore.order.exeption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CannotCancelOrderException extends Exception {

    Logger logger = LoggerFactory.getLogger(CannotCancelOrderException.class);

    public CannotCancelOrderException(){
        super("Order can be canceled ONLY during ORDERED status!!!");
        logger.info("Biz Logic Exception. Order can be canceled ONLY during ORDERED status");
    }
}
