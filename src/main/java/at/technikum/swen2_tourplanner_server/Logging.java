package at.technikum.swen2_tourplanner_server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Logging {

    protected final Logger logger;

    public Logging() {
        this.logger = LoggerFactory.getLogger(Logging.class);
    }

}
