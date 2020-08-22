package com.example.producer.model;

import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "picture")
public class Picture {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private int id;
  @Column
  private Blob image;

  public Picture() {
  }

  public Picture(Blob image) {
    this.image = image;
  }

  public Picture(int id, Blob image) {
    this.id = id;
    this.image = image;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Blob getImage() {
    return image;
  }

  public void setImage(Blob image) {
    this.image = image;
  }
}
