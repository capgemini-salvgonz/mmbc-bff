package com.tocode.mx;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExampleTest {

  @Test
  public void shouldGetPeriod() {
    int startDay = 15;
    
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate currentDate = LocalDate.now();
    LocalDate tmpDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), startDay);    
    
    LocalDate endOfPeriod = (currentDate.isAfter(tmpDate) || currentDate.isEqual(tmpDate)) ?
        tmpDate.plusMonths(1l) : 
        tmpDate;
    
    Float weeksLeft = Float.valueOf(Period.between(currentDate, endOfPeriod).getDays()/7.0f);
        
    log.info("Current date [{}]", currentDate.format(dtf));
    log.info("End of period [{}]", endOfPeriod.format(dtf));
    log.info("[{}]", weeksLeft.floatValue());
  }
  
}
