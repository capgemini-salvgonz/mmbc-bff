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
* Nombre de archivo: User.java 
* Autor: salvgonz 
* Fecha de creación: Mar 28, 2021 
*/

package com.tocode.mx.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The Class User.
 */
@Entity
@Table(name = "user")
@Getter
@Setter
public class User {

  /** The user id. */
  @Id
  @Column(name = "id_user")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long userId;

  /** The email. */
  @Column(name = "email", length = 60, nullable = false, unique = true)
  private String email;

  /** The user name. */
  @Column(name = "user_name", length = 30, nullable = false)
  private String userName;

  /** The gender. */
  @Column(name = "gender", length = 1, nullable = true)
  private String gender;

  /** The birthday. */
  @Column(name = "birthday", nullable = true)
  private Date birthday;

  /** The creation date. */
  @Column(name = "creation_date", nullable = true)
  private Date creationDate;
}
