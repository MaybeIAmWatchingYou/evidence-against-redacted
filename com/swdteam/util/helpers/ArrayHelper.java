package com.swdteam.util.helpers;

import com.swdteam.main.DalekMod;
import java.util.ArrayList;


public class ArrayHelper
{
  public static int findIndex(Object[] arr, Object t) {
     ArrayList<Object> clist = new ArrayList();
     for (Object i : arr) clist.add(i); 
     return clist.indexOf(t);
  }
  
  public static Object[] shuffle(Object[] arr) {
     for (int i = 0; i < arr.length; i++) {
       int index = DalekMod.RANDOM.nextInt(arr.length - i);
       Object tmp = arr[arr.length - 1 - i];
       arr[arr.length - 1 - i] = arr[index];
       arr[index] = tmp;
    } 
    
     return arr;
  }
}


