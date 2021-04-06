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
* File name: ExpenseServiceImpl.java
* Original Author: salvador
* Creation Date: 6 abr 2021
* ---------------------------------------------------------------------------
*/

package com.tocode.mx.application.service.impl;

import com.tocode.mx.application.dto.CognitoUser;
import com.tocode.mx.application.dto.ExpenseDto;
import com.tocode.mx.application.mapper.ExpenseMapper;
import com.tocode.mx.application.repository.ExpenseRepository;
import com.tocode.mx.application.service.ExpenseService;
import com.tocode.mx.application.service.UserService;
import com.tocode.mx.model.Expense;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

/**
 *  <code>ExpenseServiceImpl</code>.
 *
 * @author salvador
 * @version 1.0
 */
@Service
public class ExpenseServiceImpl implements ExpenseService {

  /** user service. */
  private UserService userService;

  /** expense repository. */
  private ExpenseRepository expenseRepository;

  /**
   * Creates a new instance of expense service impl.
   *
   * @param userService user service
   * @param expenseRepository expense repository
   */
  public ExpenseServiceImpl(UserService userService,
    ExpenseRepository expenseRepository) {

    this.userService = userService;
    this.expenseRepository = expenseRepository;
  }

  /**
   * Gets the expenses.
   *
   * @param cognitoUser cognito user
   * @return expenses
   */
  @Override
  public List<ExpenseDto> getExpenses(CognitoUser cognitoUser) {

    return this.userService.getUserUsingEmail(cognitoUser.getEmail())
      .map(u -> this.expenseRepository.findByUserId(u.getUserId())).get()
      .stream().map(ExpenseMapper::transform).collect(Collectors.toList());
  }

  /**
   * Publish expense.
   *
   * @param cognitoUser cognito user
   * @param expenseDto expense dto
   */
  @Override
  @Transactional
  public void publishExpense(CognitoUser cognitoUser, ExpenseDto expenseDto) {

    Consumer<Expense> action = expenseDto.getExpenseId() == null
      ? this.expenseRepository::save : this.expenseRepository::updateExpense;

    this.userService.getUserUsingEmail(cognitoUser.getEmail()).map(user -> {
      Expense expense = ExpenseMapper.from(expenseDto);
      expense.setUserId(user.getUserId());
      return expense;
    }).ifPresent(action);
  }

  /**
   * Drop expense entry.
   *
   * @param cognitoUser cognito user
   * @param expenseDto expense dto
   */
  @Override
  @Transactional
  public void dropExpenseEntry(CognitoUser cognitoUser, ExpenseDto expenseDto) {

    this.userService.getUserUsingEmail(cognitoUser.getEmail()).map(user -> {
      Expense expense = ExpenseMapper.from(expenseDto);
      expense.setUserId(user.getUserId());
      return expense;
    }).ifPresent(this.expenseRepository::deleteExpense);
  }

  /**
   * Verify if there is fixed expense.
   *
   * @param fixedExpenseId fixed expense id
   * @return true, in case the condition is satisfied there is any fixed expense
   */
  @Override
  public boolean isThereFixedExpense(Long fixedExpenseId) {

    return fixedExpenseId == null ? false
      : this.expenseRepository.countByFixedExpenseId(fixedExpenseId) > 0;
  }

}
