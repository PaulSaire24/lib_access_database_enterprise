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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
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
	public void executeTest(){
		pisdR402.execute();
		Assert.assertEquals(0, context.getAdviceList().size());
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
	
}
