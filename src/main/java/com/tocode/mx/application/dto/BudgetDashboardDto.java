/*
*                     GNU GENERAL PUBLIC LICENSE
*                        Version 3, 29 June 2007
* 
*  Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
*  Everyone is permitted to copy and distribute verbatim copies
*  of this license document, but changing it is not allowed.
* 
*                             Preamble
* 
*   The GNU General Public License is a free, copyleft license for
* software and other kinds of works.
* 
*   The licenses for most software and other practical works are designed
* to take away your freedom to share and change the works.  By contrast,
* the GNU General Public License is intended to guarantee your freedom to
* share and change all versions of a program--to make sure it remains free
* software for all its users.  We, the Free Software Foundation, use the
* GNU General Public License for most of our software; it applies also to
* any other work released this way by its authors.  You can apply it to
* your programs, too.
*
* File name: BudgetDashboardDto.java
* Original Author: salvador
* Creation Date: 12 abr 2021
* ---------------------------------------------------------------------------
*/

package com.tocode.mx.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 *  <code>BudgetDashboardDto</code>.
 *
 * @author salvador
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class BudgetDashboardDto {

  
  /** user id. */
  @JsonIgnore
  private Long userId;
  
  /** current date. */
  private String currentDate;
  
  @JsonIgnore
  private String startDate;

  /** end of period. */
  private String endOfPeriod;

  /** available balance. */
  private Float availableBalance;

  /** pending fixed expenses. */
  private Float pendingFixedExpenses;

  /** pending weeks. */
  private Float pendingWeeks;
  
  /** monthly budget. */
  private Float monthlyBudget;

  /** biweekly budget. */
  private Float biweeklyBudget;

  /** weekly budget. */
  private Float weeklyBudget;

  /** allowed expense percentage. */
  private Integer allowedExpensePercentage;
}
