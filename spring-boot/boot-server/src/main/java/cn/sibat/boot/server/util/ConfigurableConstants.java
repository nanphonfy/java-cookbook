package cn.sibat.boot.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 针对config.properties的配置文件读取类
 */
public final class ConfigurableConstants {
    private final static Logger logger = LoggerFactory.getLogger(ConfigurableConstants.class);

    private static Properties confProperties = new Properties();
    private static String propertyFileName = "application.properties";

    public final static String SPRING_PROFILES_ACTIVE = "spring.profiles.active";
    public final static String EXCEL_TEMPLATE_PATH = "excel.template.path";
    public final static String EXCEL_TEMPLATE_EXCEL = "excel.template.excel";
    public final static String EXCEL_TMP_PATH = "excel.tmp.path";

    /**
     * 静态读入属性文件到Properties p变量中
     */
    static {
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = ConfigurableConstants.class.getClassLoader().getResourceAsStream(propertyFileName);
            if (in != null) {
                properties.load(in);
                //后缀
                String activeSuffix = properties.getProperty(SPRING_PROFILES_ACTIVE);

                in = ConfigurableConstants.class.getClassLoader()
                        .getResourceAsStream("application-" + activeSuffix + ".properties");
                confProperties.load(in);
            }
        } catch (IOException e) {
            logger.error("初始化配置文件 " + propertyFileName + " 到 Constants 发生错误,请检查相关配置文件!" + e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("close " + propertyFileName + " error!");
                }
            }
        }
    }

    /**
     * 封装了Properties类的getProperty函数,使p变量对子类透明.
     *
     * @param key          property key.
     * @param defaultValue 当使用property key在properties中取不到值时的默认值.
     * @return 属性值
     */
    public static String getProperty(String key, String defaultValue) {
        if (confProperties.getProperty(key) == null) {
            return defaultValue;
        } else {
            return confProperties.getProperty(key);
        }
    }

    /**
     * 封装了Properties类的getProperty函数,使p变量对子类透明.
     *
     * @param key property key.
     * @return 属性值
     */
    public static String getProperty(String key) {
        return getProperty(key, "");
    }
}
