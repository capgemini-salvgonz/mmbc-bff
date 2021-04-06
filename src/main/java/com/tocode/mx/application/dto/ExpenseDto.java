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
* File name: ExpenseDto.java
* Original Author: salvador
* Creation Date: 5 abr 2021
* ---------------------------------------------------------------------------
*/

package com.tocode.mx.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


/**
 *  <code>ExpenseDto</code>.
 *
 * @author salvador
 * @version 1.0
 */
@Getter
@Setter
public class ExpenseDto {

  /** expense id. */
  private Long expenseId;

  /** fixed expense id. */
  private Long fixedExpenseId;

  /** amount. */
  private Float amount;

  /** description. */
  private String description;

  /** expense type id. */
  private Long expenseTypeId;

  /** execution date. */
  private LocalDate executionDate;
}
