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
* File name: ExpenseRepository.java
* Original Author: salvador
* Creation Date: 5 abr 2021
* ---------------------------------------------------------------------------
*/

package com.tocode.mx.application.repository;

import com.tocode.mx.model.Expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

/**
 *  <code>ExpenseRepository</code>.
 *
 * @author salvador
 * @version 1.0
 */
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

  /**
   * Find by user id.
   *
   * @param userId user id
   * @return list
   */
  List<Expense> findByUserId(Long userId);
  
  /**
   * Find by execution date greater than equal.
   *
   * @param executionDate execution date
   * @return list
   */
  List<Expense> findByExecutionDateGreaterThanEqualAndUserId(Date executionDate, Long userId);

  /**
   * Count by fixed expense id.
   *
   * @param fixedExpenseId fixed expense id
   * @return integer
   */
  Integer countByFixedExpenseId(Long fixedExpenseId);

  /**
   * Update expense.
   *
   * @param expense expense
   */
  @Modifying
  @Query(
    value = "UPDATE expense SET id_fixed_expense=:#{#expense.fixedExpenseId}, "
      + "amount=:#{#expense.amount}, description=:#{#expense.description}, "
      + "id_cat_expense=:#{#expense.expenseTypeId}, execution_date=:#{#expense.executionDate} "
      + "WHERE id_expense=:#{#expense.expenseId} AND id_user=:#{#expense.userId}",
    nativeQuery = true)
  void updateExpense(@Param("expense") Expense expense);

  /**
   * Delete expense.
   *
   * @param expense expense
   */
  @Modifying
  @Query(value = "DELETE FROM expense "
    + "WHERE id_expense=:#{#expense.expenseId} AND id_user=:#{#expense.userId}",
    nativeQuery = true)
  void deleteExpense(@Param("expense") Expense expense);
}
