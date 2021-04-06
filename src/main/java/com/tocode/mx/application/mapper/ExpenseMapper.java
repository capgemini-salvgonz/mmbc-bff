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
* File name: ExpenseMapper.java
* Original Author: salvador
* Creation Date: 5 abr 2021
* ---------------------------------------------------------------------------
*/

package com.tocode.mx.application.mapper;

import com.tocode.mx.application.dto.ExpenseDto;
import com.tocode.mx.model.Expense;

import java.sql.Date;

/**
 *  <code>ExpenseMapper</code>.
 *
 * @author salvador
 * @version 1.0
 */
public class ExpenseMapper {

  /**
   * From.
   *
   * @param expenseDto expense dto
   * @return expense
   */
  public static Expense from(ExpenseDto expenseDto) {

    Expense expense = new Expense();
    expense.setAmount(expenseDto.getAmount());
    expense.setDescription(expenseDto.getDescription());
    expense.setExecutionDate(Date.valueOf(expenseDto.getExecutionDate()));
    expense.setExpenseId(expenseDto.getExpenseId());
    expense.setExpenseTypeId(expenseDto.getExpenseTypeId());
    expense.setFixedExpenseId(expenseDto.getFixedExpenseId());

    return expense;
  }

  /**
   * Transform.
   *
   * @param expense expense
   * @return expense dto
   */
  public static ExpenseDto transform(Expense expense) {

    ExpenseDto expenseDto = new ExpenseDto();
    expenseDto.setAmount(expense.getAmount());
    expenseDto.setDescription(expense.getDescription());
    expenseDto.setExecutionDate(expense.getExecutionDate().toLocalDate());
    expenseDto.setExpenseId(expense.getExpenseId());
    expenseDto.setExpenseTypeId(expense.getExpenseTypeId());
    expenseDto.setFixedExpenseId(expense.getFixedExpenseId());

    return expenseDto;
  }
}
