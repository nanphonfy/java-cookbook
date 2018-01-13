package cn.sibat.boot.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller公共组件
 */
public abstract class AbstractController {
    protected Logger logger = LoggerFactory.getLogger(getClass());
}
