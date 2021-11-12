package com.example.schooltest;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;

public class Subject {
    private String name;
    private int color;
    private String teacher;
    private int percentWrite;
    private int percentSpeak;
    private ArrayList<Integer> c1W;
    private ArrayList<Integer> c2W;
    private ArrayList<Integer> c3W;
    private ArrayList<Integer> c4W;
    private ArrayList<Integer> c1S;
    private ArrayList<Integer> c2S;
    private ArrayList<Integer> c3S;
    private ArrayList<Integer> c4S;
    private double averageC1;
    private double averageC2;
    private double averageC3;
    private double averageC4;
    private double averageTotal;
    public static double overAll = 0;

    private int isVisible;

    public Subject(String name, int color, String teacher,
                   int percentWrite, int percentSpeak, ArrayList<Integer> c1W,
                   ArrayList<Integer> c2W, ArrayList<Integer> c3W,
                   ArrayList<Integer> c4W, ArrayList<Integer> c1S,
                   ArrayList<Integer> c2S, ArrayList<Integer> c3S,
                   ArrayList<Integer> c4S, int isVisible){
        this.isVisible = isVisible;
        this.name = name;
        this.color = color;
        this.teacher = teacher;
        this.percentWrite = percentWrite;
        this.percentSpeak = percentSpeak;
        this.c1W = new ArrayList<>(c1W);
        this.c2W = new ArrayList<>(c2W);
        this.c3W = new ArrayList<>(c3W);
        this.c4W = new ArrayList<>(c4W);
        this.c1S = new ArrayList<>(c1S);
        this.c2S = new ArrayList<>(c2S);
        this.c3S = new ArrayList<>(c3S);
        this.c4S = new ArrayList<>(c4S);


        averageC1 = getAverage(c1W, percentWrite) + getAverage(c1S, percentSpeak);
        averageC2 = getAverage(c2W, percentWrite) + getAverage(c2S, percentSpeak);
        averageC3 = getAverage(c3W, percentWrite) + getAverage(c3S, percentSpeak);
        averageC4 = getAverage(c4W, percentWrite) + getAverage(c4S, percentSpeak);

        averageTotal = countAverageTotal(averageC1, averageC2, averageC3, averageC4);



    }

    public static void recountOverAll(ArrayList<Subject> theSubjects){
        overAll = 0;
        for (Subject i: theSubjects) {
            overAll = overAll + i.getAverageTotal();
        }
        overAll = Math.round((overAll/theSubjects.size())*100.0)/100.0;
    }


    private double getAverage(ArrayList<Integer> grades, int percent){
        double average = 0.0;
        double myPercent = percent;
        myPercent = myPercent/100;
        for (int i: grades) {
            average = average + i;

        }

        average = average/(grades.size());
        average = average*myPercent;
        average = Math.round(average*10.0)/10.0;
        if(average > 0.0){
            return average;
        }
        return 15.0;
    }

    private double countAverageTotal(double c1, double c2, double c3, double c4){
        double average = c1 + c2 + c3 + c4;
        int counter = countFull(c1) + countFull(c2) + countFull(c3) + countFull(c4);
        average = average/counter;
        average = Math.round(average*10.0)/10.0;
        return average;
    }

    private int countFull(double c){
        if(c != 0){
            return 1;
        }
        else {return 0;}
    }

    private void updateAverages(){
        averageC1 = getAverage(c1W, percentWrite) + getAverage(c1S, percentSpeak);
        averageC2 = getAverage(c2W, percentWrite) + getAverage(c2S, percentSpeak);
        averageC3 = getAverage(c3W, percentWrite) + getAverage(c3S, percentSpeak);
        averageC4 = getAverage(c4W, percentWrite) + getAverage(c4S, percentSpeak);

        averageTotal = countAverageTotal(averageC1, averageC2, averageC3, averageC4);
    }
    ////////////////////////////////////////////////////setter and getter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getPercentWrite() {
        return percentWrite;
    }

    public void setPercentWrite(int percentWrite) {
        this.percentWrite = percentWrite;
    }

    public int getPercentSpeak() {
        return percentSpeak;
    }

    public void setPercentSpeak(int percentSpeak) {
        this.percentSpeak = percentSpeak;
    }

    public ArrayList<Integer> getC1W() {
        return c1W;
    }

    public void setC1W(ArrayList<Integer> c1W) {
        this.c1W = new ArrayList<>(c1W);
        updateAverages();
    }

    public ArrayList<Integer> getC2W() {
        return c2W;
    }

    public void setC2W(ArrayList<Integer> c2W) {
        this.c2W = new ArrayList<>(c2W);
        updateAverages();
    }

    public ArrayList<Integer> getC3W() {
        return c3W;
    }

    public void setC3W(ArrayList<Integer> c3W) {
        this.c3W = new ArrayList<>(c3W);
        updateAverages();
    }

    public ArrayList<Integer> getC4W() {
        return c4W;
    }

    public void setC4W(ArrayList<Integer> c4W) {
        this.c4W = new ArrayList<>(c4W);
        updateAverages();
    }

    public ArrayList<Integer> getC1S() {
        return c1S;
    }

    public void setC1S(ArrayList<Integer> c1S) {
        this.c1S = new ArrayList<>(c1S);
        updateAverages();
    }

    public ArrayList<Integer> getC2S() {
        return c2S;
    }

    public void setC2S(ArrayList<Integer> c2S) {
        this.c2S = new ArrayList<>(c2S);
        updateAverages();
    }

    public ArrayList<Integer> getC3S() {
        return c3S;
    }

    public void setC3S(ArrayList<Integer> c3S) {
        this.c3S = new ArrayList<>(c3S);
        updateAverages();
    }

    public ArrayList<Integer> getC4S() {
        return c4S;
    }

    public void setC4S(ArrayList<Integer> c4S) {
        this.c4S = new ArrayList<>(c4S);
        updateAverages();
    }

    public double getAverageC1() {
        return averageC1;
    }

    public void setAverageC1(double averageC1) {
        this.averageC1 = averageC1;
    }

    public double getAverageC2() {
        return averageC2;
    }

    public void setAverageC2(double averageC2) {
        this.averageC2 = averageC2;
    }

    public double getAverageC3() {
        return averageC3;
    }

    public void setAverageC3(double averageC3) {
        this.averageC3 = averageC3;
    }

    public double getAverageC4() {
        return averageC4;
    }

    public void setAverageC4(double averageC4) {
        this.averageC4 = averageC4;
    }

    public double getAverageTotal() {
        return averageTotal;
    }

    public void setAverageTotal(double averageTotal) {
        this.averageTotal = averageTotal;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

}
