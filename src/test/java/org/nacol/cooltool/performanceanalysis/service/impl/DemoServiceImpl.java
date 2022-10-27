package org.nacol.cooltool.performanceanalysis.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.nacol.cooltool.performanceanalysis.PerformanceAnalysis;
import org.nacol.cooltool.performanceanalysis.service.Demo2Service;
import org.nacol.cooltool.performanceanalysis.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DemoServiceImpl implements DemoService {

    private final Demo2Service Demo2ServiceImpl;

    /****************************************** 第 1 次层 *****************************************/
    @PerformanceAnalysis(taskName = "f1", processName = "Process_1", startFlag = true)
    public void f1() {
        log.info("f1");
        Demo2ServiceImpl.f11();
        Demo2ServiceImpl.f12();
        Demo2ServiceImpl.f13();
    }

    @PerformanceAnalysis(taskName = "f2", processName = "Process_1")
    public void f2() {
        log.info("f2");
    }

    @PerformanceAnalysis(taskName = "f3", processName = "Process_1", endFlag = true)
    public void f3() {
        log.info("f3");
    }
    /****************************************** 第 3 层 *********************************************/
    @PerformanceAnalysis(taskName = "f1.2.2", processName = "Process_1.1")
    public String f121() {
        log.info("f1.2.2");
        return "";
    }

}
