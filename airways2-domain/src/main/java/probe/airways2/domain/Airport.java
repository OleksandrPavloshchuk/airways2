package probe.airways2.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import java.util.Objects;

@Entity
public class Airport {
  @Length(min = 3, max = 3)
  @Id
  @Column(nullable = false)
  @NotNull
  @NotEmpty
  private String code;

  @Column(nullable = false)
  @Length(min = 5, max = 255)
  @NotNull
  @NotEmpty
  private String name;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return String.format("%s \"%s\"", code, name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Airport airport = (Airport) o;
    return code.equals(airport.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code);
  }
}
