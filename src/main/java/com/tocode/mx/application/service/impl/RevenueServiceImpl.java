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
* Nombre de archivo: RevenueServiceImpl.java 
* Autor: salvgonz 
* Fecha de creación: 1 abr. 2021 
*/

package com.tocode.mx.application.service.impl;

import com.tocode.mx.application.dto.CognitoUser;
import com.tocode.mx.application.dto.RevenueDto;
import com.tocode.mx.application.mapper.RevenueMapper;
import com.tocode.mx.application.repository.RevenueRepository;
import com.tocode.mx.application.service.RevenueService;
import com.tocode.mx.application.service.UserService;
import com.tocode.mx.model.Revenue;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

/**
 * The Class RevenueServiceImpl.
 */
@Service
public class RevenueServiceImpl implements RevenueService {

  /** The user service. */
  private UserService userService;

  /** The revenue repository. */
  private RevenueRepository revenueRepository;

  /**
   * Instantiates a new revenue service impl.
   *
   * @param userService the user service
   * @param revenueRepository the revenue repository
   */
  public RevenueServiceImpl(UserService userService, RevenueRepository revenueRepository) {
    this.userService = userService;
    this.revenueRepository = revenueRepository;
  }

  /**
   * Gets the revenue list.
   *
   * @param cognitoUser the cognito user
   * @return the revenue list
   */
  @Override
  public List<RevenueDto> getRevenueList(CognitoUser cognitoUser) {
    return this.userService
      .getUserUsingEmail(cognitoUser.getEmail())
      .map(u -> u.getUserId())
      .map(this.revenueRepository::findByUserId)
      .orElse(null).stream()
      .map(RevenueMapper::transform)
      .collect(Collectors.toList());
  }

  /**
   * Save revenue entry.
   *
   * @param revenueDto the revenue dto
   * @param cognitoUser the cognito user
   */
  @Override
  @Transactional
  public void saveRevenueEntry(RevenueDto revenueDto, CognitoUser cognitoUser) {
    
    Consumer<Revenue> revenueConsumer = revenueDto.getRevenueId() == null ? 
        this.revenueRepository::save : this.revenueRepository::updateRevenue;
    
    this.userService
      .getUserUsingEmail(cognitoUser.getEmail())
      .map(u -> {
        Revenue revenue = RevenueMapper.from(revenueDto);
        revenue.setUserId(u.getUserId());
        return revenue;})
      .ifPresent(revenueConsumer);
  }

  /**
   * Delete revenue element.
   *
   * @param revenueDto the revenue dto
   * @param cognitoUser the cognito user
   */
  @Override
  @Transactional
  public void deleteRevenueElement(final RevenueDto revenueDto, CognitoUser cognitoUser) {
    
    this.userService
    .getUserUsingEmail(cognitoUser.getEmail())
    .map(u -> u.getUserId())
    .ifPresent(u -> this.revenueRepository.deleteRevenue(revenueDto.getRevenueId(), u));
  }
}
