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
* Nombre de archivo: ExpenseType.java 
* Autor: salvgonz 
* Fecha de creación: 2 abr. 2021 
*/

package com.tocode.mx.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The Class ExpenseType.
 */
@Table(name = "cat_expense")
@Entity
@Getter
@Setter
public class ExpenseType implements Serializable {
  
  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -913860178839431044L;

  /** The expense type id. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_cat_expense")
  private Long expenseTypeId;

  /** The description. */
  @Column(name = "description", nullable = false)
  private String description;

  /** The user id. */
  @Column(name = "id_user", nullable = true)
  private Long userId;
}
