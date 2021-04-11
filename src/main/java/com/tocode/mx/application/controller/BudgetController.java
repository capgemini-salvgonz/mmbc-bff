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
* File name: BudgetController.java
* Original Author: salvador
* Creation Date: 10 abr 2021
* ---------------------------------------------------------------------------
*/

package com.tocode.mx.application.controller;

import com.tocode.mx.application.dto.BudgetDto;
import com.tocode.mx.application.dto.CognitoUser;
import com.tocode.mx.application.service.BudgetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *  <code>BudgetController</code>.
 *
 * @author salvador
 * @version 1.0
 */
@RestController
public class BudgetController {

  /** budget service. */
  @Autowired
  private BudgetService budgetService;

  /**
   * Gets the budget.
   *
   * @param httpServletRequest http servlet request
   * @param authorizationTokenId authorization token id
   * @return budget
   */
  @GetMapping(value = "/api/budget")
  public ResponseEntity<BudgetDto> getBudget(
    HttpServletRequest httpServletRequest,
    @RequestHeader(value = "Authorization",
      required = true) String authorizationTokenId) {

    CognitoUser cognitoUser =
      (CognitoUser) httpServletRequest.getAttribute("user");

    return new ResponseEntity<>(this.budgetService.getBudget(cognitoUser),
      HttpStatus.OK);
  }

  /**
   * Update budget.
   *
   * @param httpServletRequest http servlet request
   * @param authorizationTokenId authorization token id
   * @param budgetDto budget dto
   * @return response entity
   */
  @PostMapping(value = "/api/budget")
  public ResponseEntity<String> postBudget(
    HttpServletRequest httpServletRequest,
    @RequestHeader(value = "Authorization",
      required = true) String authorizationTokenId,
    @RequestBody BudgetDto budgetDto) {

    CognitoUser cognitoUser =
      (CognitoUser) httpServletRequest.getAttribute("user");
    this.budgetService.postBudget(cognitoUser, budgetDto);

    return new ResponseEntity<>(null, HttpStatus.OK);
  }
}
