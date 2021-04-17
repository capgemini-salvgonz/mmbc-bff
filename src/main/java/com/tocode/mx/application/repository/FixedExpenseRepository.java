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
* File name: FixedExpenseRepository.java
* Original Author: salvador
* Creation Date: 5 abr 2021
* ---------------------------------------------------------------------------
*/

package com.tocode.mx.application.repository;

import com.tocode.mx.model.FixedExpense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

/**
 *  <code>FixedExpenseRepository</code>.
 *
 * @author salvador
 * @version 1.0
 */
public interface FixedExpenseRepository
  extends JpaRepository<FixedExpense, Long> {

  /**
   * Find by user id.
   *
   * @param userId user id
   * @return list
   */
  List<FixedExpense> findByUserId(Long userId);
  
  /**
   * Count by user id.
   *
   * @param userId user id
   * @return integer
   */
  Integer countByUserId(Long userId);

  /**
   * Update.
   *
   * @param fixedExpense fixed expense
   */
  @Modifying
  @Query(value = "UPDATE fixed_expense SET amount=:#{#fixedExpense.amount}, "
    + "description=:#{#fixedExpense.description}, id_cat_expense=:#{#fixedExpense.expenseTypeId} "
    + "WHERE id_fixed_expense=:#{#fixedExpense.fixedExpenseId} "
    + "AND id_user=:#{#fixedExpense.userId}", nativeQuery = true)
  void update(@Param("fixedExpense") FixedExpense fixedExpense);
  
  
  @Modifying
  @Query(value = "UPDATE fixed_expense SET active = 0 "
  +  "WHERE id_fixed_expense=:#{#fixedExpense.fixedExpenseId} "
  + "AND id_user=:#{#fixedExpense.userId}", nativeQuery = true)
  void disable(@Param("fixedExpense") FixedExpense fixedExpense);

  /**
   * Delete.
   *
   * @param fixedExpense fixed expense
   */
  @Modifying
  @Query(value = "DELETE FROM fixed_expense "
    + "WHERE id_fixed_expense=:#{#fixedExpense.fixedExpenseId} "
    + "AND id_user=:#{#fixedExpense.userId}", nativeQuery = true)
  void delete(@Param("fixedExpense") FixedExpense fixedExpense);
  
  
  /**
   * Gets the total fixed expenses.
   *
   * @param userId user id
   * @return total fixed expenses
   */
  @Query(value = "SELECT SUM(f.amount) FROM fixed_expense f WHERE f.id_user=:userId "
    + "AND f.active = 1 ", 
    nativeQuery = true)
  Float getTotalFixedExpenses(@Param("userId") Long userId);

  
  @Query(value = "SELECT sum(f.amount) as 'count' "
    + "FROM fixed_expense f "
    + "LEFT JOIN expense e ON "
    + "  f.id_user = e.id_user "
    + "  AND f.id_fixed_expense = e.id_fixed_expense "
    + "  AND e.execution_date between :startDate and :endDate "
    + "WHERE "
    + "  f.id_user = :userId AND "
    + "  f.active = 1 "
    + "group by e.id_user "
    + "having e.id_user is null", 
    nativeQuery = true)
  Float pendingFixedExpenses(
    @Param("userId") Long userId, 
    @Param("startDate") Date startDate,
    @Param("endDate") Date endDate);
  
}
