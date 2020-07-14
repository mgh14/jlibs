package com.mgh14;

public class TimeCalc {
  public static final String format = "<hours>h<minutes>m";
  public static final String format2 = "hh:mm";

  public static void main(String[] args) {
    args = new String[] {"pn", "8:05", "13:04"};
    String startTime = args[1];
    String finishedTime = args[2];

    int startTimeColonIndex = startTime.indexOf(":");
    double startTimeHrs = Double.parseDouble(startTime.substring(0, startTimeColonIndex));
    double startTimeMins = Double.parseDouble(startTime.substring(startTimeColonIndex + 1));
    int endTimeColonIndex = finishedTime.indexOf(":");
    double endTimeHrs = Double.parseDouble(finishedTime.substring(0, endTimeColonIndex));
    double endTimeMins = Double.parseDouble(finishedTime.substring(endTimeColonIndex + 1));

    double totalMins = ((endTimeHrs * 60) + endTimeMins) - ((startTimeHrs * 60) + startTimeMins);
    double div = totalMins / 60;
    int totalHrs = (int) Math.floor(div);
    long adjustedMins = Math.round((div - totalHrs) * 60);
    System.out.println("Total minutes: " + totalMins);
    System.out.println("Total hours: " + totalHrs);
    System.out.println("Adjusted minutes: " + adjustedMins);
    System.out.println("Total time: " + String.format("%s:%s", totalHrs, adjustedMins));
  }
}
