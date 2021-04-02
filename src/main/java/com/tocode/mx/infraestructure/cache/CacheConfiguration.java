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
* Nombre de archivo: CacheConfiguration.java 
* Autor: salvgonz 
* Fecha de creación: 2 abr. 2021 
*/

package com.tocode.mx.infraestructure.cache;

import lombok.extern.slf4j.Slf4j;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;


/**
 * The Class CacheConfiguration.
 * <p>
 * This class adds Spring's @EnableCaching annotation to a Spring bean so that Spring's
 * annotation-driven cache management is enabled.
 * 
 * Spring's auto-configuration finds Ehcache's implementation of JSR-107. However, 
 * no caches are created by default.
 * Because neither Spring nor Ehcache looks for a default ehcache.xml file. 
 * We add the following property to tell Spring where to find it: 
 * spring.cache.jcache.config=classpath:ehcache.xml
 * </p>
 * <a href="https://www.baeldung.com/spring-boot-ehcache">More info at www.baeldung.com</a>
*/

@Configuration
@EnableCaching
@Slf4j
public class CacheConfiguration implements CacheEventListener<Object, Object>{
  
  /**
   * On event.
   *
   * @param cacheEvent the cache event
   */
  @Override
  public void onEvent(CacheEvent<? extends Object, ? extends Object> cacheEvent) {
      log.info("Cache event {} for item with key {}. Old value = {}, New value = {}", 
          cacheEvent.getType(), 
          cacheEvent.getKey(), 
          cacheEvent.getOldValue(), 
          cacheEvent.getNewValue());
  }
}
