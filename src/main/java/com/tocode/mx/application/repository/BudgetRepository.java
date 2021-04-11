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
* File name: BudgetRepository.java
* Original Author: salvador
* Creation Date: 10 abr 2021
* ---------------------------------------------------------------------------
*/

package com.tocode.mx.application.repository;

import com.tocode.mx.model.Budget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 *  <code>BudgetRepository</code>.
 *
 * @author salvador
 * @version 1.0
 */
public interface BudgetRepository extends JpaRepository<Budget, Long> {

  /**
   * Find by user id.
   *
   * @param userId user id
   * @return optional
   */
  Budget findByUserId(Long userId);

  /**
   * Update budget.
   *
   * @param budget budget
   */
  @Modifying
  @Query(
    value = "UPDATE budget SET " + "period = :#{#budget.period},"
      + "start_day = :#{#budget.startDay},"
      + "allowed_expenses_percentage = :#{#budget.allowedExpensesPercentage} "
      + "WHERE id_budget=:#{#budget.budgetId} AND id_user=:#{#budget.userId}",
    nativeQuery = true)
  void updateBudget(@Param("budget") Budget budget);
}
