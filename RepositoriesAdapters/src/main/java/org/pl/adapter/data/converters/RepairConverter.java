package org.pl.adapter.data.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.adapter.data.model.ClientEnt;
import org.pl.adapter.data.model.HardwareEnt;
import org.pl.adapter.data.model.RepairEnt;
import org.pl.model.Client;
import org.pl.model.Hardware;
import org.pl.model.Repair;

@ApplicationScoped
public class RepairConverter {
    @Inject
    private ClientConverter clientConverter;
    @Inject
    private HardwareConverter hardwareConverter;
    @Inject
    private DateRangeConverter dateRangeConverter;
    public RepairEnt convert(Repair repair) {
        if (repair == null) return null;
        try {
            ClientEnt clientEnt = clientConverter.convert(repair.getClient());
            HardwareEnt hardwareEnt = hardwareConverter.convert(repair.getHardware());
            return RepairEnt.builder()
                    .id(repair.getId())
                    .archive(repair.getArchive())
                    .clientEnt(clientEnt)
                    .hardwareEnt(hardwareEnt)
                    .dateRangeEnt(dateRangeConverter.convert(repair.getDateRange()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Repair convert(RepairEnt repairEnt) {
        if (repairEnt == null) return null;
        try {
            Client client = clientConverter.convert(repairEnt.getClientEnt());
            Hardware hardware = hardwareConverter.convert(repairEnt.getHardwareEnt());
            return Repair.builder()
                    .id(repairEnt.getId())
                    .archive(repairEnt.isArchive())
                    .client(client)
                    .hardware(hardware)
                    .dateRange(dateRangeConverter.convert(repairEnt.getDateRangeEnt()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
