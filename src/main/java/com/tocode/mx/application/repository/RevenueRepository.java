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
* Nombre de archivo: RevenueRepository.java 
* Autor: salvgonz 
* Fecha de creación: 2 abr. 2021 
*/

package com.tocode.mx.application.repository;

import com.tocode.mx.model.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * The Interface RevenueRepository.
 */
public interface RevenueRepository extends JpaRepository<Revenue, Long> {

  /**
   * Find all.
   *
   * @return the list
   */
  List<Revenue> findByUserId(Long userId);
  
  /**
   * Delete revenue.
   *
   * @param revenueId the revenue id
   * @param userId the user id
   */
  @Modifying
  @Query(value = "DELETE FROM revenue WHERE id_revenue=:revenueId AND id_user=:userId",
    nativeQuery = true) 
  void deleteRevenue(@Param("revenueId")Long revenueId, @Param("userId") Long userId);  
  
  /**
   * Update revenue.
   *
   * @param revenue the revenue
   * @return the revenue
   */
  @Modifying
  @Query(value = 
        "UPDATE revenue "
      + "SET description=:#{#revenue.description}, amount=:#{#revenue.amount} "
      + "WHERE id_revenue=:#{#revenue.revenueId} AND id_user=:#{#revenue.userId}", 
    nativeQuery = true)
  void updateRevenue(@Param("revenue") Revenue revenue);
  
  /**
   * Total revenue.
   *
   * @param userId user id
   * @return float
   */
  @Query(value = "SELECT SUM(r.amount) FROM revenue r WHERE r.id_user=:userId",
    nativeQuery = true)
  Float totalRevenue(@Param("userId") Long userId);
}
