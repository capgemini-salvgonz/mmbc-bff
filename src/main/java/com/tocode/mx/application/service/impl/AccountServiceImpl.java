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
* Nombre de archivo: AccountServiceImpl.java 
* Autor: salvgonz 
* Fecha de creación: 1 abr. 2021 
*/

package com.tocode.mx.application.service.impl;

import com.tocode.mx.application.dto.AccountDto;
import com.tocode.mx.application.dto.CognitoUser;
import com.tocode.mx.application.mapper.AccountMapper;
import com.tocode.mx.application.repository.AccountRepository;
import com.tocode.mx.application.service.AccountService;
import com.tocode.mx.application.service.UserService;
import com.tocode.mx.model.Account;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * The Class AccountServiceImpl.
 */
@Service
public class AccountServiceImpl implements AccountService {

  /** The account repository. */
  private AccountRepository accountRepository;  
  
  /** The user service. */
  private UserService userService;

  /**
   * Instantiates a new account service impl.
   *
   * @param accountRepository the account repository
   * @param userRepository the user repository
   */
  public AccountServiceImpl(AccountRepository accountRepository, UserService userService) {
    this.accountRepository = accountRepository;
    this.userService = userService;
  }

  /**
   * Gets the accounts.
   *
   * @param cognitoUser the cognito user
   * @return the accounts
   */
  @Override
  public List<AccountDto> getAccounts(CognitoUser cognitoUser) {
    
    return this.userService.getUserUsingEmail(cognitoUser.getEmail())
      .map(u -> u.getUserId())
      .map(this.accountRepository::findByUserId)      
      .orElse(new ArrayList<Account>())
      .stream()
      .map(AccountMapper::transform)
      .collect(Collectors.toList());
  }

  /**
   * Publish account.
   *
   * @param cognitoUser the cognito user
   * @param account the account dto
   */
  @Override
  public void publishAccount(CognitoUser cognitoUser, AccountDto accountDto) {
    this.getUserAndExecuteActionWithAccount(
        cognitoUser.getEmail(), 
        accountDto, 
        a -> this.accountRepository.save(a)); 
  }

  /**
   * Drop account.
   *
   * @param cognitoUser the cognito user
   * @param account the account
   */
  @Override
  public void dropAccount(CognitoUser cognitoUser, AccountDto accountDto) {
    
    this.getUserAndExecuteActionWithAccount(
        cognitoUser.getEmail(), 
        accountDto, 
        a -> this.accountRepository.delete(a));    
  }
  
  /**
   * Gets the user and execute action with account.
   *
   * @param email the email
   * @param accountDto the account dto
   * @param consumer the consumer
   * @return the user and execute action with account
   */
  private void getUserAndExecuteActionWithAccount(String email, AccountDto accountDto, 
      Consumer<Account> consumer) {
    this.userService.getUserUsingEmail(email)
      .map(u -> {
        Account account = AccountMapper.from(accountDto);
        account.setUserId(u.getUserId());        
        return account;
      })      
      .ifPresent(consumer);
  }
}
