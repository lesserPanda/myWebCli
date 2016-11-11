package net.tuxun.core.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tuxun.core.base.dao.IQueryDao;
import net.tuxun.core.mybatis.page.Collate;
import net.tuxun.core.mybatis.page.PageAttr;

/**
 * 实现返回最终结果的方法。这样子类就可以专注于设置条件方法的实现。
 * 
 * @author Administrator
 * @param <T>
 */
public abstract class AbstractQuery<T> implements Query<T> {

  protected Map<String, Object> search = new HashMap<String, Object>();
  protected List<Collate> collates = new ArrayList<Collate>();

  public long count() {
    return getDao().selectAllCount(search, collates);
  }

  public T singleResult() {
    List<T> list = listPage(new PageAttr(1, 1));
    if (list.isEmpty())
      return null;
    else
      return list.get(0);
  }

  public List<T> list() {
    return getDao().selectAll(search, collates);
  }

  public List<T> listPage(PageAttr attr) {
    return getDao().selectAllPage(attr, search, collates);
  }

  @Override
  public void clearParam() {
    search.clear();
    collates.clear();
  }

  public abstract IQueryDao<T> getDao();

}
