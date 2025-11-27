package com.healthcare;

public abstract class Person {

    protected int id;
    protected String name;
    protected int age;
    protected String phone;

    public Person(int id, String name, int age, String phone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    public int getId() { 
        return id; 
    }

    public String getName() { 
        return name; 
    }

    public int getAge() { 
        return age; 
    }

    public String getPhone() {
        return phone;
    }

    // Polymorphism - Override in subclasses
    public abstract String getDetails();
}
