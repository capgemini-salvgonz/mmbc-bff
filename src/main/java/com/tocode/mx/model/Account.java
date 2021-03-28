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
* Nombre de archivo: Account.java 
* Autor: salvgonz 
* Fecha de creación: Mar 28, 2021 
*/

package com.tocode.mx.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The Class Account.
 */
@Entity
@Table(name = "account")
@Getter
@Setter
public class Account {

  /** The account number. */
  @Id
  @Column(name = "account_number", nullable = false, length = 11)
  private Long accountNumber;

  /** The description. */
  @Column(name = "description", nullable = false, length = 40)
  private String description;

  /** The type. */
  @Column(name = "type", nullable = false, length = 1)
  private String type;

  /** The balance. */
  @Column(name = "balance", nullable = false, length = 12, precision = 4)
  private Float balance;

  /** The user id. */
  @Column(name = "id_user")
  private Long userId;
}
