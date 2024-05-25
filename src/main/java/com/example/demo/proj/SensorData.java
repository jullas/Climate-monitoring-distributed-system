package com.example.demo.proj;


 import jakarta.persistence.*; // for Spring Boot 3

@Table(name = "sensorData")
@Entity
public class SensorData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "tt")
    private String tt;

    @Column(name = "vv")
    private String vv;

    // Getters and Setters


    public SensorData() {
    }

    public SensorData(String type, String value) {
        this.tt = type;
        this.vv = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return tt;
    }

    public void setType(String type) {
        this.tt = type;
    }

    public String getValue() {
        return vv;
    }

    public void setValue(String value) {
        this.vv = value;
    }


}
