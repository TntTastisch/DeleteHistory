package de.tnttastisch.utils.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
public class InOut {

  public String readString(String prompt) {
    java.lang.System.out.print(prompt);
    return readln();
  }
  private String readln() {
    try {
      return Input.readLine();
    } catch(Exception e) {
      return "";
    }
  }

  static BufferedReader Input;

  static {
    try {
      Input = new BufferedReader(new InputStreamReader(java.lang.System.in));
    }
    catch (Exception e) {
      System.out.println("An error occurred while executing this system. Contact the system administrator! Code: F6vGgR1A");
    }
  }
}
