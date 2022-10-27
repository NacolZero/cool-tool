package org.nacol.cooltool.performanceanalysis.service.impl;

import lombok.extern.log4j.Log4j2;
import org.nacol.cooltool.performanceanalysis.PerformanceAnalysis;
import org.nacol.cooltool.performanceanalysis.service.Demo3Service;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class Demo3ServiceImpl implements Demo3Service {


    @Override
    @PerformanceAnalysis(taskName = "f1.2.1", processName = "Process_1.1", startFlag = true, endFlag = true)
    public String f121() {
        log.info("f121");
        return "null";
    }
}
