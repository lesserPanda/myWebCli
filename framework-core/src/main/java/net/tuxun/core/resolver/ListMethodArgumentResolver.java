package net.tuxun.core.resolver;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

// extends HandlerMethodArgumentResolverComposite
public class ListMethodArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(ListRequestParam.class);

  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    ListRequestParam lrp = parameter.getParameterAnnotation(ListRequestParam.class);
    Class<?> cla = lrp.bean();

    List list = new ArrayList();
    Field[] fields = cla.getDeclaredFields();

    if (lrp.findSuperClass()) {
      Class superclass = cla.getSuperclass();
      Field[] superFields = superclass.getDeclaredFields();
      fields = merge(fields, superFields);
    }

    String[] ids = webRequest.getParameterValues("id");

    // 获取动态关联的字段值
    String[] rels =
        lrp.relevance().equals("") ? new String[0] : webRequest.getParameterValues(lrp.relevance());

    for (int i = 0; i < ids.length; i++) {
      Object obj = cla.newInstance();
      Method setId = cla.getMethod("setId", String.class);
      setId.invoke(obj, ids[i]);


      for (Field field : fields) {
        if (field.getName().equals("id"))
          continue;

        String setm =
            "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        Method setM = null;
        try {
          setM = cla.getMethod(setm, field.getType());
        } catch (Exception e) {
          continue;
        }

        if (!lrp.relevance().equals("")) {

          String rel = rels[i];

          String[] relvalues = webRequest.getParameterValues(rel + "_" + field.getName());
          if (relvalues != null) {
            setM.invoke(obj, new Object[] {relvalues});
            continue;
          }
        }



        String[] fn = webRequest.getParameterValues(field.getName());
        if (fn == null || fn.length <= i) {
          continue;
        }
        setM.invoke(obj, getValue(fn[i], field));
      }

      list.add(obj);
    }

    // String[] rels= webRequest.getParameterValues(lrp.relevance());

    return list;
  }

  public <T> T[] merge(T[] t1, T[] t2) {
    List<T> list = new ArrayList<T>();
    list.addAll(Arrays.asList(t1));
    list.addAll(Arrays.asList(t2));
    T[] ts = list.toArray(t1);
    return ts;
  }


  public Object getValue(String paramvalue, Field field) throws Exception {
    Class type = field.getType();
    if (type == String.class) {
      return paramvalue;
    } else if (type == Integer.class || type.getName().equals("int")) {
      return Integer.valueOf(paramvalue);
    } else if (type == Float.class) {
      return Float.valueOf(paramvalue);
    } else if (type == Double.class) {
      return Double.valueOf(paramvalue);
    } else if (type == Date.class) {
      SimpleDateFormat df = null;
      if (paramvalue.matches("\\d{4}-\\d{2}-\\d{2}")) {
        df = new SimpleDateFormat("yyyy-MM-dd");
      } else if (paramvalue.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}")) {
        df = new SimpleDateFormat("yyyy-MM-dd HH");
      } else if (paramvalue.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")) {
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      } else if (paramvalue.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      }

      return df.parse(paramvalue);
    }

    return null;

  }

}
