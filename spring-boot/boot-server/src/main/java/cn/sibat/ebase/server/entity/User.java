package cn.sibat.ebase.server.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User 实体
 *
 * @since 1.0.0 2017年3月5日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
@Entity // 实体
public class User implements Serializable,UserDetails{

	private static final long serialVersionUID = 1L;

	@Id // 主键
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
	private Long id; // 用户的唯一标识

	@NotEmpty(message = "姓名不能为空")
	@Size(min=2, max=20)
	@Column(nullable = false, length = 20) // 映射为字段，值不能为空
	private String name;

	@NotEmpty(message = "邮箱不能为空")
	@Size(max=50)
	@Email(message= "邮箱格式不对" )
	@Column(nullable = false, length = 50, unique = true)
	private String email;

	@NotEmpty(message = "账号不能为空")
	@Size(min=3, max=20)
	@Column(nullable = false, length = 20, unique = true)
	private String username; // 用户账号，用户登录时的唯一标识

	@NotEmpty(message = "密码不能为空")
	@Size(max=100)
	@Column(length = 100)
	private String password; // 登录时密码

	@Column(length = 200)
	private String avatar; // 头像图片地址

	@Transient
	private List<Authority> authorities;

	protected User() { // JPA 的规范要求无参构造函数；设为 protected 防止直接使用
	}

	public User(String name, String email, String username, String password) {
		this.name = name;
		this.email = email;
		this.username = username;
		setEncodePassword(password);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//  需将 List<Authority> 转成 List<SimpleGrantedAuthority>，否则前端拿不到角色列表名称
		List<SimpleGrantedAuthority> simpleAuthorities = new ArrayList<>();
		for(GrantedAuthority authority : this.authorities){
			simpleAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
		}
		return simpleAuthorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		setEncodePassword(password);
	}

	public void setEncodePassword(String password) {
		PasswordEncoder  encoder = new BCryptPasswordEncoder();
		String encodePasswd = encoder.encode(password);
		this.password = encodePasswd;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override public boolean isAccountNonExpired() {
		return true;
	}

	@Override public boolean isAccountNonLocked() {
		return true;
	}

	@Override public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return String.format("User[id=%d, username='%s', name='%s', email='%s', password='%s']", id, username, name, email,
				password);
	}
}
