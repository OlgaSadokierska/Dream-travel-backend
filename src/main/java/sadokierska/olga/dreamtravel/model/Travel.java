package sadokierska.olga.dreamtravel.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.util.Date;

@Entity
@Data
@Table(name="travel")
public class Travel {

    @Id
    @JdbcTypeCode(java.sql.Types.VARCHAR)
    private Integer id;
    private String country;
    private String city;
    private Date startDate;
    private Date endDate;
    private String description;
    private int rate;
}
