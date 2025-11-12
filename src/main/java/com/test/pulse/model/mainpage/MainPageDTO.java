package com.test.pulse.model.mainpage;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MainPageDTO {
    
	//현재 날씨
	private double temp;              // 현재 기온 (℃)
    private int humidity;             // 습도 (%)
    private int rainChance;           // 강수확률 (%)
    private String skyCondition;      // 하늘 상태 (맑음, 흐림 등)
    private String precipitationType; // 강수형태 (비, 눈, 없음 등)
    
}



