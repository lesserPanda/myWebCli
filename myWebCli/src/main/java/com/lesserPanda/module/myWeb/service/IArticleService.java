package com.lesserPanda.module.myWeb.service;

import org.springframework.stereotype.Service;

import com.lesserPanda.module.myWeb.bean.Article;

import net.tuxun.core.base.service.IbaseService;
/**
 * 
 * @author pand
 *
 */
@Service
public interface IArticleService extends IbaseService{
  
  void saveFull(Article bean);

  Article getFull(String id);

  void modifyFull(Article bean);

  void removeFull(String id);
  
}
