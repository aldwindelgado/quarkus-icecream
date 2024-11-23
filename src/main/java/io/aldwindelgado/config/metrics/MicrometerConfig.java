package io.aldwindelgado.config.metrics;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;


/**
 * @author Aldwin Delgado
 */
public class MicrometerConfig {

    private static final double POINT_FIVE_PERCENT = 0.5;
    private static final double POINT_NINE_FIVE_PERCENT = 0.95;
    private static final double POINT_NINETY_NINE_PERCENT = 0.99;
    private static final double POINT_NINE_NINE_NINE_PERCENT = 0.999;

    @Produces
    @ApplicationScoped
    public MeterFilter enableHistogram() {
        return new MeterFilter() {
            @Override
            public DistributionStatisticConfig configure(Meter.Id id, DistributionStatisticConfig config) {
                if (id.getName().startsWith("http.server.requests")) {
                    return DistributionStatisticConfig.builder()
                        .percentiles(
                            POINT_FIVE_PERCENT,
                            POINT_NINE_FIVE_PERCENT,
                            POINT_NINETY_NINE_PERCENT,
                            POINT_NINE_NINE_NINE_PERCENT
                        )
                        .build()
                        .merge(config);
                }
                return config;
            }
        };
    }

}
