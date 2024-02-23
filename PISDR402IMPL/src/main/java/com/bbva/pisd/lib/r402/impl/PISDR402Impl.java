package com.bbva.pisd.lib.r402.impl;

import com.bbva.apx.exception.db.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Map;
import java.util.List;
import java.util.Arrays;

import static java.util.Objects.nonNull;

/**
 * The PISDR402Impl class...
 */
public class PISDR402Impl extends PISDR402Abstract {

	private static final Logger LOGGER = LoggerFactory.getLogger(PISDR402Impl.class);

	/**
	 * The execute method...
	 */

	@Override
	public int executeInsertSingleRow(String queryId, Map<String, Object> arguments, String... requiredParameters) {
		LOGGER.info("***** PISDR402Impl - insertSingleRow START *****");
		LOGGER.info("***** PISDR402Impl - insertSingleRow - EXECUTING {} QUERY ... *****", queryId);
		int affectedRows = 0;
		if(parametersEvaluation(arguments, requiredParameters)) {
			LOGGER.info("***** PISDR402Impl - insertSingleRow - PARAMETERS OK ... EXECUTING *****");
			try {
				affectedRows = this.jdbcUtils.update(queryId, arguments);
			} catch (NoResultException ex) {
				LOGGER.info("***** PISDR402Impl - {} database exception: {} *****", queryId, ex.getMessage());
				affectedRows = -1;
			}
		} else {
			LOGGER.info("insertSingleRow - MISSING MANDATORY PARAMETERS {}", queryId);
		}
		LOGGER.info("***** PISDR402Impl - insertSingleRow | Number of inserted rows: {} *****", affectedRows);
		LOGGER.info("***** PISDR402Impl - insertSingleRow END *****");
		return affectedRows;
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

	@Override
	public List<Map<String, Object>>executeGetListASingleRow(String queryId, Map<String, Object> arguments) {
		LOGGER.info("***** PISDR402Impl - executeGetListASingleRow START *****");
		LOGGER.info("***** PISDR402Impl - executeGetListASingleRow | Executing {} QUERY", queryId);
		try {
			List<Map<String, Object>> response = this.jdbcUtils.queryForList(queryId, arguments);
			LOGGER.info("***** PISDR402Impl - executeGetListASingleRow | Number of listed rows: {} *****", response.size());
			LOGGER.info("***** PISDR402Impl - executeGetListASingleRow END *****");
			return response;
		} catch (NoResultException ex) {
			LOGGER.info("executeGetListASingleRow - There wasn't no result in query {}. Reason -> {}", queryId, ex.getMessage());
			return null;
		}
	}

	private boolean parametersEvaluation(Map<String, Object> arguments, String... keys) {
		return Arrays.stream(keys).allMatch(key -> nonNull(arguments.get(key)));
	}

}
