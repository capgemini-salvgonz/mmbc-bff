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
* File name: ExpenseService.java
* Original Author: salvador
* Creation Date: 6 abr 2021
* ---------------------------------------------------------------------------
*/

package com.tocode.mx.application.service;

import com.tocode.mx.application.dto.CognitoUser;
import com.tocode.mx.application.dto.ExpenseDto;

import java.util.List;

/**
 *  <code>ExpenseService</code>.
 *
 * @author salvador
 * @version 1.0
 */
public interface ExpenseService {

  /**
   * Gets the expenses.
   *
   * @param cognitoUser cognito user
   * @return expenses
   */
  List<ExpenseDto> getExpenses(CognitoUser cognitoUser);
  
  /**
   * Verify if there is any fixed expense.
   *
   * @param fixedExpenseId fixed expense id
   * @return true, in case the condition is satisfied there is fixed expense
   */
  boolean isThereFixedExpense(Long fixedExpenseId);

  /**
   * Publish expense.
   *
   * @param cognitoUser cognito user
   * @param expenseDto expense dto
   */
  void publishExpense(CognitoUser cognitoUser, ExpenseDto expenseDto);

  /**
   * Drop expense entry.
   *
   * @param cognitoUser cognito user
   * @param expenseDto expense dto
   */
  void dropExpenseEntry(CognitoUser cognitoUser, ExpenseDto expenseDto);
}
