package com.example.demo.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Created by noba on 12/16/2017.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The Id generated by database")
    private Integer id;
    @ApiModelProperty(notes = "The name of the user")
    private String name;
    @ApiModelProperty(notes = "The age of the user")
    private int age;
    @ApiModelProperty(notes = "The salary of the user")
    private Long salary;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }
}
