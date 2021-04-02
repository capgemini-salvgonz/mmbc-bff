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
* Nombre de archivo: UserServiceImpl.java 
* Autor: salvgonz 
* Fecha de creación: 2 abr. 2021 
*/

package com.tocode.mx.application.service.impl;

import com.tocode.mx.application.dto.CognitoUser;
import com.tocode.mx.application.mapper.UserMapper;
import com.tocode.mx.application.repository.UserRepository;
import com.tocode.mx.application.service.UserService;
import com.tocode.mx.model.User;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The Class UserServiceImpl.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

  /** The user repository. */
  private UserRepository userRepository;

  /**
   * Instantiates a new user service impl.
   *
   * @param userRepository the user repository
   */
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Adds the new user.
   *
   * @param cognitoUser the cognito user
   */
  @Override
  public void addNewUser(CognitoUser cognitoUser) {
    User user = this
        .getUserUsingEmail(cognitoUser.getEmail())
        .orElseGet(() -> this.saveCognitoUser(cognitoUser));
    log.info("User [{}]", user);
  }

  /**
   * Save cognito user.
   *
   * @param cognitoUser the cognito user
   * @return the user
   */
  private User saveCognitoUser(CognitoUser cognitoUser) {
    User user = UserMapper.from(cognitoUser);
    return this.userRepository.save(user);
  }

  /**
   * Gets the user using email.
   *
   * @param email the email
   * @return the user using email
   */
  @Override
  public Optional<User> getUserUsingEmail(String email) {
    return userRepository.findByEmail(email);
  }  

}
