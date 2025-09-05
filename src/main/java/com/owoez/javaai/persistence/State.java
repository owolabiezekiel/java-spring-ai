package com.owoez.javaai.persistence;

import com.owoez.javaai.model.StateModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Objects;

@Accessors(chain = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "states")
@Getter
@Setter
@ToString
@SuperBuilder
@RequiredArgsConstructor
@SQLRestriction("is_deleted = false")
public class State extends BaseEntity {
  @Column(nullable = false)
  private String name;


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    State that = (State) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id);
  }

  public StateModel toModel() {
    return StateModel.builder().id(id).name(name).build();
  }
}
