package org.pl.model;

import com.sun.istack.Nullable;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Access(AccessType.FIELD)
public class DateRange {
    @NotNull
    @JsonbDateFormat(value = "dd-MM-yyyy HH:mm:ss")
    private Date startDate;
    @JsonbDateFormat(value = "dd-MM-yyyy HH:mm:ss")
    private Date endDate;
}
