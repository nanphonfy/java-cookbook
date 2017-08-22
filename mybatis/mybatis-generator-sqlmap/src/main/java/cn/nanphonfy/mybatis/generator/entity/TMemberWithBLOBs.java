package main.java.cn.nanphonfy.mybatis.generator.entity;

public class TMemberWithBLOBs extends TMember {
    private String address;

    private byte[] photo;

    private String selfintroduction;

    private String teachingexperience;

    private String teachingrecords;

    private String workspace;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getSelfintroduction() {
        return selfintroduction;
    }

    public void setSelfintroduction(String selfintroduction) {
        this.selfintroduction = selfintroduction == null ? null : selfintroduction.trim();
    }

    public String getTeachingexperience() {
        return teachingexperience;
    }

    public void setTeachingexperience(String teachingexperience) {
        this.teachingexperience = teachingexperience == null ? null : teachingexperience.trim();
    }

    public String getTeachingrecords() {
        return teachingrecords;
    }

    public void setTeachingrecords(String teachingrecords) {
        this.teachingrecords = teachingrecords == null ? null : teachingrecords.trim();
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace == null ? null : workspace.trim();
    }
}