package com.truphone.devices.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "Device")
public class Device {

    public Device(String name, String brand) {
        this.name = name;
        this.brand = brand;
    }

    public Device(String name, String brand, Date creationDate) {
        this.name = name;
        this.brand = brand;
        this.creationDate = creationDate;
    }


    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name", nullable = false)
    @Size(min = 2, message
            = "The name should have at least 2 characters")
    private String name;

    @Column(name = "brand", nullable = false)
    @Size(min = 2, message
            = "The brand should have at least 2 characters")
    private String brand;

    @Column(name = "creationDate")
    private Date creationDate;

}
