package com.github.moinmarcell.starter.custom;

public class Ping {
  private String message;
  private boolean enabled;

  public Ping(String message, boolean enabled) {
    this.message = message;
    this.enabled = enabled;
  }

  public Ping() {
  }

  public String getMessage() {
    return message;
  }

  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public String toString() {
    return "Ping{" +
      "message='" + message + '\'' +
      ", enabled=" + enabled +
      '}';
  }
}
