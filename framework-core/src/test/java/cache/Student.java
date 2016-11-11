package cache;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Student implements Serializable {
  String name;
  String idcard;
  String sex;

  public Student(String name, String idcard, String sex) {
    super();
    this.name = name;
    this.idcard = idcard;
    this.sex = sex;
  }
}
