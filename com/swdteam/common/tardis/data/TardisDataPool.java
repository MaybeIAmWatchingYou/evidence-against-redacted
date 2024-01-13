package com.swdteam.common.tardis.data;

import com.swdteam.common.tardis.TardisSaveHandler;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.MinecraftServer;














public class TardisDataPool
  implements Serializable
{
  private static final long serialVersionUID = -7376469716096016050L;
  private transient MinecraftServer server;
  public static TardisDataPool INSTANCE;
   private List<TardisClaimState> tardisList = new ArrayList<>();





  public int generateNextFreeID() {
     if (this.tardisList != null) {

       for (int i = 0; i < this.tardisList.size(); i++) {
         TardisClaimState tardisClaimState = this.tardisList.get(i);
         if (tardisClaimState.isAvailble()) {
           return tardisClaimState.getId();
        }
      }

       TardisClaimState state = new TardisClaimState();
       this.tardisList.add(state);

       TardisSaveHandler.saveTardisPool(this.server);

       return state.getId();
    }

     return 0;
  }


  public void setServer(MinecraftServer server) {
     this.server = server;
  }

  public MinecraftServer getServer() {
     return this.server;
  }

  private class TardisClaimState
    implements Serializable {
    private static final long serialVersionUID = -1392020351136784498L;
    private int id;
    private boolean availble = false;

    public TardisClaimState() {
       this.availble = false;
       this.id = TardisDataPool.this.tardisList.size();
    }

    public int getId() {
       return this.id;
    }

    public boolean isAvailble() {
       return this.availble;
    }
  }
}


