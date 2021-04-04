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
* Nombre de archivo: ExpenseTypeController.java 
* Autor: salvgonz 
* Fecha de creación: 2 abr. 2021 
*/

package com.tocode.mx.application.controller;

import com.tocode.mx.application.dto.CognitoUser;
import com.tocode.mx.application.dto.ExpenseTypeDto;
import com.tocode.mx.application.service.ExpenseTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * The Class ExpenseTypeController.
 */
@RestController
public class ExpenseTypeController {

  /** The expense type service. */
  @Autowired
  private ExpenseTypeService expenseTypeService;

  /**
   * Gets the expense types.
   *
   * @param httpServletRequest the http servlet request
   * @param authorizationTokenId the authorization token id
   * @return the expense types
   */
  @GetMapping(value = "/api/expenses/types")
  public ResponseEntity<List<ExpenseTypeDto>> getExpenseTypes(
      HttpServletRequest httpServletRequest,
      @RequestHeader(value = "Authorization", required = true) String authorizationTokenId) {

    CognitoUser cognitoUser = (CognitoUser) httpServletRequest.getAttribute("user");
    return new ResponseEntity<>(this.expenseTypeService.getExpenseTypes(cognitoUser),
        HttpStatus.OK);
  }
}
