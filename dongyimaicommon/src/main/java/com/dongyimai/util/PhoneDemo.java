package com.dongyimai.util;

public class PhoneDemo {


    public static void main(String[] args) {

        int[] arr = {0,1,2,3,4,5,6,7,8,9};
        //180 1352 2345
        String myPhone = "17112411234";

        int[] mypone = {1,7,1,1,2,4,1,1,2,3,4};

        String telPhone = "";

        for(int i=0;i<mypone.length;i++){
            System.out.println("index - " +i +"---"+ mypone[i]);
            System.out.println("arr - " + i +"==="+arr[mypone[i]]);

            telPhone += arr[mypone[i]] +"";
        }

        System.out.println("phone : " + telPhone);
    }

}
