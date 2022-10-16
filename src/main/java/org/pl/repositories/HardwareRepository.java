package org.pl.repositories;

import lombok.AllArgsConstructor;
import org.pl.model.Hardware;

import java.util.ArrayList;

@AllArgsConstructor
public class HardwareRepository extends Repository<Hardware> {
    public HardwareRepository(ArrayList<Hardware> elements) {
        super(elements);
    }
}
