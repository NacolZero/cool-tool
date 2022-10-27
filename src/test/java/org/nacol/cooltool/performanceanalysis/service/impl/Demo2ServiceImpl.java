package org.nacol.cooltool.performanceanalysis.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.nacol.cooltool.performanceanalysis.PerformanceAnalysis;
import org.nacol.cooltool.performanceanalysis.service.Demo2Service;
import org.nacol.cooltool.performanceanalysis.service.Demo3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class Demo2ServiceImpl implements Demo2Service {

    private final Demo3Service demo3Service;

    @PerformanceAnalysis(taskName = "f1.1", processName = "Process_1.1", startFlag = true)
    public void f11() {
        log.info("f1.1");
    }

    @PerformanceAnalysis(taskName = "f1.2", processName = "Process_1.1")
    public void f12() {
        log.info("f1.2");

        List<Future<String>> results = new ArrayList<>();
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 12; i++) {
            Future<String> result = pool.submit(() -> demo3Service.f121());
            results.add(result);
        }
        for (Future<String> result : results) {
            try {
                result.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @PerformanceAnalysis(taskName = "f1.3", processName = "Process_1.1", endFlag = true)
    public void f13() {
        log.info("f1.3");
    }

}
