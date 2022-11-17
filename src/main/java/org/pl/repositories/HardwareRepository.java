package org.pl.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.pl.model.Hardware;

import java.util.ArrayList;

@ApplicationScoped
@AllArgsConstructor
public class HardwareRepository extends Repository<Hardware> {
    public HardwareRepository(ArrayList<Hardware> elements) {
        super(elements);
    }
}
