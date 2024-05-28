package sadokierska.olga.dreamtravel.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="travel")
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String country;
    private String city;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private int rate;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}
