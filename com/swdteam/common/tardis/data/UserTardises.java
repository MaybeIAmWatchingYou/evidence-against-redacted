package com.swdteam.common.tardis.data;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserTardises {
   private List<Integer> tardises = new ArrayList<>();

  public List<Integer> getTardises() {
     return this.tardises;
  }

  public void addTardis(int i) {
     if (!this.tardises.contains(Integer.valueOf(i))) this.tardises.add(Integer.valueOf(i));
  }

  public void removeTardis(int i) {
     List<Integer> n = new ArrayList<>();
     if (this.tardises.contains(Integer.valueOf(i))) for (Iterator<Integer> iterator = this.tardises.iterator(); iterator.hasNext(); ) { int id = ((Integer)iterator.next()).intValue(); if (id != i) n.add(Integer.valueOf(id));  }
         this.tardises = n;
  }
}


