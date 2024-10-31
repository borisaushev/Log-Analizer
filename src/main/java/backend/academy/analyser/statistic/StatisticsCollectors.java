package backend.academy.analyser.statistic;

import backend.academy.analyser.statistic.impl.GeneralRequestStatisticCollector;
import backend.academy.analyser.statistic.impl.LeastRequestedResourcesStatisticCollector;
import backend.academy.analyser.statistic.impl.MostRequestedResourcesStatisticCollector;
import backend.academy.analyser.statistic.impl.TopResponseCodesStatisticCollector;

//TODO: add new real
public enum StatisticsCollectors {
    GENERAL(new GeneralRequestStatisticCollector()),
    LEAST_REQUESTED_RESOURCES(new LeastRequestedResourcesStatisticCollector()),
    MOST_REQUESTED_RESOURCES(new MostRequestedResourcesStatisticCollector()),
    TOP_RESPONSE_CODES(new TopResponseCodesStatisticCollector());

    public final StatisticsCollector strategy;
    StatisticsCollectors(StatisticsCollector strategy) {
        this.strategy = strategy;
    }
}
