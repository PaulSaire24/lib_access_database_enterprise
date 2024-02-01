package com.bbva.pisd.lib.r402;

import com.bbva.apx.exception.db.NoResultException;
import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;
import com.bbva.elara.domain.transaction.Context;
import com.bbva.elara.domain.transaction.ThreadContext;
import javax.annotation.Resource;

import com.bbva.elara.utility.jdbc.JdbcUtils;
import com.bbva.pisd.lib.r402.impl.PISDR402Impl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/META-INF/spring/PISDR402-app.xml",
		"classpath:/META-INF/spring/PISDR402-app-test.xml",
		"classpath:/META-INF/spring/PISDR402-arc.xml",
		"classpath:/META-INF/spring/PISDR402-arc-test.xml" })
public class PISDR402Test {

	@Spy
	private Context context;

	private final PISDR402Impl pisdR402 = new PISDR402Impl();

	private JdbcUtils jdbcUtils;

	private static final Logger LOGGER = LoggerFactory.getLogger(PISDR402Test.class);

	@Mock
	private Map<String, Object> insertSingleRowArguments;



	@Resource(name = "applicationConfigurationService")
	private ApplicationConfigurationService applicationConfigurationService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		context = new Context();
		ThreadContext.set(context);
		jdbcUtils = mock(JdbcUtils.class);
		pisdR402.setJdbcUtils(jdbcUtils);
	}


	@Test
	public void executeInsertSingleRow_WithNoResultException() {
		LOGGER.info("PISDR402Test - Executing executeInsertSingleRow_WithNoResultException...");

		when(insertSingleRowArguments.get("firstKey")).thenReturn("anyValue");

		when(jdbcUtils.update("anyQueryId", insertSingleRowArguments)).thenThrow(new NoResultException("adviceCode", "errorMessage"));

		int validation = pisdR402.executeInsertSingleRow("anyQueryId", insertSingleRowArguments, "firstKey");

		assertEquals(-1, validation);
	}

	@Test
	public void executeInsertSingleRow_WithMissingMandatoryParameters() {
		LOGGER.info("PISDR402Test - Executing executeInsertSingleRow_WithMissingMandatoryParameters...");

		int validation = pisdR402.executeInsertSingleRow("anyQueryId", insertSingleRowArguments, "firstKey");

		assertEquals(0, validation);
	}

	@Test
	public void executeGetASingleRow_OK() {
		LOGGER.info("PISDR402Test - Executing executeGetASingleRow_OK...");

		Map<String, Object> response = new HashMap<>();
		response.put("key", "someValue");

		when(jdbcUtils.queryForMap("anyQueryId", new HashMap<>())).thenReturn(response);

		Map<String, Object> validation = pisdR402.executeGetASingleRow("anyQueryId", new HashMap<>());

		assertNotNull(validation);
	}

	@Test
	public void executeGetASingleRow_WithNoResultException() {
		LOGGER.info("PISDR402Test - Executing executeGetASingleRow_WithNoResultException...");

		when(jdbcUtils.queryForMap("anyQueryId", new HashMap<>())).thenThrow(new NoResultException("adviceCode", "errorMessage"));

		Map<String, Object> validation = pisdR402.executeGetASingleRow("anyQueryId", new HashMap<>());

		assertNull(validation);
	}

	@Test
	public void executeGetListASingleRow_OK() {
		LOGGER.info("PISDR402Test - Executing executeGetListASingleRow_OK...");

		List<Map<String, Object>> listResponse = new ArrayList<>();
		Map<String, Object> response = new HashMap<>();
		response.put("key", "someValue");
		listResponse.add(response);

		when(jdbcUtils.queryForList("anyQueryId", new HashMap<>())).thenReturn(listResponse);

		List < Map<String, Object> >validation = pisdR402.executeGetListASingleRow("anyQueryId", new HashMap<>());

		assertNotNull(validation);
	};

	@Test
	public void executeGetListASingleRow_WithNoResultException() {
		LOGGER.info("PISDR402Test - Executing executeGetListASingleRow_WithNoResultException...");

		when(jdbcUtils.queryForList("anyQueryId", new HashMap<>())).thenThrow(new NoResultException("adviceCode", "errorMessage"));

		List <Map<String, Object>> validation = pisdR402.executeGetListASingleRow("anyQueryId", new HashMap<>());

		assertNull(validation);
	}


	
}
