package sadokierska.olga.dreamtravel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="travel")
@Getter
@Setter
public class Travel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String country;
    private String city;
    private Date startDate;
    private Date endDate;
    private String description;
    private int rate;
}
