package org.pl.repositories;

import org.pl.model.Hardware;

import java.util.ArrayList;

public class HardwareRepository extends Repository<Hardware> {
    HardwareRepository(ArrayList<Hardware> elements) {
        super(elements);
    }
}
