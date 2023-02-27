package org.pl.adapter.data.model;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Access(AccessType.FIELD)
public class DateRangeEnt implements Serializable {
    @NotNull
    @JsonbDateFormat(value = "dd-MM-yyyy HH:mm:ss")
    private Date startDate;
    @JsonbDateFormat(value = "dd-MM-yyyy HH:mm:ss")
    private Date endDate;
}
