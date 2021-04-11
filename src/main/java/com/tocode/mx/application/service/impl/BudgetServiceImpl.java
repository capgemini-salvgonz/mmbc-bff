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
* File name: BudgetServiceImpl.java
* Original Author: salvador
* Creation Date: 10 abr 2021
* ---------------------------------------------------------------------------
*/

package com.tocode.mx.application.service.impl;

import com.tocode.mx.application.dto.BudgetDto;
import com.tocode.mx.application.dto.CognitoUser;
import com.tocode.mx.application.mapper.BudgetMapper;
import com.tocode.mx.application.repository.BudgetRepository;
import com.tocode.mx.application.service.BudgetService;
import com.tocode.mx.application.service.UserService;
import com.tocode.mx.model.Budget;
import com.tocode.mx.model.User;

import org.springframework.stereotype.Service;

import java.util.function.Consumer;

import javax.transaction.Transactional;

/**
 *  <code>BudgetServiceImpl</code>.
 *
 * @author salvador
 * @version 1.0
 */
@Service
public class BudgetServiceImpl implements BudgetService {

  /** user service. */
  private UserService userService;

  /** budget repository. */
  private BudgetRepository budgetRepository;

  /**
   * Creates a new instance of budget service impl.
   *
   * @param userService user service
   * @param budgetRepository budget repository
   */
  public BudgetServiceImpl(UserService userService,
    BudgetRepository budgetRepository) {

    this.userService = userService;
    this.budgetRepository = budgetRepository;
  }

  /**
   * Gets the budget.
   *
   * @param user user
   * @return budget
   */
  @Override
  public BudgetDto getBudget(CognitoUser user) {
    
    BudgetDto budgetDto = 
      this.userService.getUserUsingEmail(user.getEmail())
      .map(User::getUserId)
      .map(this.budgetRepository::findByUserId)
      .map(BudgetMapper::transform)
      .orElse(null);
    
    return budgetDto;
  }

  /**
   * Update budget.
   *
   * @param user user
   * @param budget budget
   */
  @Override
  @Transactional
  public void postBudget(CognitoUser user, BudgetDto budgetDto) {
    
    Consumer<Budget> action = budgetDto.getBudgetId() != null ? 
      this.budgetRepository::updateBudget :
      this.budgetRepository::save;
    
    this.userService.getUserUsingEmail(user.getEmail())
    .map(User::getUserId)
    .map(userId -> {
      Budget budget = BudgetMapper.from(budgetDto);
      budget.setUserId(userId);
      return budget;
    }).ifPresent(action);
  }

}
