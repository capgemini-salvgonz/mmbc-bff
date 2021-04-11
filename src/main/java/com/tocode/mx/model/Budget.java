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
* File name: Budget.java
* Original Author: salvador
* Creation Date: 9 abr 2021
* ---------------------------------------------------------------------------
*/

package com.tocode.mx.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  <code>Budget</code>.
 *
 * @author salvador
 * @version 1.0
 */
@Entity
@Table(name = "budget")
@Getter
@Setter
public class Budget {

  /** budget id. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_budget")
  private Long budgetId;

  /** user id. */
  @Column(name = "id_user", nullable = false, unique = true)
  private Long userId;

  /** period. */
  @Column(name = "period", nullable = false, length = 2)
  private String period;

  /** start day. */
  @Column(name = "start_day", nullable = false, length = 2)
  private Integer startDay;

  /** allowed expenses percentage. */
  @Column(name = "allowed_expenses_percentage", nullable = false, length = 3)
  private Integer allowedExpensesPercentage;
}
