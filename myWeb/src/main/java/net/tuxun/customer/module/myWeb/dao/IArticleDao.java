package net.tuxun.customer.module.myWeb.dao;

import net.tuxun.core.base.dao.IBaseDao;
import net.tuxun.customer.module.myWeb.bean.Article;
/**
 * 
 * @author pand
 *
 */
public interface IArticleDao extends IBaseDao {
	Article selectFull(String id);
}
