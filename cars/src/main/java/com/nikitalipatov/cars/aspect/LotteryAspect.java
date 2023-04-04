package com.nikitalipatov.cars.aspect;

import com.nikitalipatov.common.dto.response.ActiveCitizen;
import com.nikitalipatov.common.enums.WorkPlace;
import com.nikitalipatov.common.feign.CitizenClient;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor
public class LotteryAspect {

    private final CitizenClient citizenClient;

    @Pointcut("@annotation(com.nikitalipatov.common.annotation.ChooseWinner)")
    public void callAtLotteryChooseWinner() {}

    @Around(value = "callAtLotteryChooseWinner()")
    public ActiveCitizen aroundCall(ProceedingJoinPoint joinPoint) throws Throwable {
        ActiveCitizen winner = (ActiveCitizen) joinPoint.proceed();
        if (!winner.getWork().equals(WorkPlace.FACTORY.name())) {
            List<ActiveCitizen> allCitizens = citizenClient.getActiveCitizens();
            List<ActiveCitizen> factoryWorkers = allCitizens
                    .stream()
                    .filter(c -> c.getWork().equals(WorkPlace.FACTORY.name()))
                    .collect(Collectors.toList());
            winner = factoryWorkers.get(new Random().nextInt(factoryWorkers.size()));
        }
        return winner;
    }
}
