package cn.sibat.boot.server.util;

public class Status {
	public static final int OK = 200;// OK
	public static final int PARA_ERROR = 400;// 服务器不理解请求的语法
	public static final int AUTH_ERROR = 401;// 未授权
	public static final int NOT_FOUND = 404;// 服务器找不到请求的网页
	public static final int DUPLICATED = 409;// 服务器在完成请求时发生冲突
	public static final int SYS_ERROR = 500;// 服务器遇到错误，无法完成请求
	public static final int OTHER_ERROR = 444;// 被使用在Nginx的日志中表明服务器没有返回信息给客户端并且关闭了连接（在威慑恶意软件的时候比较有用）
}
