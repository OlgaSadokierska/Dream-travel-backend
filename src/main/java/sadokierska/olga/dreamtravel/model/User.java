package sadokierska.olga.dreamtravel.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

@Entity
@Data
@Table(name="user")
public class User {

    @Id
    @JdbcTypeCode(java.sql.Types.VARCHAR)
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String provider;

    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;
}
