package com.swdteam.client.dmu.data;

import java.util.List;

public class DMUData
{
  private List<Player> players;
  private int players_25565;
  private int max_players;
  
  public List<Player> getPlayersOnline() {
     return this.players;
  }
  
  public int getMax() {
     return this.max_players;
  }
  
  public int getOnline() {
     return this.players_25565;
  }
  
  public static class Player
  {
    public String getUsername() {
       return this.username;
    }
    
    private String username;
  }
}


