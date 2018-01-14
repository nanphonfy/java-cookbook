package cn.sibat.boot.server.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_admin")
public class Admin implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "t_id")
	private int id;
	@Column(name = "t_username")
	private String username;
	@Column(name = "t_password")
	private String password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
