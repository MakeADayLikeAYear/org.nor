package org.nor.tutorials.java.generics.g.genTypeInference;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import org.nor.tutorials.java.generics.d.bounded.Box;

public class BoxUtilTest {

  @Test
  public void outputBoxes() {
    java.util.ArrayList<Box<Integer>> listOfIntegerBoxes = new java.util.ArrayList<>();
    BoxUtil.<Integer>addBox(Integer.valueOf(10), listOfIntegerBoxes);
    BoxUtil.addBox(Integer.valueOf(20), listOfIntegerBoxes);
    BoxUtil.addBox(Integer.valueOf(30), listOfIntegerBoxes);
    BoxUtil.outputBoxes(listOfIntegerBoxes);

    assertEquals(listOfIntegerBoxes.get(2).get(), 30);
  }

  @Test
  public void test() {
    // List<String> listOne = Collections.<String>emptyList();
    processStringList(Collections.emptyList());
    processStringList(Collections.<String>emptyList());

  }

  void processStringList(List<String> stringList) {
    // process stringList
  }

}
