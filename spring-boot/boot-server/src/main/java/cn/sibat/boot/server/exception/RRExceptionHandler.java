package cn.sibat.boot.server.exception;

import cn.sibat.boot.server.util.FastJsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午10:16:19
 */
@RestControllerAdvice
public class RRExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(RRException.class)
	public String handleRRException(RRException e){
		return FastJsonUtil.error(e.getMsg());
	}

	@ExceptionHandler(ExcelDataFormatException.class)
	public String handleExcelDataFormatException(ExcelDataFormatException e){
		return FastJsonUtil.error(e.getMessage());
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public String handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return FastJsonUtil.error("数据库中已存在该记录");
	}

	/*@ExceptionHandler(AuthorizationException.class)
	public R handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return R.error("没有权限，请联系管理员授权");
	}*/

	@ExceptionHandler(Exception.class)
	public String handleException(Exception e){
		logger.error(e.getMessage(), e);
		return FastJsonUtil.error(e.getMessage());
	}
}
