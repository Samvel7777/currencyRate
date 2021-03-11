package am.itspace.restapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "last_login")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ip",
        "country_code",
        "country_name",
        "region_code",
        "region_name",
        "city"
})
public class LastLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonProperty("ip")
    private String ip;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("country_name")
    private String countryName;
    @JsonProperty("region_code")
    private String regionCode;
    @JsonProperty("region_name")
    private String regionName;
    @JsonProperty("city")
    private String city;
    private LocalDateTime loginDate;
    private int userId;

    public LastLogin(LastLogin lastLogin, int userId) {
        this.ip = lastLogin.ip;
        this.countryCode = lastLogin.countryCode;
        this.countryName = lastLogin.countryName;
        this.regionCode = lastLogin.regionCode;
        this.regionName = lastLogin.regionName;
        this.city = lastLogin.city;
        this.userId = userId;
    }
}
