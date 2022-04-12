package com.weather.server.domain.dto.chart;

import java.util.List;

public class ChartListDto {
    private List<?> chartDtoList;

    public ChartListDto(List<?> chartTempDtoList) {
        this.chartDtoList = chartTempDtoList;
    }

    public List<?> getChartDtoList() {
        return chartDtoList;
    }

    public void setChartDtoList(List<ChartTempDto> chartDtoList) {
        this.chartDtoList = chartDtoList;
    }
}
