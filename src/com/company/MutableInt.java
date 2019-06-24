package com.company;

/*
* A class that allows the creation of a mutable int that can be set to specific values
* */
public class MutableInt
{
  private int i;

  public MutableInt()
  {
    this.i = 0;
  }

  public void increment()
  {
    this.i++;
  }

  public int getCount() {
    return this.i;
  }

  public int compareTo(MutableInt num)
  {
    if (this.i == num.getCount() ) {
      return 0;
    } else if (this.i < num.getCount()) {
      return -1;
    }
    else {
      return 1;
    }
  }

}
