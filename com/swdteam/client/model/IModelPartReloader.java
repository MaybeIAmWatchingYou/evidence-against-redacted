package com.swdteam.client.model;

import com.swdteam.model.javajson.JSONModel;

public interface IModelPartReloader {
  void init();
  
  JSONModel getModel();
}


