/**
 * 
 */
package org.nor.xml.model;

/**
 * @author Administrator
 *
 */
public class Item {
  /** 
   * 
   */
  private String date;
  /**
   * 
   */
  private String mode;
  /**
   * 
   */
  private String unit;
  /**
   * 
   */
  private String current;
  /**
   * 
   */
  private String interactive;

  /**
   * @return String
   */
  public final String getDate() {
    return date;
  }

  /**
   * @param d : date
   */
  public final void setDate(final String d) {
    this.date = d;
  }

  /**
   * @return String
   */
  public final String getMode() {
    return mode;
  }

  /**
   * @param m : mode
   */
  public final void setMode(final String m) {
    this.mode = m;
  }

  /**
   * @return String
   */
  public final String getUnit() {
    return unit;
  }

  /**
   * @param u : unit
   */
  public final void setUnit(final String u) {
    this.unit = u;
  }

  /**
   * @return String
   */
  public final String getCurrent() {
    return current;
  }

  /**
   * @param c : current
   */
  public final void setCurrent(final String c) {
    this.current = c;
  }

  /**
   * @return String
   */
  public final String getInteractive() {
    return interactive;
  }

  /**
   * @param i : interactive
   */
  public final void setInteractive(final String i) {
    this.interactive = i;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public final String toString() {
    return "Item [current=" + current + ", date=" + date + ", interactive="
        + interactive + ", mode=" + mode + ", unit=" + unit + "]";
  } 
}
