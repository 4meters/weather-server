package com.weather.server.domain.dto.chart;

import java.util.List;

public class ChartListDto {
    private List<?> chartDtoList;

    public ChartListDto() {
    }

    private ChartListDto(Builder builder) {
        this.chartDtoList = builder.chartDtoList;
    }

    public List<?> getChartDtoList() {
        return chartDtoList;
    }

    public void setChartDtoList(List<ChartTempDto> chartDtoList) {
        this.chartDtoList = chartDtoList;
    }


    public static final class Builder {
        private List<?> chartDtoList;

        public Builder() {
        }

        public static Builder aChartListDto() {
            return new Builder();
        }

        public Builder chartDtoList(List<?> chartDtoList) {
            this.chartDtoList = chartDtoList;
            return this;
        }

        public ChartListDto build() {
            return new ChartListDto(this);
        }
    }
}
