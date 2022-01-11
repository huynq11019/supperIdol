
package com.ap.iamstu.infrastructure.persistence.entity;

import com.ap.iamstu.infrastructure.support.entity.AuditableEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;


@Entity
@Table(name = "user_login")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginEntity extends AuditableEntity {
	private static final long serialVersionUID = 3192282905748712929L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ip", length = 75)
	private String ip;

	@Column(name = "login_time")
	private Instant loginTime;

	@Column(name = "success", nullable = false, columnDefinition = "tinyint(1) default 1")
	private boolean success;

	@Column(name = "username", length = 75)
	private String username;

	@Column(name = "description", length = 255)
	private String description;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		UserLoginEntity that = (UserLoginEntity) o;
		return id != null  && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
