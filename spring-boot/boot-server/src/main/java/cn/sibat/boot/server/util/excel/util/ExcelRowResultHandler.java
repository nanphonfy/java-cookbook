package cn.sibat.boot.server.util.excel.util;

import java.util.List;

public interface ExcelRowResultHandler<T> {
	public T invoke(List<Object> list);
}
