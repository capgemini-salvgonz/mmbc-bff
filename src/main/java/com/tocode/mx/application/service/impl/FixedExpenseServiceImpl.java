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
* File name: FixedExpenseServiceImpl.java
* Original Author: salvador
* Creation Date: 5 abr 2021
* ---------------------------------------------------------------------------
*/

package com.tocode.mx.application.service.impl;

import com.tocode.mx.application.dto.CognitoUser;
import com.tocode.mx.application.dto.FixedExpenseDto;
import com.tocode.mx.application.mapper.FixedExpenseMapper;
import com.tocode.mx.application.repository.FixedExpenseRepository;
import com.tocode.mx.application.service.FixedExpenseService;
import com.tocode.mx.application.service.UserService;
import com.tocode.mx.model.FixedExpense;
import com.tocode.mx.model.User;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

/**
 *  <code>FixedExpenseServiceImpl</code>.
 *
 * @author salvador
 * @version 1.0
 */
@Service
public class FixedExpenseServiceImpl implements FixedExpenseService {

  /** user service. */
  private UserService userService;

  /** fixed expense repository. */
  private FixedExpenseRepository fixedExpenseRepository;

  /**
   * Creates a new instance of fixed expense service impl.
   *
   * @param userSerice user serice
   * @param fixedExpenseRepository fixed expense repository
   */
  public FixedExpenseServiceImpl(UserService userSerice,
    FixedExpenseRepository fixedExpenseRepository) {

    this.userService = userSerice;
    this.fixedExpenseRepository = fixedExpenseRepository;
  }

  /**
   * Gets the fixed expenses.
   *
   * @param user user
   * @return fixed expenses
   */
  @Override
  public List<FixedExpenseDto> getFixedExpenses(CognitoUser user) {

    return this.userService.getUserUsingEmail(user.getEmail())
      .map(User::getUserId).map(this.fixedExpenseRepository::findByUserId).get()
      .stream().map(FixedExpenseMapper::transform).collect(Collectors.toList());
  }

  /**
   * Publish fixed expense.
   *
   * @param user user
   * @param expenseDto expense dto
   */
  @Override
  @Transactional
  public void publishFixedExpense(CognitoUser user,
    FixedExpenseDto expenseDto) {

    Consumer<FixedExpense> consumerExpense =
      expenseDto.getFixedExpenseId() == null ? 
        this.fixedExpenseRepository::save
        : this.fixedExpenseRepository::update;

    this.userService.getUserUsingEmail(user.getEmail())
      .map(u -> this.getEntity(u, expenseDto))
      .ifPresent(consumerExpense);
  }

  /**
   * Drop fixed expense entry.
   *
   * @param user user
   * @param expenseDto expense dto
   */
  @Override
  public void dropFixedExpenseEntry(CognitoUser user,
    FixedExpenseDto expenseDto) {

    this.userService.getUserUsingEmail(user.getEmail())
      .map(u -> this.getEntity(u, expenseDto))
      .ifPresent(this.fixedExpenseRepository::delete);
  }

  /**
   * Gets the entity.
   *
   * @param user user
   * @param expenseDto expense dto
   * @return entity
   */
  private FixedExpense getEntity(User user, FixedExpenseDto expenseDto) {

    FixedExpense expense = FixedExpenseMapper.from(expenseDto);
    expense.setUserId(user.getUserId());
    return expense;
  }

}
