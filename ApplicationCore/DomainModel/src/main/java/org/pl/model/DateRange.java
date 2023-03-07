package org.pl.model;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateRange implements Serializable {
    @NotNull
    @JsonbDateFormat(value = "dd-MM-yyyy HH:mm:ss")
    private Date startDate;

    @NotNull
    @JsonbDateFormat(value = "dd-MM-yyyy HH:mm:ss")
    private Date endDate;
}
