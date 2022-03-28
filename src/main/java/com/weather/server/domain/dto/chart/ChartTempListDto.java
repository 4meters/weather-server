package com.weather.server.domain.dto.chart;

import java.util.List;

public class ChartTempListDto {
    private List<ChartTempDto> chartTempDtoList;

    public ChartTempListDto(List<ChartTempDto> chartTempDtoList) {
        this.chartTempDtoList = chartTempDtoList;
    }

    public List<ChartTempDto> getChartTempDtoList() {
        return chartTempDtoList;
    }

    public void setChartTempDtoList(List<ChartTempDto> chartTempDtoList) {
        this.chartTempDtoList = chartTempDtoList;
    }
}
