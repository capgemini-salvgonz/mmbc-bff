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
* Nombre de archivo: ExpenseTypeMapper.java 
* Autor: salvgonz 
* Fecha de creación: 2 abr. 2021 
*/

package com.tocode.mx.application.mapper;

import com.tocode.mx.application.dto.ExpenseTypeDto;
import com.tocode.mx.model.ExpenseType;

/**
 * The Class ExpenseTypeMapper.
 */
public class ExpenseTypeMapper {

  /**
   * Transform.
   *
   * @param expenseType the expense type
   * @return the expense type dto
   */
  public static ExpenseTypeDto transform(ExpenseType expenseType) {
    return new ExpenseTypeDto(
        expenseType.getExpenseTypeId(), 
        expenseType.getDescription(),
        expenseType.getUserId() != null 
        );
  }

  /**
   * From.
   *
   * @param expenseTypeDto the expense type dto
   * @return the expense type
   */
  public static ExpenseType from(ExpenseTypeDto expenseTypeDto) {
    ExpenseType expenseType = new ExpenseType();
    expenseType.setDescription(expenseTypeDto.getDescription());
    expenseType.setExpenseTypeId(expenseTypeDto.getExpenseTypeId());

    return expenseType;
  }
}
