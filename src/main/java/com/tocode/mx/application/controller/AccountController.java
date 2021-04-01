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
* Nombre de archivo: AccountController.java 
* Autor: salvgonz 
* Fecha de creación: 30 mar. 2021 
*/

package com.tocode.mx.application.controller;

import com.tocode.mx.application.dto.AccountDto;
import com.tocode.mx.application.dto.CognitoUser;
import com.tocode.mx.application.service.AccountService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.servlet.http.HttpServletRequest;


/**
 * The Class AccountController.
 */
@RestController
@Slf4j
public class AccountController {

  /** The account service. */
  @Autowired
  private AccountService accountService;

  /**
   * Gets the accounts.
   *
   * @param httpServletRequest the http servlet request
   * @param authorizationTokenId the authorization token id
   * @return the accounts
   */
  @GetMapping(value = "/api/accounts")
  public ResponseEntity<List<AccountDto>> getAccounts(HttpServletRequest httpServletRequest,
      @RequestHeader(value = "Authorization", required = true) String authorizationTokenId) {

    CognitoUser cognitoUser = (CognitoUser) httpServletRequest.getAttribute("user");
    log.info("Retrieving accounts for user [{}]", cognitoUser);

    return new ResponseEntity<>(accountService.getAccounts(cognitoUser), HttpStatus.OK);
  }
  
  /**
   * Post account.
   *
   * @param httpServletRequest the http servlet request
   * @param authorizationTokenId the authorization token id
   * @param account the account
   * @return the response entity
   */
  @PostMapping(value = "/api/accounts")
  public ResponseEntity<String> postAccount(
      HttpServletRequest httpServletRequest,
      @RequestHeader(value = "Authorization", required = true) String authorizationTokenId,
      @RequestBody AccountDto account
      ) {
    CognitoUser cognitoUser = (CognitoUser) httpServletRequest.getAttribute("user");
    log.info("Publish account [{}] for user [{}]", account, cognitoUser);
    
    this.accountService.publishAccount(cognitoUser, account);
    
    return new ResponseEntity<>(null, HttpStatus.OK);
  }
  
  /**
   * Delete account.
   *
   * @param httpServletRequest the http servlet request
   * @param authorizationTokenId the authorization token id
   * @param account the account
   * @return the response entity
   */
  @PostMapping(value = "/api/accounts/delete")
  public ResponseEntity<String> deleteAccount(
      HttpServletRequest httpServletRequest,
      @RequestHeader(value = "Authorization", required = true) String authorizationTokenId,
      @RequestBody AccountDto account
      ) {
    CognitoUser cognitoUser = (CognitoUser) httpServletRequest.getAttribute("user");
    log.info("Delete account [{}] for user [{}]", account.getAccountNumber(), cognitoUser);    
    this.accountService.dropAccount(cognitoUser, account);
    
    return new ResponseEntity<>(null, HttpStatus.OK);
  }
  
  @DeleteMapping(value = "/api/accounts")
  public ResponseEntity<String> deleteAccounts(
      HttpServletRequest httpServletRequest,
      @RequestHeader(value = "Authorization", required = true) String authorizationTokenId,
      @RequestBody AccountDto account
      ) {
    CognitoUser cognitoUser = (CognitoUser) httpServletRequest.getAttribute("user");
    log.info("Delete account [{}] for user [{}]", account, cognitoUser);    
    return new ResponseEntity<>(null, HttpStatus.OK);
  }
}
