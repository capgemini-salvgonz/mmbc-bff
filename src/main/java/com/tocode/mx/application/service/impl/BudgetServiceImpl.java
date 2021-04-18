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

import com.tocode.mx.application.dto.BudgetDashboardDto;
import com.tocode.mx.application.dto.BudgetDto;
import com.tocode.mx.application.dto.CognitoUser;
import com.tocode.mx.application.mapper.BudgetMapper;
import com.tocode.mx.application.repository.BudgetRepository;
import com.tocode.mx.application.repository.FixedExpenseRepository;
import com.tocode.mx.application.service.AccountService;
import com.tocode.mx.application.service.BudgetService;
import com.tocode.mx.application.service.RevenueService;
import com.tocode.mx.application.service.UserService;
import com.tocode.mx.model.Budget;
import com.tocode.mx.model.User;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
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
  
  /** fixed expense service. */
  private FixedExpenseRepository fixedExpenseRepository;

  /** budget repository. */
  private BudgetRepository budgetRepository;
  
  /** revenue service. */
  private RevenueService revenueService;
  
  /** account service. */
  private AccountService accountService;
  
  /** dtf. */
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  /**
   * Creates a new instance of budget service impl.
   *
   * @param userService user service
   * @param budgetRepository budget repository
   */
  public BudgetServiceImpl(UserService userService,
    FixedExpenseRepository fixedExpenseRepository,
    RevenueService revenueService,
    BudgetRepository budgetRepository,
    AccountService accountService) {

    this.userService = userService;
    this.budgetRepository = budgetRepository;
    this.fixedExpenseRepository = fixedExpenseRepository;
    this.revenueService = revenueService;
    this.accountService = accountService;
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

  /**
   * Gets the budget dashboard.
   *
   * @param cognitoUser user
   * @return budget dashboard
   */
  @Override
  public BudgetDashboardDto getBudgetDashboard(CognitoUser cognitoUser) {
    
    User user = this.userService.getUserUsingEmail(cognitoUser.getEmail()).get();    
    
    Optional<BudgetDashboardDto> dashboardDto = 
      Optional.ofNullable(this.getBudget(cognitoUser))
      .map(this::setDates)
      .map(bd -> {bd.setUserId(user.getUserId()); return bd;})
      .map(bd -> this.setBalance(cognitoUser, bd))
      .map(bd -> {
        bd.setAvailableBalance(
          this.accountService.sumBalance(user.getUserId()) - 
          bd.getPendingFixedExpenses());
        return bd;
      });
    
    return dashboardDto.orElse(new BudgetDashboardDto());
  }
  
  /**
   * Sets the balance.
   *
   * @param cognitoUser cognito user
   * @param dto dto
   * @return budget dashboard dto
   */
  private BudgetDashboardDto setBalance(CognitoUser cognitoUser, 
      BudgetDashboardDto dto) {
    Date startDate = Date.valueOf(LocalDate.parse(dto.getStartDate(), FORMATTER));
    Date endDate = Date.valueOf(LocalDate.parse(dto.getEndOfPeriod(), FORMATTER));
    
    Float pending = 
      this.fixedExpenseRepository.pendingFixedExpenses(dto.getUserId(), startDate, endDate);
    Float totalFixedExpenses = 
      this.fixedExpenseRepository.getTotalFixedExpenses(dto.getUserId());
    Float totalRevenue = 
      this.revenueService.getTotal(cognitoUser);
        
    Float montlyBudget = 
      Optional.of(totalRevenue).orElse(Float.valueOf(0f)) -
      Optional.of(totalFixedExpenses).orElse(Float.valueOf(0f));
        
    dto.setTotalRevenues(totalRevenue);
    dto.setPendingFixedExpenses(Optional.of(pending).orElse(Float.valueOf(0f)));
    dto.setMonthlyBudget(montlyBudget);
    dto.setBiweeklyBudget(montlyBudget/2);
    dto.setWeeklyBudget(montlyBudget/4);
    
    return dto;
  }
  
  /**
   * Set the dates.
   *
   * @param budget  dates
   */
  private BudgetDashboardDto setDates(BudgetDto budget) {
    
    LocalDate currentDate = LocalDate.now();
    LocalDate tmpDate = 
      LocalDate.of(currentDate.getYear(), currentDate.getMonth(), budget.getStartDay());    
    LocalDate endOfPeriod = (currentDate.isAfter(tmpDate) || currentDate.isEqual(tmpDate)) ?
        this.addDays(budget.getPeriod(), tmpDate) : 
        tmpDate;

    BudgetDashboardDto dashboardDto = new BudgetDashboardDto();
    dashboardDto.setStartDate(FORMATTER.format(tmpDate));
    dashboardDto.setCurrentDate(FORMATTER.format(currentDate));
    dashboardDto.setEndOfPeriod(FORMATTER.format(endOfPeriod));
    dashboardDto.setPendingWeeks(
      Float.valueOf(Period.between(currentDate, endOfPeriod).getDays()/7.0f));
    dashboardDto.setAllowedExpensePercentage(budget.getAllowedExpensesPercentage());
    
    return dashboardDto;
  }
  
  /**
   * Adds the days.
   *
   * @param period period
   * @param date date
   * @return local date
   */
  private LocalDate addDays(String period, LocalDate date) {
    
    return "M".equalsIgnoreCase(period) ? 
      date.plusMonths(1l).minusDays(1L): date.plusDays(14);
  }

}
