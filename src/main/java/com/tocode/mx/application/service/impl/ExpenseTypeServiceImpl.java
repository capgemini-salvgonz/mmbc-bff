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
* Nombre de archivo: ExpenseTypeServiceImpl.java 
* Autor: salvgonz 
* Fecha de creación: 2 abr. 2021 
*/

package com.tocode.mx.application.service.impl;

import com.tocode.mx.application.dto.CognitoUser;
import com.tocode.mx.application.dto.ExpenseTypeDto;
import com.tocode.mx.application.mapper.ExpenseTypeMapper;
import com.tocode.mx.application.repository.ExpenseTypeRepository;
import com.tocode.mx.application.service.ExpenseTypeService;
import com.tocode.mx.application.service.UserService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The Class ExpenseTypeServiceImpl.
 */
@Service
public class ExpenseTypeServiceImpl implements ExpenseTypeService {

  /** The user service. */
  private UserService userService;
  
  /** The expense type repository. */
  private ExpenseTypeRepository expenseTypeRepository;
  
  /**
   * Instantiates a new expense type service impl.
   *
   * @param userService the user service
   * @param expenseTypeRepository the expense type repository
   */
  public ExpenseTypeServiceImpl(UserService userService,
      ExpenseTypeRepository expenseTypeRepository) {
    this.userService = userService;
    this.expenseTypeRepository = expenseTypeRepository;
  }
  
  /**
   * Gets the expense types.
   *
   * @param user the user
   * @return the expense types
   */
  @Override
  public List<ExpenseTypeDto> getExpenseTypes(CognitoUser user) {
    return this.userService
        .getUserUsingEmail(user.getEmail())
        .map(this.expenseTypeRepository::retrieveAllTypes).get()
        .stream()
        .map(ExpenseTypeMapper::transform)
        .collect(Collectors.toList());
  }
}
