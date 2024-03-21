package com.github.moinmarcell.starter.custom;

public class Pong {

  private Integer id;

  public Pong(Integer id) {
    this.id = id;
  }

  public Pong() {
  }

  public Integer getId() {
    return id;
  }

  @Override
  public String toString() {
    return "Pong{" +
      "id=" + id +
      '}';
  }
}
