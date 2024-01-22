package com.bbva.pisd.lib.r402.impl;

import com.bbva.apx.exception.db.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * The PISDR402Impl class...
 */
public class PISDR402Impl extends PISDR402Abstract {

	private static final Logger LOGGER = LoggerFactory.getLogger(PISDR402Impl.class);

	/**
	 * The execute method...
	 */
	@Override
	public void execute() {
		//method is empty because of tests

	}
	
	@Override
	public Map<String, Object> executeGetASingleRow(String queryId, Map<String, Object> arguments) {
		LOGGER.info("***** PISDR402Impl - executeGetASingleRow START *****");
		LOGGER.info("***** PISDR402Impl - executeGetASingleRow | Executing {} QUERY", queryId);
		try {
			Map<String, Object> response = this.jdbcUtils.queryForMap(queryId, arguments);
			response.forEach((key, value) -> LOGGER.info("Column -> {} with value: {}", key,value));
			LOGGER.info("***** PISDR402Impl - executeGetASingleRow END *****");
			return response;
		} catch (NoResultException ex) {
			LOGGER.info("executeGetASingleRow - There wasn't no result in query {}. Reason -> {}", queryId, ex.getMessage());
			return null;
		}
	}

}
