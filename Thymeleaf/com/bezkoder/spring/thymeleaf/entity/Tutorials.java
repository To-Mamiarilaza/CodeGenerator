
package com.bezkoder.spring.thymeleaf.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "tutorials")
public class Tutorials {
    /// Field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;
    
    @Column(name = "title")
    String title;
    
    @Column(name = "description")
    String description;
    
    @Column(name = "level")
    Integer level;
    
    @Column(name = "published")
    Boolean published;
    
    
    
    /// Constructor
    public Tutorials(){}
    
    
    /// Getter and setter
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getLevel() {
        return level;
    }
    
    public void setLevel(Integer level) {
        this.level = level;
    }
    
    public Boolean getPublished() {
        return published;
    }
    
    public void setPublished(Boolean published) {
        this.published = published;
    }
    
    
}
