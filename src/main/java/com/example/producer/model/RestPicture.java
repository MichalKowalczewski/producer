package com.example.producer.model;

public class RestPicture {

  private int id;
  private String url;

  public RestPicture(int id, String url) {
    this.id = id;
    this.url = url;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
