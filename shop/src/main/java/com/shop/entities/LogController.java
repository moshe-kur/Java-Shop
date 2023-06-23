package com.shop.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



//@RestController
public class LogController {

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);
//    Logger logger = LoggerFactory.getLogger(LogController.class);

    //@RequestMapping("/")
    public void index(String message) {
        logger.trace(message);
        logger.debug(message);
        logger.info(message);
        logger.warn(message);
        logger.error(message);

        //return "Howdy! Check out the Logs to see the output...";
    }
}

