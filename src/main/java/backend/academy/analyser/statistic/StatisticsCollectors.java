package backend.academy.analyser.statistic;

import backend.academy.analyser.statistic.impl.GeneralRequestStatisticCollector;
import backend.academy.analyser.statistic.impl.LeastRequestedResourcesStatisticCollector;
import backend.academy.analyser.statistic.impl.MostRequestedResourcesStatisticCollector;
import backend.academy.analyser.statistic.impl.TopResponseCodesStatisticCollector;

/**
 * An enumeration of available statistics collectors for analyzing log records.
 * Each collector implements the {@link StatisticsCollector} interface, providing
 * functionality to gather specific statistical metrics from log data.
 */
public enum StatisticsCollectors {
    /**
     * General statistics collector that aggregates various metrics from log records.
     */
    GENERAL(new GeneralRequestStatisticCollector()),

    /**
     * Collector for identifying the least requested resources from log records.
     */
    LEAST_REQUESTED_RESOURCES(new LeastRequestedResourcesStatisticCollector()),

    /**
     * Collector for identifying the most requested resources from log records.
     */
    MOST_REQUESTED_RESOURCES(new MostRequestedResourcesStatisticCollector()),

    /**
     * Collector for tracking the top HTTP response codes from log records.
     */
    TOP_RESPONSE_CODES(new TopResponseCodesStatisticCollector());

    /**
     * The statistics collector strategy associated with this enum constant.
     */
    public final StatisticsCollector strategy;

    /**
     * Constructs a StatisticsCollectors enum constant with the specified statistics collector strategy.
     *
     * @param strategy the statistics collector to be associated with this enum constant
     */
    StatisticsCollectors(StatisticsCollector strategy) {
        this.strategy = strategy;
    }
}
