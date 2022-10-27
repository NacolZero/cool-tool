package org.nacol.cooltool.performanceanalysis;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.nacol.cooltool.performanceanalysis.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Log4j2
@SpringBootTest
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class PerformanceAnalysisTests {

	private final DemoService demoService;

	@Test
	public void contextLoads() {
		demoService.f1();
		demoService.f2();
		demoService.f3();
	}

}
