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
* File name: BudgetMapper.java
* Original Author: salvador
* Creation Date: 10 abr 2021
* ---------------------------------------------------------------------------
*/

package com.tocode.mx.application.mapper;

import com.tocode.mx.application.dto.BudgetDto;
import com.tocode.mx.model.Budget;

/**
 *  <code>BudgetMapper</code>.
 *
 * @author salvador
 * @version 1.0
 */
public class BudgetMapper {

  /**
   * Transform.
   *
   * @param budget budget
   * @return budget dto
   */
  public static BudgetDto transform(Budget budget) {

    BudgetDto dto = new BudgetDto();
    dto.setAllowedExpensesPercentage(budget.getAllowedExpensesPercentage());
    dto.setBudgetId(budget.getBudgetId());
    dto.setPeriod(budget.getPeriod());
    dto.setStartDay(budget.getStartDay());

    return dto;
  }

  /**
   * From.
   *
   * @param budgetDto budget dto
   * @return budget
   */
  public static Budget from(BudgetDto budgetDto) {

    Budget budget = new Budget();
    budget
      .setAllowedExpensesPercentage(budgetDto.getAllowedExpensesPercentage());
    budget.setBudgetId(budgetDto.getBudgetId());
    budget.setPeriod(budgetDto.getPeriod());
    budget.setStartDay(budgetDto.getStartDay());

    return budget;
  }
}
