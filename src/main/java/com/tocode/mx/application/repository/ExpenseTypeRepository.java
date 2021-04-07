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
* Nombre de archivo: ExpenseTypeRepository.java 
* Autor: salvgonz 
* Fecha de creación: 2 abr. 2021 
*/

package com.tocode.mx.application.repository;

import com.tocode.mx.model.ExpenseType;
import com.tocode.mx.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

/**
 * The Interface ExpenseTypeRepository.
 */
public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long>{

  /**
   * Retrieve all types.
   *
   * @param userId the user id
   * @return the list
   */
  @Query(
      value = "SELECT c.id_cat_expense, c.description, c.id_user FROM cat_expense c "
          + "WHERE c.id_user=:#{#user.userId} OR c.id_user IS NULL "
          + "ORDER BY c.description ASC" 
      , nativeQuery = true)
  List<ExpenseType> retrieveAllTypes(@Param("user") User user);
}

