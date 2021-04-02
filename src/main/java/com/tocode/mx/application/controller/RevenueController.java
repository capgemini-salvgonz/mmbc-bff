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
* Nombre de archivo: RevenueController.java 
* Autor: salvgonz 
* Fecha de creación: 2 abr. 2021 
*/

package com.tocode.mx.application.controller;

import com.tocode.mx.application.dto.CognitoUser;
import com.tocode.mx.application.dto.RevenueDto;
import com.tocode.mx.application.service.RevenueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.servlet.http.HttpServletRequest;



/**
 * The Class RevenueController.
 */
@RestController
public class RevenueController {

  @Autowired
  private RevenueService revenueService;
  
  /**
   * Gets the revenue entries.
   *
   * @return the revenue entries
   */
  @GetMapping(value = "/api/revenues")
  public ResponseEntity<List<RevenueDto>> getRevenueEntries(
      HttpServletRequest request,
      @RequestHeader(value = "Authorization", required = true) String authorization){
    
    CognitoUser cognitoUser = (CognitoUser) request.getAttribute("user");    
    return new ResponseEntity<>(this.revenueService.getRevenueList(cognitoUser), HttpStatus.OK);
  }
  
  /**
   * Post revenue entry.
   *
   * @param request the request
   * @param authorization the authorization
   * @param revenueDto the revenue dto
   * @return the response entity
   */
  @PostMapping(value = "/api/revenues")
  public ResponseEntity<String> postRevenueEntry(
      HttpServletRequest request,
      @RequestHeader(value = "Authorization", required = true) String authorization,
      @RequestBody RevenueDto revenueDto){

    this.revenueService.saveRevenueEntry(revenueDto, (CognitoUser) request.getAttribute("user"));
    return new ResponseEntity<>(null, HttpStatus.OK);
  }
  
  /**
   * Delete revenue entry.
   *
   * @param request the request
   * @param authorization the authorization
   * @param revenueDto the revenue dto
   * @return the response entity
   */
  @PostMapping(value = "/api/revenues/delete")
  public ResponseEntity<String> deleteRevenueEntry(
      HttpServletRequest request,
      @RequestHeader(value = "Authorization", required = true) String authorization,
      @RequestBody RevenueDto revenueDto){

    this.revenueService.deleteRevenueElement
      (revenueDto, (CognitoUser) request.getAttribute("user"));
    return new ResponseEntity<>(null, HttpStatus.OK);
  }
}
