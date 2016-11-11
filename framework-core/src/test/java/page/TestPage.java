package page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tuxun.core.mybatis.page.PageAttr;
import net.tuxun.core.mybatis.page.PageNav;
import net.tuxun.core.mybatis.page.PageQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import page.animal.bean.Wolf;
import page.animal.service.IWolfService;

/**
 * mybatis物理分页测试
 * 
 * @author liuqiang
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/page/application.xml")
public class TestPage {

  protected Logger log = LoggerFactory.getLogger(TestPage.class);

  @Autowired
  private IWolfService wolfService;

  // 插入10条数据
  // @Transactional
  @Test
  public void insertWolfs() {
    for (int i = 0; i < 10; i++) {
      wolfService.save(new Wolf(null, "品种-" + i, "毛色-" + i, i));
    }
  }

  // 取得第1页数据
  @Test
  public void getFirstPage() {
    PageAttr attr = new PageAttr();
    attr.setPageCur(1);
    attr.setInfoSize(5);
    Map<String, String> search = new HashMap<String, String>();
    PageQuery query = new PageQuery(attr, search);
    PageNav<Wolf> wolfs = wolfService.pageResult(query);
    printList(wolfs.getList());
  }

  // 取得第2页数据
  @Test
  public void getSecondPage() {
    PageAttr attr = new PageAttr();
    attr.setPageCur(2);
    attr.setInfoSize(5);
    Map<String, String> search = new HashMap<String, String>();
    PageQuery query = new PageQuery(attr, search);
    PageNav<Wolf> wolfs = wolfService.pageResult(query);
    printList(wolfs.getList());
  }

  // 测试搜索条件
  @Test
  public void pageSearch() {
    PageAttr attr = new PageAttr();
    attr.setPageCur(1);
    attr.setInfoSize(5);
    Map<String, String> search = new HashMap<String, String>();
    PageQuery query = new PageQuery(attr, search);
    query.search("variety", "品种-5");
    PageNav<Wolf> wolfs = wolfService.pageResult(query);
    printList(wolfs.getList());
  }

  // 测试排序
  @Test
  public void pageCollate() {
    PageAttr attr = new PageAttr();
    attr.setPageCur(1);
    attr.setInfoSize(5);
    Map<String, String> search = new HashMap<String, String>();
    PageQuery query = new PageQuery(attr, search);
    query.order("weight", "DESC");
    PageNav<Wolf> wolfs = wolfService.pageResult(query);
    printList(wolfs.getList());
  }

  // 测试缓存
  // 测试结果如下
  // 1.SQL语句、查询条件构成cacheKey, 即分页属性、排序发生改变，不影响缓存的使用
  // 2.SQL语句、分页属性、排序、查询条件构成cacheKey, 即任何一条件发生改变都会重新从数据库中读取数据
  // @See org.apache.ibatis.executor.CachingExecutor#query debug
  @Test
  public void pageCache() {
    PageAttr attr = new PageAttr();
    attr.setPageCur(1);
    attr.setInfoSize(2);
    Map<String, String> search = new HashMap<String, String>();
    PageQuery query = new PageQuery(attr, search);
    query.orderDefault("weight", "ASC");
    // 第一次请求
    PageNav<Wolf> wolfs1 = wolfService.pageResult(query);
    printList(wolfs1.getList());
    // 同样的条件再请求一次
    PageNav<Wolf> wolfs2 = wolfService.pageResult(query);
    printList(wolfs2.getList());

    // 更改分页属性
    attr.setInfoSize(3);
    PageNav<Wolf> wolfs3 = wolfService.pageResult(query);
    printList(wolfs3.getList());
    // 同样的条件再请求一次
    PageNav<Wolf> wolfs4 = wolfService.pageResult(query);
    printList(wolfs4.getList());

    // 更改查询条件
    query.search("variety", "品种-5");
    PageNav<Wolf> wolfs5 = wolfService.pageResult(query);
    printList(wolfs5.getList());
    // 同新的条件再请求一次
    PageNav<Wolf> wolfs6 = wolfService.pageResult(query);
    printList(wolfs6.getList());

    // 更改排序状态
    query.orderDefault("weight", "DESC");
    PageNav<Wolf> wolfs7 = wolfService.pageResult(query);
    printList(wolfs7.getList());
    // 同新的条件再请求一次
    PageNav<Wolf> wolfs8 = wolfService.pageResult(query);
    printList(wolfs8.getList());
  }

  private void printList(List<Wolf> list) {
    Assert.notEmpty(list);
    log.info("-----------------返回的数据---------------------");
    for (Wolf wolf : list) {
      log.info("id={}, variety={}, color={}, weight={}", wolf.getId(), wolf.getVariety(),
          wolf.getColor(), wolf.getWeight());
    }
    log.info("-----------------返回的数据---------------------");
  }
}
