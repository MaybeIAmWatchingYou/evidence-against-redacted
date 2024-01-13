package com.swdteam.util.world;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;




public class SchematicUtils
{
  public enum GenerationQueue
  {
     DEFAULT,
     TARDIS,
     CITADEL(4096);


     private int chunkSize = 512; private List<SchematicUtils.SchematicChunk> list;

    GenerationQueue() {
       this.list = new ArrayList<>();
    }

    GenerationQueue(int chunkSize) {
       this.list = new ArrayList<>();
       this.chunkSize = chunkSize;
    }

    public List<SchematicUtils.SchematicChunk> getList() {
       return this.list;
    }
  }

  public static class SchematicChunk
  {
    private final RegistryKey<World> worldID;
     private final List<SchematicChunkBlockState> blocks = new ArrayList<>();

    public SchematicChunk(World world) {
       this.worldID = world.getDimensionKey();
    }

    public List<SchematicChunkBlockState> getBlocks() {
       return this.blocks;
    }

    public RegistryKey<World> getWorld() {
       return this.worldID;
    }

    public static class SchematicChunkBlockState {
      private final BlockPos p;
      private final BlockState block;
       private String nbt = "";
      private SchematicUtils.IReplaceBlockSpawn blockUpdater;

      public SchematicChunkBlockState(BlockPos p, BlockState state) {
         this.p = p;
         this.block = state;
      }

      public SchematicChunkBlockState(BlockPos p, String nbt) {
         this.p = p;
         this.block = null;
         this.nbt = nbt;
      }

      public SchematicUtils.IReplaceBlockSpawn getBlockUpdater() {
         return this.blockUpdater;
      }

      public void setBlockUpdater(SchematicUtils.IReplaceBlockSpawn blockUpdater) {
         this.blockUpdater = blockUpdater;
      }

      public BlockState getBlock() {
         return this.block;
      }

      public String getNbt() {
         return this.nbt;
      }

      public BlockPos getPos() {
         return this.p; } } } public static class SchematicChunkBlockState { private final BlockPos p; private final BlockState block; public BlockPos getPos() { return this.p; }
    private String nbt = "";
    private SchematicUtils.IReplaceBlockSpawn blockUpdater;
    public SchematicChunkBlockState(BlockPos p, BlockState state) { this.p = p;
      this.block = state; }
    public SchematicChunkBlockState(BlockPos p, String nbt) { this.p = p;
      this.block = null;
      this.nbt = nbt; }
    public SchematicUtils.IReplaceBlockSpawn getBlockUpdater() { return this.blockUpdater; } public void setBlockUpdater(SchematicUtils.IReplaceBlockSpawn blockUpdater) { this.blockUpdater = blockUpdater; } public BlockState getBlock() { return this.block; } public String getNbt() { return this.nbt; } }
   public static Schematic loadSchematic(String file, FileLocation location) {
     Schematic schem = null;
     if (location == FileLocation.INTERNAL) {
       InputStream stream = SchematicUtils.class.getResourceAsStream("/data/dalekmod/structures/" + file + ".schm");

      try {
         ObjectInputStream ois = new ObjectInputStream(stream);
         Object o = ois.readObject();
         ois.close();
         if (o instanceof Schematic) {
           schem = (Schematic)o;
        }
       } catch (Exception e) {
         e.printStackTrace();
      } finally {
         if (stream != null) {
          try {
             stream.close();
           } catch (IOException e) {
             e.printStackTrace();
          }

        }
      }
     } else if (location == FileLocation.EXTERNAL) {

      try {
         FileInputStream stream = new FileInputStream(file);
         ObjectInputStream ois = new ObjectInputStream(stream);
         Object o = ois.readObject();

         if (o instanceof Schematic) {
           schem = (Schematic)o;
        }

         ois.close();
       } catch (Exception e) {
         e.printStackTrace();
      }
    }


     return schem;
  }
  public static void main(String[] args) {}

  public static void saveSchematic(Schematic schem, String fileName) {
     String fileDir = "schematics/dalekmod/";
     fileName = fileName + ".schm";

    try {
       File file = new File(fileDir + fileName);
       file.getParentFile().mkdirs();
       file.createNewFile();
       FileOutputStream out = new FileOutputStream(file, false);
       ObjectOutputStream stream = new ObjectOutputStream(out);
       stream.writeObject(schem);
       stream.close();
     } catch (Exception e) {
       e.printStackTrace();
    }
  }

  public static void generateSchematic(GenerationQueue queue, World w, BlockPos p, Schematic schem) {
     if (schem != null) {
       generateSchematic(queue, w, p, schem, null, null, null);
    }
  }

  public static void generateSchematic(GenerationQueue queue, World w, BlockPos p, Schematic schem, BlockState[] blocksToIgnore, BlockState airBlock) {
     generateSchematic(queue, w, p, schem, blocksToIgnore, airBlock, null);
  }


  public static void generateSchematic(GenerationQueue queue, World w, BlockPos p, Schematic schem, BlockState[] blocksToIgnore, BlockState airBlock, IReplaceBlockSpawn[] blockReplace) {
     if (schem == null) {
      return;
    }



     int chunkSize = schem.getSchemDimX() * schem.getSchemDimY() * schem.getSchemDimZ();
     int index = 0;

     List<SchematicChunk.SchematicChunkBlockState> ents = new ArrayList<>();

     SchematicChunk chunk = new SchematicChunk(w);
    int i;
     for (i = 0; i <= schem.getSchemDimY(); i++) {
       int y = i;

       for (int x = 0; x <= schem.getSchemDimX(); x++) {
         for (int z = 0; z <= schem.getSchemDimZ(); z++) {

           if (index != 0 && index % queue.chunkSize == 0) {
             queue.getList().add(chunk);
             chunk = new SchematicChunk(w);
          }

           BlockPos pos = (new BlockPos(x, y, z)).func_177982_a(p.getX(), p.getY(), p.getZ());



           BlockState block = schem.getStateFromID(schem.blockMap[index]);
           chunk.blocks.add(new SchematicChunk.SchematicChunkBlockState(pos, block));

           index++;
        }
      }
    }

     queue.getList().add(chunk);
     chunk = new SchematicChunk(w);

     for (i = 0; i < schem.getTileData().size(); i++) {
       TileData tileData = schem.getTileData().get(i);
       BlockPos pos = new BlockPos(p.getX() + tileData.getPos()[0], p.getY() + tileData.getPos()[1], p.getZ() + tileData.getPos()[2]);

       chunk.blocks.add(new SchematicChunk.SchematicChunkBlockState(pos, tileData.getNbtDataString()));
    }

     queue.getList().add(chunk);

     SchematicChunk cc = new SchematicChunk(w);
     cc.blocks.addAll(ents);
     queue.getList().add(cc);
  }















  public enum FileLocation
  {
     INTERNAL, EXTERNAL;
  }

  public static interface IReplaceBlockSpawn {
    void replaceBlock(World param1World, BlockPos param1BlockPos);

    boolean keepBlock();

    BlockState getBlockState();

    Block getBlock();
  }
}


