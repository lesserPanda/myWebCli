package com.lesserPanda.module.myWeb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lesserPanda.module.myWeb.bean.CmsComment;

import net.tuxun.core.base.service.IbaseService;
/**
 * 
 * @author pand
 *
 */
@Service
public interface ICmsCommentService extends IbaseService{
  
  void removeByPrimaryTableId(String primaryTableId);

  List<CmsComment> listByPrimaryTableId(String primaryTableId);
  
}
