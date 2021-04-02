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
* Nombre de archivo: RevenueMapper.java 
* Autor: salvgonz 
* Fecha de creación: 1 abr. 2021 
*/

package com.tocode.mx.application.mapper;

import com.tocode.mx.application.dto.RevenueDto;
import com.tocode.mx.model.Revenue;

/**
 * The Class RevenueMapper.
 */
public class RevenueMapper {

  /**
   * From.
   *
   * @param revenueDto the revenue dto
   * @return the revenue
   */
  public static Revenue from(RevenueDto revenueDto) {
    Revenue revenue = new Revenue();
    revenue.setAmount(revenueDto.getAmount());
    revenue.setDescription(revenueDto.getDescription());
    revenue.setRevenueId(revenueDto.getRevenueId());
    revenue.setUserId(null);

    return revenue;
  }

  /**
   * Transform.
   *
   * @param revenue the revenue
   * @return the revenue dto
   */
  public static RevenueDto transform(Revenue revenue) {
    RevenueDto revenueDto = new RevenueDto();
    revenueDto.setAmount(revenue.getAmount());
    revenueDto.setDescription(revenue.getDescription());
    revenueDto.setRevenueId(revenue.getRevenueId());

    return revenueDto;
  }
}
