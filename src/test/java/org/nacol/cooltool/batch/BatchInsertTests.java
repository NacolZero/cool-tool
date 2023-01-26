package org.nacol.cooltool.batch;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.lang.UUID;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
@SpringBootTest
class BatchInsertTests {

	@Autowired
	DataSource dataSource;


	@Test
	public void test() {
		log.info("123 : {}", dataSource);
//		List<SysLog> logs = new ArrayList<>();
//		for (int i = 0; i < 1000000; i++) {
//			logs.add(new SysLog().setMsg(UUID.fastUUID().toString()));
//		}
//
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//
//		BacthUtils.batchInsert(logs, "insert into sys_log (msg) values (?)", 10000, "", dataSource);
//		stopWatch.stop();
//		System.out.println(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
	}
}
