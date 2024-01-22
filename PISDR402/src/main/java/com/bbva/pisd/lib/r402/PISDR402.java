package com.bbva.pisd.lib.r402;

import java.util.List;
import java.util.Map;

/**
 * The  interface PISDR402 class...
 */
public interface PISDR402 {

	/**
	 * The execute method...
	 */
	void execute();

	Map<String, Object> executeGetASingleRow(String queryId, Map<String, Object> arguments);

	List<Map<String, Object>> executeGetListASingleRow(String queryId, Map<String, Object> arguments);

	int executeInsertSingleRow(String queryId, Map<String, Object> arguments, String... requiredParameters);




}
