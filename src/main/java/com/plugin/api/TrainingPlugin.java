package com.plugin.api;

public interface TrainingPlugin {
  String getName();
  default void init() {}
  default String execute(String input) { return "OK: " + input; }
}
